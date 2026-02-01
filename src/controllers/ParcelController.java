package controllers;

import interfaces.IParcelController;
import model.Client;
import model.Parcel;
import model.ParcelCategory;
import model.ParcelStatus;
import security.Role;
import security.RoleGuard;
import security.UserSession;
import service.ParcelService;


import java.util.List;

public class ParcelController implements IParcelController {

    private final ParcelService service;
    private final UserSession session;

    public ParcelController(ParcelService service) {
        this.service = service;
        this.session = UserSession.getInstance();
    }


    @Override
    public String createParcel(double weight,
                               String category,
                               String senderName, String senderSurname, String senderAddress,
                               String receiverName, String receiverAddress) {
        try {
            RoleGuard.getInstance().requireAny(session.getCurrentRole(), Role.ADMIN, Role.MANAGER, Role.EDITOR);
            ParcelCategory parcelCategory = ParcelCategory.valueOf(category.toUpperCase());

            Client sender = new Client();
            sender.setName(senderName);
            sender.setSurname(senderSurname);
            sender.setAddress(senderAddress);

            Client receiver = new Client();
            receiver.setName(receiverName);
            receiver.setAddress(receiverAddress);

            Parcel created = service.createParcel(weight, parcelCategory, sender, receiver);
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
    public String getParcelsByCategory(String category) {
        try {
            RoleGuard.getInstance().requireAny(session.getCurrentRole(), Role.ADMIN, Role.MANAGER, Role.EDITOR);
            ParcelCategory parcelCategory = ParcelCategory.valueOf(category.toUpperCase());
            List<Parcel> list = service.getParcelsByCategory(parcelCategory);
            if (list.isEmpty()) return "No parcels in this categoyr";

            StringBuilder sb = new StringBuilder();
            for (Parcel p : list) sb.append(p).append("\n");
            return sb.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String getFullParcelDescription(int id) {
        try {
            RoleGuard.getInstance().requireAny(session.getCurrentRole(), Role.ADMIN, Role.MANAGER);
            Parcel parcel = service.getFullParcelById(id);
            if (parcel == null) return "Parcel not found";
            return parcel.getFullParcelDescription();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


    @Override
    public String changeStatus(int id, String newStatus) {
        try {
            RoleGuard.getInstance().requireAny(session.getCurrentRole(), Role.ADMIN, Role.MANAGER);
            ParcelStatus status = ParcelStatus.valueOf(newStatus.toUpperCase());
            service.changeStatus(id, status);
            return "Status updated in DB for id=" + id + " to " + status;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}