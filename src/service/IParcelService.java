package service;

import model.Client;
import model.Parcel;
import model.ParcelCategory;
import model.ParcelStatus;
import java.util.List;

public interface IParcelService {
    Parcel createParcel(double weight, ParcelCategory category, Client sender, Client recipient);
    List<Parcel> getAllParcels();
    List<Parcel> getParcelsByCategory(ParcelCategory category);
    Parcel getFullParcelById(int id);
    void changeStatus(int parcelId, ParcelStatus newStatus);
    void cancelParcel(int parcelId);
    List<Parcel> getParcelsByPerson(String name, String surname);
    List<Parcel> getParcelsSentBy(String name, String surname);
    List<Parcel> getParcelsReceivedBy(String name, String surname);
    double getDeliveredRevenue();
    void addClientToBlacklist(String name, String surname, String reason);
}

