package service;

import model.Parcel;
import model.Client;
import model.ParcelCategory;
import model.ParcelStatus;
import repository.ClientRepository;
import repository.ParcelRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ParcelService implements IParcelService {

    private final ParcelRepository repo;
    private final ClientRepository clientRepo;
    private final ValidationService valService = new ValidationService();

    public ParcelService(ParcelRepository repo, ClientRepository clientRepo) {
        this.repo = repo;
        this.clientRepo = clientRepo;

    }

    @Override
    public Parcel createParcel(double weight, ParcelCategory category, Client sender, Client recipient) {
        valService.validateWeight(weight);
        valService.validateCategory(category);
        valService.validateClient(sender);
        valService.validateClient(recipient);

        int senderId = clientRepo.save(sender);
        int receiverId = clientRepo.save(recipient);

        double cost = calculateCost(weight);

        Parcel parcel = new Parcel.Builder(weight, category, sender, recipient)
                .withCost(cost)
                .withStatus(ParcelStatus.CREATED)
                .withCreatedAt(LocalDateTime.now())
                .build();


        repo.save(parcel,senderId,receiverId);

        return parcel;
    }


@Override
public List<Parcel> getAllParcels() {
    return repo.getAll();
}

@Override
public List<Parcel> getParcelsByCategory(ParcelCategory category){
        return repo.getAll().stream()
                .filter(parcel -> parcel.getCategory() == category)
                .toList();
}
@Override
public Parcel getFullParcelById(int id){
        return repo.getFullById(id);
}
@Override
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
public double calculateCost(double weight, ParcelCategory category){
    double base = weight <= 10 ? 2700 : 2700 + ((weight / 10) * 777);
    if(category == ParcelCategory.EXPRESS){
        return base + 1500;
    }
    return base;
}
}