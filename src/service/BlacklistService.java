package service;

import repository.BlacklistRepository;

public class BlacklistService {
    private final BlacklistRepository repo;

    public BlacklistService(BlacklistRepository repo) {
        this.repo = repo;
    }

    public void addToBlacklist(int clientId, String reason) {
        repo.add(clientId, reason);
    }

    public boolean isBlacklisted(int clientId) {
        return repo.isBlacklisted(clientId);
    }
}