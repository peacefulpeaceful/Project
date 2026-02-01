package model;

import java.time.LocalDateTime;

public class Parcel {
    private int id;
    private double weight;
    private ParcelStatus status;
    private ParcelCategory category;
    private Client sender;
    private Client recipient;
    private LocalDateTime createdAt;
    private double cost;



    public Parcel() {;
    }

    private Parcel(Builder builder){
        this.weight = builder.weight;
        this.status = builder.status;
        this.category = builder.category;
        this.sender = builder.sender;
        this.recipient = builder.recipient;
        this.createdAt = builder.createdAt;
        this.cost = builder.cost;
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

    public ParcelCategory getCategory() {
        return category;
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

    public void setCategory(ParcelCategory category) {
        this.category = category;
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

    public static class Builder{
        private final double weight;
        private final ParcelCategory category;
        private final Client sender;
        private final Client recipient;
        private ParcelStatus status;
        private LocalDateTime createdAt;
        private double cost;

        public Builder(double weight, Parcel category, Client sender, Client recipient){
            this.weight = weight;
            this.category = category;
            this.sender = sender;
            this.recipient = recipient;
        }
        public Builder withStatus(ParcelStatus status){
            this.status = status;
            return this;
        }
        public Builder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withCost(double cost) {
            this.cost = cost;
            return this;
        }

        public Parcel build() {
            return new Parcel(this);
        }

        public String getFullParcelDescription() {
            String senderInfo = sender == null ? "N/A" : sender.toString();
            String recipientInfo = recipient == null ? "N/A" : recipient.toString();
            return "Parcel{id=" + id +
                    ", weight=" + weight +
                    ", status=" + status +
                    ", category=" + category +
                    ", cost=" + cost +
                    ", createdAt=" + createdAt +
                    ", sender=" + senderInfo +
                    ", recipient=" + recipientInfo +
                    "}";
        }

    }



    @Override
    public String toString() {
        return "Parcel{id=" + id +
                ", weight=" + weight +
                ", status=" + status +
                ", category=" + category +
                ", cost=" + cost +
                ", createdAt=" + createdAt +
                "}";
    }
}