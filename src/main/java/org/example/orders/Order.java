package org.example.orders;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class Order {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public Order(String firstName, String lastName, String address, String metroStation, String phone,
                 int rentTime, String deliveryDate, String comment, List<String> color) {
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

    public Order() {}

    public static Order generic() {
        return new Order(RandomStringUtils.randomAlphabetic(5, 15), RandomStringUtils.randomAlphabetic(5, 15),
                "Охотный ряд 2", "5", "+79900091122", 5,
                "2024-05-18", RandomStringUtils.randomAlphabetic(5, 15), List.of("BLACK"));
    }

    public static Order randomWithoutColor(List<String> color) {
        return new Order(RandomStringUtils.randomAlphabetic(5, 15), RandomStringUtils.randomAlphabetic(5, 15),
                "Охотный ряд 2", "1", "+79900091122", 5,
                "2024-05-18", RandomStringUtils.randomAlphabetic(5, 15), color);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }
}
