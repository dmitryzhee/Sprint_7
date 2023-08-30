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

public class CourierCreationTest {

    public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";

    public static final Courier COURIER = new Courier("naruta2", "12345", "Ivan");

    private ScooterServiceClient client = new ScooterServiceClient();

    RequestSpecification requestSpecification;

    @Before
    public void setUp() {
       requestSpecification= new RequestSpecBuilder()
                .setBaseUri(SCOOTER_SERVICE_URI)
                .setContentType(ContentType.JSON)
                .build();
    }



    @Test
    public void courierCreationSuccess () {

        client.setRequestSpecification(requestSpecification);

        ValidatableResponse response = client.createCourier(COURIER);
        response.assertThat().statusCode(201);

//        response.assertThat().body("ok", is(true));
    }

    @Test
    public void couriersWithSameLoginCreationFailure () {
        client.setRequestSpecification(requestSpecification);
        client.createCourier(COURIER);
        ValidatableResponse response = client.createCourier(COURIER);
        response.assertThat().body(containsString("Этот логин уже используется"));
    }





    @After
    public void tearDown() {
        int id = client.login(Credentials.fromCourier(COURIER)).extract().body().jsonPath()
                .getInt("id");
        client.deleteUser(id);
    }

}
