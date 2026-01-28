package service;

import model.Client;
import model.ParcelCategory;

public class ValidationService {
    public void validateWeight(double weight){
        if (weight <= 0){
            throw new IllegalArgumentException("Weigth must be > 0")
        }
        if (weight > 30){
            throw new IllegalArgumentException("Weight need to be no more than 30kg");
        }
    }

    public void validateClient(Client client){
        if (client == null){
            throw new IllegalArgumentException("Client is required");
        }
        if(isBlank(client.getName())){
            throw new IllegalArgumentException("Client name is required");
        }
        if(isBlank(client.getAddress())){
            throw new IllegalArgumentException("Client address is required");
        }
    }

    public void validateCategory(ParcelCategory category){
        if(category == null){
            throw new IllegalArgumentException("Category is required")
        }
    }

    private boolean isBlank(String value){
        return value == null || value.trim().isEmpty();
    }
}
