package org.example.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotEquals;

public class LoginCourierTests {
    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    int courierId;
    private Courier courier;

    @Before
    public void setUp() {
        courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.createdSuccessfully(createResponse);

        var creds = CourierCredentials.from(courier);
        ValidatableResponse loginResponse = client.loginCourier(creds);
        courierId = check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Courier login with correct data")
    @Description("Авторизация с корректными данными")
    public void courierLoginWithCorrectData() {
        assertNotEquals(0, courierId);
    }

    @Test
    @DisplayName("Courier login with empty field Login")
    @Description("Попытка авторизации с пустым полем Логин")
    public void courierLoginWithEmptyFieldLoginTest() {
        ValidatableResponse loginResponse = client.loginCourier
                (new CourierCredentials("", courier.getPassword()));

        assertThat(loginResponse.extract().statusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));

        assertThat(loginResponse.extract().path("message"), equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Courier login with empty field Password")
    @Description("Попытка авторизации с пустым полем Пароль")
    public void courierLoginWithEmptyFieldPasswordTest() {

        ValidatableResponse loginResponse = client.loginCourier
                (new CourierCredentials(courier.getLogin(), ""));

        assertThat(loginResponse.extract().statusCode(), equalTo(HttpStatus.SC_BAD_REQUEST));

        assertThat(loginResponse.extract().path("message"), equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Courier login with unexisting Login")
    @Description("Попытка авторизации с несуществующим Логином")
    public void courierLoginWithUnexistingLoginTest() {
        ValidatableResponse loginResponse = client.loginCourier(new CourierCredentials("CountZero1001",
                "abed123$"));

        assertThat(loginResponse.extract().statusCode(), equalTo(HttpStatus.SC_NOT_FOUND));

        assertThat(loginResponse.extract().path("message"), equalTo("Учетная запись не найдена"));
    }

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse response = client.deleteCourier(courierId);
            check.deletedSuccesfully(response);
        }
    }
}
