package controllers;

import controllers.interfaces.IParcelController;
import model.Client;
import model.Parcel;
import model.ParcelStatus;
import service.ParcelService;

import java.util.List;

public class ParcelController implements IParcelController {

    private final ParcelService service;

    public ParcelController(ParcelService service) {
        this.service = service;
    }

    @Override
    public String createParcel(double weight,
                               String senderName, String senderSurname, String senderAddress,
                               String receiverName, String receiverAddress) {
        try {
            Client sender = new Client();
            sender.setName(senderName);
            sender.setSurname(senderSurname);
            sender.setAddress(senderAddress);

            Client receiver = new Client();
            receiver.setName(receiverName);
            receiver.setAddress(receiverAddress);

            Parcel created = service.createParcel(weight, sender, receiver);
            return "Parcel created and saved to DB. ID=" + created.getId() + ", status=" + created.getStatus();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String getAllParcels() {
        try {
            List<Parcel> list = service.getAllParcels();
            if (list.isEmpty()) return "No parcels in DB.";

            StringBuilder sb = new StringBuilder();
            for (Parcel p : list) sb.append(p).append("\n");
            return sb.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String changeStatus(int id, String newStatus) {
        try {
            ParcelStatus status = ParcelStatus.valueOf(newStatus.toUpperCase());
            service.changeStatus(id, status);
            return "Status updated in DB for id=" + id + " to " + status;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}