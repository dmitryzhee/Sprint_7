import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class CourierCreationTest implements TestData{

    private ScooterServiceClient client = new ScooterServiceClient();

    Courier courier;

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
    public void courierCreationSuccess () {
        courier=COURIER;
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(201);
    }

    @Test
    public void courierCreationSuccessResponseIsOk () {
        courier=COURIER;
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().body("ok", is(true));
    }

    @Test
    public void couriersWithSameLoginCreationFailure () {
        courier=COURIER;
        client.createCourier(courier);
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().body(containsString("Этот логин уже используется"));
    }

    @Test
    public void courierWithPartialParametersCorrectMessage() {
        courier = NO_PASSWORD_COURIER;
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().body(containsString("Недостаточно данных для создания учетной записи"));
    }



    @After
    public void tearDown() {
        if (courier.equals(COURIER)) {
            int id = client.login(Credentials.fromCourier(courier)).extract().body().jsonPath()
                    .getInt("id");
            client.deleteUser(id);
        }
    }

}
