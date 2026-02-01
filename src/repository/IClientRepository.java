package repository;

import model.Client;

public interface IClientRepository {
    int save(Client client) throws Exception;
}
