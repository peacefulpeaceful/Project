package repository;

import model.Client;

public interface IClientRepository {
    int save(Client client) throws Exception;
    Client findByNameSurname(String name, String surname) throws Exception;
}
