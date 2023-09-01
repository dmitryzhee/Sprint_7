import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class CourierLoginTest implements TestData{


    private ScooterServiceClient client = new ScooterServiceClient();

    RequestSpecification requestSpecification;

    @Before
    public void setUp() {
        requestSpecification= new RequestSpecBuilder()
                .setBaseUri(SCOOTER_SERVICE_URI)
                .setContentType(ContentType.JSON)
                .build();
        client.setRequestSpecification(requestSpecification);
    }

    @Test
    public void loginSuccess() {
        client.createCourier(COURIER);
        ValidatableResponse response = client.login(Credentials.fromCourier(COURIER));
        response.assertThat().statusCode(200);
    }

    @Test
    public void invalidPasswordAuthorizationFailure() {
        client.createCourier(COURIER);
        Credentials credentials = Credentials.fromCourier(COURIER);
        credentials.setPassword("invalid");
        ValidatableResponse response = client.login(credentials);
        response.assertThat().body(containsString("Учетная запись не найдена"));
    }

    @Test
    public void invalidLoginAuthorizationFailure() {
        client.createCourier(COURIER);
        Credentials credentials = Credentials.fromCourier(COURIER);
        credentials.setLogin("invalid");
        ValidatableResponse response = client.login(credentials);
        response.assertThat().body(containsString("Учетная запись не найдена"));
    }

    @Test
    public void successfulLoginResponseContainsID() {
        client.createCourier(COURIER);
        ValidatableResponse response = client.login(Credentials.fromCourier(COURIER));
        response.assertThat().body(containsString("id"));
    }

    @Test
    public void loginWithPartialParametersFailure() {
     Courier courier = COURIER;
     client.createCourier(courier);
     Credentials noPassCredentials = new Credentials(courier.getLogin(), null);
     ValidatableResponse noPassResponse = client.login(noPassCredentials);
     Credentials noLoginCredentials = new Credentials(null, courier.getPassword());
     ValidatableResponse noLoginResponse = client.login(noLoginCredentials);
     noPassResponse.assertThat().statusCode(400);
     noLoginResponse.assertThat().statusCode(400);


    }




    @After
    public void tearDown() {
            int id = client.login(Credentials.fromCourier(COURIER)).extract().body().jsonPath()
                    .getInt("id");
            client.deleteUser(id);
    }


}
