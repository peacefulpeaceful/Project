package service;

public interface IBlacklistService {
    void addToBlacklist(int clientId, String reason);
    boolean isBlacklisted(int clientId);
}
