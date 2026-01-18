package controllers.interfaces;

public interface IParcelController {
    String createParcel(double weight,
                        String senderName, String senderSurname, String senderAddress,
                        String receiverName, String receiverAddress);

    String getAllParcels();

    String changeStatus(int id, String newStatus);
}