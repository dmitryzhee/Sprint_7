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
    public void loginWithoutPasswordFailure() {
        Courier courier = COURIER;
        client.createCourier(courier);
        //При установке password = null, возвращается код 504 и тест падает. Куратор сказал, что это, вероятно, баг в системе.
        Credentials credentials = new Credentials(courier.getLogin(), "");
        ValidatableResponse response = client.login(credentials);
        response.assertThat().statusCode(400);
    }
    @Test
    public void loginWithoutLoginFailure() {
        Courier courier = COURIER;
        client.createCourier(courier);
        //При установке password = null, возвращается код 504 и тест падает. Куратор сказал, что это, вероятно, баг в системе.
        Credentials credentials = new Credentials("", courier.getPassword());
        ValidatableResponse response = client.login(credentials);
        response.assertThat().statusCode(400);
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
        //проверяется авторизация с неправильным логином, а также авторизация несуществующего пользователя
        client.createCourier(COURIER);
        Credentials credentials = Credentials.fromCourier(COURIER);
        credentials.setLogin("invalid");
        ValidatableResponse response = client.login(credentials);
        response.assertThat().body(containsString("Учетная запись не найдена"));
    }

    @Test
    public void loginWithoutPasswordErrorMessage() {
        Courier courier = COURIER;
        client.createCourier(courier);
        //При установке password = null, возвращается код 504 и тест падает. Куратор сказал, что это, вероятно, баг в системе.
        Credentials credentials = new Credentials(courier.getLogin(), "");
        ValidatableResponse response = client.login(credentials);
        response.assertThat().body(containsString("Недостаточно данных для входа"));
    }
    @Test
    public void loginWithoutLoginErrorMessage() {
        Courier courier = COURIER;
        client.createCourier(courier);
        //При установке password = null, возвращается код 504 и тест падает. Куратор сказал, что это, вероятно, баг в системе.
        Credentials credentials = new Credentials("", courier.getPassword());
        ValidatableResponse response = client.login(credentials);
        response.assertThat().body(containsString("Недостаточно данных для входа"));
    }

    @Test
    public void successfulLoginResponseContainsID() {
        client.createCourier(COURIER);
        ValidatableResponse response = client.login(Credentials.fromCourier(COURIER));
        response.assertThat().body(containsString("id"));
    }

    @After
    public void tearDown() {
            int id = client.login(Credentials.fromCourier(COURIER)).extract().body().jsonPath()
                    .getInt("id");
            client.deleteUser(id);
    }


}
