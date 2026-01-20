package model;

import java.time.LocalDateTime;

public class Parcel {
    private int id;
    private double weight;
    private ParcelStatus status;
    private Client sender;
    private Client recipient;
    private LocalDateTime createdAt;
    private double cost;


    public Parcel() {;
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

    public double getCost(){
        return cost;
    }
    public void setCost(double cost){
        this.cost = cost;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
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