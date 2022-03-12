package org.p10.PetStore.Models;

import org.p10.PetStore.Models.Pojo.OrderPojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private int id;
    private int petId;
    private int quantity;
    private LocalDateTime shipDate;
    private OrderStatus status;
    private boolean complete;

    public Order(OrderPojo orderPojo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(orderPojo.getShipDate(), formatter);
        this.id = orderPojo.getId();
        this.petId = orderPojo.getPetId();
        this.quantity = orderPojo.getQuantity();
        this.shipDate = dateTime;
        this.status = OrderStatus.values()[orderPojo.getStatus()];
        this.complete = orderPojo.isComplete();
    }

    public Order(int id, int petId, int quantity, LocalDateTime shipDate, OrderStatus status, boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
