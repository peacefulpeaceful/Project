package service;

import model.Client;
import model.Parcel;
import model.ParcelCategory;
import model.ParcelStatus;

import java.time.LocalDateTime;

public class ParcelFactory {
    public Parcel create(double weight, ParcelCategory, Client sender, Client recipient, double cost){
        Parcel parcel = new Parcel();
        parcel.setWeight(weight);
        parcel.setCategory(category);
        parcel.setSender(sender);
        parcel.setRecipient(recipient);
        parcel.setCost(cost);
        parcel.setStatus(ParcelStatus.CREATED);
        parcel.setCreatedAt(LocalDateTime.now());
        return parcel;
    }
}
