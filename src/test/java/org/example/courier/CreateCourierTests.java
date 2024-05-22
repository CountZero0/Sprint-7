package org.example.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateCourierTests {
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    int courierId;

    @Test
    @DisplayName("Create courier")
    @Description("Проверка возможности создания курьера")
    public void courierCreateTest() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);
        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

        assertEquals(HttpStatus.SC_CREATED, createResponse.extract().statusCode());

        assertEquals(true, createResponse.extract().path("ok"));
    }

    @Test
    @DisplayName("Create two identical couriers")
    @Description("Проверка возможности создания двух одинаковых курьеров")
    public void twoIdenticalCourierCreateTest() {
        var courier = Courier.random();
        ValidatableResponse responseFirst = client.createCourier(courier);
        check.createdSuccessfully(responseFirst);
        assertEquals(HttpStatus.SC_CREATED, responseFirst.extract().statusCode());

        ValidatableResponse responseSecond = client.createCourier(courier);
        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);

        assertEquals(HttpStatus.SC_CONFLICT, responseSecond.extract().statusCode());

        assertEquals("Этот логин уже используется. Попробуйте другой.",
                responseSecond.extract().path("message"));
    }

    @Test
    @DisplayName("Create courier with firstName - Null")
    @Description("Проверка возможности создания курьера с пустым полем Имя")
    public void courierCreateWithNullFirstNameTest() {
        var courierLogin = Courier.random().getLogin();
        var courierPassword = Courier.random().getPassword();
        ValidatableResponse createResponse = client.createCourier(new Courier(courierLogin,
                courierPassword,null));
        check.createdSuccessfully(createResponse);
        ValidatableResponse loginResponse = client.loginCourier(new CourierCredentials(courierLogin, courierPassword));
        courierId = check.loggedInSuccessfully(loginResponse);

        assertEquals(HttpStatus.SC_CREATED, createResponse.extract().statusCode());

        assertEquals(true, createResponse.extract().path("ok"));
    }

    @Test
    @DisplayName("Create courier with login - Null")
    @Description("Проверка возможности создания курьера с пустым полем Логин")
    public void courierCreateWithNullLoginTest() {

        ValidatableResponse response = client.createCourier(new Courier(null,
                Courier.random().getPassword(), Courier.random().getFirstName()));

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.extract().statusCode());

        assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
    }

    @Test
    @DisplayName("Create courier with password - Null")
    @Description("Проверка возможности создания курьера с пустым полем Пароль")
    public void courierCreateWithNullPasswordTest() {
        ValidatableResponse response = client.createCourier(new Courier(Courier.random().getLogin(),
                null, Courier.random().getFirstName()));

        assertEquals(HttpStatus.SC_BAD_REQUEST, response.extract().statusCode());

        assertEquals("Недостаточно данных для создания учетной записи", response.extract().path("message"));
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse response = client.deleteCourier(courierId);
            check.deletedSuccesfully(response);
        }
    }
}
