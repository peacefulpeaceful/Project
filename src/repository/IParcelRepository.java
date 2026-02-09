package repository;

import model.Parcel;
import model.ParcelStatus;
import java.util.List;

public interface IParcelRepository {
    void save(Parcel p, int senderId, int receiverId);
    List<Parcel> getAll();
    Parcel getById(int id);
    Parcel getFullById(int id);
    void updateStatus(int id, ParcelStatus status);
    double getDeliveredRevenue();

}
