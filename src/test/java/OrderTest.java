import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class OrderTest implements TestData{
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    private ScooterServiceClient client = new ScooterServiceClient();

    RequestSpecification requestSpecification;


    public OrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment,  List<String> color) {
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
                {"Ivan", "Ivanov", "Pushkinskaya-5", "Pushkinskaya", "890312345678", 5, "2023-09-05", "Comment", Arrays.asList("BLACK") },
                {"Boris", "Borisov", "<Belorusskaya>-7", "Belorusskaya", "891712345678", 7, "2023-09-01", "Comment2", Arrays.asList("GREY")},
                {"Pavel", "Pavlov", "Tverskaya-10", "Tverskaya", "890587654321", 2, "2023-09-07", "Comment3", Arrays.asList("BLACK", "GREY")},

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
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = client.makeOrder(order);
        response.assertThat().statusCode(201);
    }


}
