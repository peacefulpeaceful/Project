package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Parcel {
    private int id;
    private double weight;
    private ParcelStatus status;
    private Client sender;
    private Client recipient;
    private BigDecimal cost;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;


    public Parcel() {
        this.createdAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public ParcelStatus getStatus() {
        return status;
    }

    public Client getSender() {
        return sender;
    }

    public Client getRecipient() {
        return recipient;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setStatus(ParcelStatus status) {
        this.status = status;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public void setRecipient(Client recipient) {
        this.recipient = recipient;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    @Override
    public String toString() {
        return "Parcel{id=" + id +
                ", weight=" + weight +
                ", status=" + status +
                ", cost=" + cost +
                ", createdAt=" + createdAt +
                "}";
    }
}