package service;

import model.Parcel;
import model.Client;
import model.ParcelStatus;
import repository.ParcelRepository;

import java.util.List;
import java.util.UUID;

public class ParcelService {

    private final ParcelRepository repo;

    public ParcelService(ParcelRepository repo) {
        this.repo = repo;
    }


    public Parcel createParcel(double weight, Client sender, Client recipient) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be > 0");
        }

        Parcel parcel = new Parcel();
        parcel.setWeight(weight);
        parcel.setSender(sender);
        parcel.setRecipient(recipient);


        parcel.setStatus(ParcelStatus.CREATED);


        parcel.setTrackingNumber("PO" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());


        repo.save(parcel);

        return parcel;
    }

    public List<Parcel> getAllParcels() {
        return repo.getAll();
    }

    public void changeStatus(int parcelId, ParcelStatus newStatus) {
        Parcel existing = repo.getById(parcelId);
        if (existing == null) {
            throw new IllegalArgumentException("Parcel not found");
        }

        ParcelStatus current = existing.getStatus();

        if (!isValidStatusChange(current, newStatus)) {
            throw new IllegalStateException("Can't change status from " + current + " to " + newStatus);
        }

        repo.updateStatus(parcelId, newStatus);
    }

    private boolean isValidStatusChange(ParcelStatus current, ParcelStatus next) {
        return (current == ParcelStatus.CREATED && next == ParcelStatus.IN_TRANSIT)
                || (current == ParcelStatus.IN_TRANSIT && next == ParcelStatus.DELIVERED);
    }
}