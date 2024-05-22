package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.base.Client;

import java.util.Map;

public class CourierClient extends Client {
    private static final String COURIER_PATH = "/courier";
    private static final String BASE_PATH = "/api/v1" ;
    @Step("login courier")
    public ValidatableResponse loginCourier(CourierCredentials creds) {
        return spec()
                .body(creds)
                .when()
                .post(BASE_PATH + COURIER_PATH + "/login")
                .then().log().all();
    }

    @Step("create courier")
    public ValidatableResponse createCourier(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(BASE_PATH + COURIER_PATH)
                .then().log().all();
    }

    @Step("delete courier")
    public ValidatableResponse deleteCourier(int id) {
        return spec()
                .body(Map.of("id", id))
                .when()
                .delete(BASE_PATH + COURIER_PATH + "/" + id)
                .then().log().all();
    }
}