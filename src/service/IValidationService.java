package service;

import model.Client;
import model.ParcelCategory;

public interface IValidationService {
    void validateWeight(double weight);
    void validateClient(Client client);
    void validateCategory(ParcelCategory category);
}
