package service;

import model.Client;
import model.Parcel;
import model.ParcelStatus;
import java.util.List;

public interface IParcelSerice {
    Parcel createParcel(double weight, Client sender, Client recipient);
    List<Parcel> getAllParcels();
    void changeStatus(int parcelId, ParcelStatus newStatus);
}
