package org.example.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.orders.Order;
import org.example.orders.OrderClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GetOrderListTests {
    private Order order;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Get orders list")
    public void getOrderList() {
        order = Order.generic();
        ValidatableResponse response = orderClient.getOrderList();

        assertEquals(HttpStatus.SC_OK, response.extract().statusCode());

        List<String> listResponse = response.extract().path("orders");
        assertFalse(listResponse.isEmpty());
    }
}
