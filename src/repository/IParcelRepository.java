package repository;

import model.Parcel;
import model.ParcelStatus;
import java.util.List;

public interface IParcelRepository {
    void save(Parcel p);
    List<Parcel> getAll();
    Parcel getById(int id);
    void updateStatus(int id, ParcelStatus status);

}
