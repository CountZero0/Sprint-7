package org.example.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.base.Client;

public class OrderClient extends Client {
    private static final String ORDER_CREATE_URL ="/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_CREATE_URL)
                .then().log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return spec()
                .when()
                .get(ORDER_CREATE_URL)
                .then().log().all();
    }
}