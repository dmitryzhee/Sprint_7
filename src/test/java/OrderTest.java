import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderTest implements TestData{
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private int deliveryDate;
    private String comment;
    private String color;

    private ScooterServiceClient client = new ScooterServiceClient();

    RequestSpecification requestSpecification;


    public OrderTest(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, int deliveryDate, String comment, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object [][] getData() {
        return new Object[][] {
                {"Иван", "Иванов", "Пушкина-5", "Пушкинская", "903123456789", "сутки", 5, "Comment", "GREY"}
        };
    }

    @Before
    public void setUp() {
        requestSpecification= new RequestSpecBuilder()
                .setBaseUri(SCOOTER_SERVICE_URI)
                .setContentType(ContentType.JSON)
                .build();
        client.setRequestSpecification(requestSpecification);
    }

    @Test
    public void makeOrderSuccess() {
        Order order = new Order();
        ValidatableResponse response = client.makeOrder(order);
        response.assertThat().statusCode(201);
    }


}
