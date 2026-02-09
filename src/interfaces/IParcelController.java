package interfaces;

public interface IParcelController {
    String createParcel(double weight,
                        String category,
                        String senderName, String senderSurname, String senderAddress,
                        String receiverName, String receiverAddress);

    String getAllParcels();

    String getParcelsByCategory(String category);
    String getFullParcelDescription(int id);

    String changeStatus(int id, String newStatus);
    String cancelParcel(int id);

    String findParcelsByPerson(String name, String surname);

    String getClientHistory(String name, String surname);

    String getDeliveredRevenue();

    String addClientToBlacklist(String name, String surname, String reason);
}
