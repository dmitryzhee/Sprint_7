import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ScooterServiceClient {

    public static final String CREATE_COURIER_ENDPOINT = "/api/v1/courier";
    public static final String COURIER_LOGIN_ENDPOINT = "/api/v1/courier/login";

    public static final String COURIER_DELETE_ENDPOINT = "/api/v1/courier/";

    private RequestSpecification requestSpecification;

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(requestSpecification)
                .body(courier)
                .post(CREATE_COURIER_ENDPOINT)
                .then();
    }

    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec(requestSpecification)
                .body(credentials)
                .post(COURIER_LOGIN_ENDPOINT)
                .then();
    }

    public ValidatableResponse deleteUser(int id)  {
        String json = "{\"id\": \"" + id + "\"}";
        return given()
                .spec(requestSpecification)
                .body(json)
                .delete(COURIER_DELETE_ENDPOINT + id)
                .then()
                .log().all();
    }


}
