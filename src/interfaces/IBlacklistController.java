package interfaces;

public interface IBlacklistController {
    String addClientToBlacklist(String name, String surname, String reason);
    String isClientBlacklisted(String name, String surname);
}
