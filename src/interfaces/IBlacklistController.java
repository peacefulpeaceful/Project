package interfaces;

public interface IBlacklistController {
    String addClientToBlackList(String name, String surname, String reason);
    String isClientBlackListed(String name, String surname);
}
