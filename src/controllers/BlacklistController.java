package controllers;

import interfaces.IBlacklistController;
import repository.ClientRepository;
import security.Role;
import security.RoleGuard;
import security.UserSession;
import service.IBlacklistService;

public class BlacklistController implements IBlacklistController {
    private final IBlacklistService blacklistService;
    private final ClientRepository clientRepository;
    private final UserSession session;

    public BlacklistController(IBlacklistService blacklistService, ClientRepository clientRepository) {
        this.blacklistService = blacklistService;
        this.clientRepository = clientRepository;
        this.session = UserSession.getInstance();
    }

    @Override
    public String addClientToBlacklist(String name, String surname, String reason) {
        try {
            RoleGuard.getInstance().requireAny(session.getCurrentRole(), Role.ADMIN, Role.MANAGER);
            int clientId = resolveClientId(name, surname);
            blacklistService.addToBlacklist(clientId, reason);
            return "Client added to blacklist.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String isClientBlacklisted(String name, String surname) {
        try {
            RoleGuard.getInstance().requireAny(session.getCurrentRole(), Role.ADMIN, Role.MANAGER);
            int clientId = resolveClientId(name, surname);
            boolean blocked = blacklistService.isBlacklisted(clientId);
            return blocked ? "Client is blacklisted." : "Client is not blacklisted.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    private int resolveClientId(String name, String surname) throws Exception {
        var client = clientRepository.findByNameSurname(name, surname);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }
        return client.getId();
    }
}
