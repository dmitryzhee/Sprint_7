import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ScooterServiceClient {

    public static final String CREATE_COURIER_ENDPOINT = "/api/v1/courier";
    public static final String COURIER_LOGIN_ENDPOINT = "/api/v1/courier/login";

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
                .body(credentials)
                .post(COURIER_LOGIN_ENDPOINT)
                .then();
    }



}
