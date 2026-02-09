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
    private final IBlacklistService blacklistService;
    private final IValidationService valService;

    public ParcelService(ParcelRepository repo, ClientRepository clientRepo, IValidationService valService, IBlacklistService blacklistService) {
        this.repo = repo;
        this.clientRepo = clientRepo;
        this.valService = valService;
        this.blacklistService = blacklistService;
    }

    @Override
    public Parcel createParcel(double weight, ParcelCategory category, Client sender, Client recipient) {
        valService.validateWeight(weight);
        valService.validateCategory(category);
        valService.validateClient(sender);
        valService.validateClient(recipient);

        Client existingSender = findOrCreateClient(sender);
        Client existingReceiver = findOrCreateClient(recipient);

        if (blacklistService.isBlacklisted(existingSender.getId())
                || blacklistService.isBlacklisted(existingReceiver.getId())) {
            throw new IllegalStateException("Sender or receiver is blacklisted");

            int senderId;
            int receiverId;

            try{
                senderId = existingSender.getId();
                receiverId = existingReceiver.getId()
        }catch (Exception e){
            throw new RuntimeException("Client save failed " + e.getMessage());
        }
            Parcel parcel = new Parcel.Builder(weight, category, existingSender, existingReceiver)
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

        @Override
        public void cancelParcel(int parcelId) {
            Parcel existing = repo.getById(parcelId);
            if (existing == null) {
                throw new IllegalArgumentException("Parcel not found");
            }
            if (existing.getStatus() != ParcelStatus.CREATED) {
                throw new IllegalStateException("Can cancel only before IN_TRANSIT");
            }
            repo.updateStatus(parcelId, ParcelStatus.CANCELLED);
        }
        @Override
        public List<Parcel> getParcelsByPerson(String name, String surname){
            return repo.getAll().stream()
                    .filter(parcel -> matchesSenderOrReceiver(parcel, name, surname))
                    .toList();
        }
            @Override
            public List<Parcel> getParcelsSentBy(String name, String surname){
            return repo.getAll().stream()
                    .filter(parcel -> matchesSender(parcel, name, surname))
                    .toList();
        }

        @Override
        public List<Parcel> getParcelsReceivedBy(String name, String surname){
            return repo.getAll().stream()
                    .filter(parcel -> matchesReceiver(parcel, name, surname))
                    .toList();
        }

        @Override
        public double getDeliveredRevenue() {
            return repo.getDeliveredRevenue();
        }

        @Override
        public void addClientToBlacklist(String name, String surname, String reason) {
            try {
                Client client = clientRepo.findByNameSurname(name, surname);
                if (client == null) {
                    throw new IllegalArgumentException("Client not found");
                }
                blacklistService.addToBlacklist(client.getId(), reason);
            } catch (Exception e) {
                throw new RuntimeException("Blacklist update failed: " + e.getMessage());
            }
        }
        private boolean isValidStatusChange(ParcelStatus current, ParcelStatus next){
            return (current == ParcelStatus.CREATED && next == ParcelStatus.IN_TRANSIT)
                    || (current == ParcelStatus.IN_TRANSIT && next == ParcelStatus.DELIVERED)
                    || (current == ParcelStatus.CREATED && next == ParcelStatus.CANCELLED);
        }
        private Client findOrCreateClient(Client client) {
            try {
                Client existing = clientRepo.findByNameSurname(client.getName(), client.getSurname());
                if (existing != null) {
                    return existing;
                }
                int id = clientRepo.save(client);
                client.setId(id);
                return client;
            } catch (Exception e) {
                throw new RuntimeException("Client lookup failed: " + e.getMessage());
            }
        }
        private boolean matchesSenderOrReceiver(Parcel parcel, String name, String surname) {
            return matchesSender(parcel, name, surname) || matchesReceiver(parcel, name, surname);
        }

        private boolean matchesSender(Parcel parcel, String name, String surname) {
            Client sender = parcel.getSender();
            return sender != null
                    && name.equals(sender.getName())
                    && surname.equals(sender.getSurname());
        }

        private boolean matchesReceiver(Parcel parcel, String name, String surname) {
            Client receiver = parcel.getRecipient();
            return receiver != null
                    && name.equals(receiver.getName())
                    && surname.equals(receiver.getSurname());
        }


    }
    return base;
}

}