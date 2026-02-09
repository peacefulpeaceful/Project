package service;

import repository.BlacklistRepository;

public class BlacklistService implements IBlacklistService {
    private final BlacklistRepository repo;

    public BlacklistService(BlacklistRepository repo) {
        this.repo = repo;
    }

    @Override
    public void addToBlacklist(int clientId, String reason) {
        repo.add(clientId, reason);
    }

    @Override
    public boolean isBlacklisted(int clientId) {
        return repo.isBlacklisted(clientId);
    }
}