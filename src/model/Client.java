package model;

public class Client {
    private int id;
    private String name;
    private String surname;
    private String address;


    public Client(){}

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client{id=" + id +
                ", name='" + name + "'" +
                ", surname='" + surname + "'" +
                ", address='" + address + "'}";
    }
}