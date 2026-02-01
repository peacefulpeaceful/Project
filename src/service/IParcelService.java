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
}
