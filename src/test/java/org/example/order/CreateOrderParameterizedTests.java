package org.example.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.orders.Order;
import org.example.orders.OrderClient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTests {

    private Order order;
    private OrderClient orderClient;
    private final List<String> color;

    public CreateOrderParameterizedTests(List<String> color) {
        this.color = color;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("GREY","BLACK")},
                {List.of()},
        };
    }

    @Test
    @DisplayName("Create order with different colors")
    @Description("Создание заказа с выбором разных цветов")
    public void createOrderWithDifferentColors() {
        order = Order.randomWithoutColor(color);
        ValidatableResponse response = orderClient.create(order);

        assertEquals(HttpStatus.SC_CREATED, response.extract().statusCode());

        int track = response.extract().path("track");
        assertNotEquals(0, track);
    }
}
