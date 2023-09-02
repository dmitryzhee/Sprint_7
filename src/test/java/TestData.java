import java.util.Random;

public interface TestData {

    String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";

//    Random random = new Random();
//    int max = 999;
//    int min = 1;
//    Courier COURIER = new Courier("Courier" + random.nextInt((max - min +1) + min), "12345", "Ivan");

    Courier COURIER = new Courier("Ivan321", "12345", "Ivan");

    Courier NO_PASSWORD_COURIER = new Courier("Courier", null, "Ivan");
    Courier NO_LOGIN_COURIER = new Courier(null, "12345", "Ivan");





}
