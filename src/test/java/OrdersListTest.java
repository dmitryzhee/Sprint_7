import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class OrdersListTest implements TestData {
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
    public void responseContainsOrdersList() {
        ValidatableResponse response = client.getOrdersList();
//        response.assertThat().body(containsString("orders"));
        response.assertThat().body("orders", notNullValue());
    }


}
