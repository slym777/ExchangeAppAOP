package model;

import auth.Authenticable;

import java.util.ArrayList;
import java.util.List;

public class User implements Authenticable {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String country;
    private String address;
    private List <Transaction> listOfTransactions = new ArrayList<>();

    public User() { }

    public User(String firstName, String lastName, String username, String password, String country, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.country = country;
        this.address = address;
    }

    public User(String firstName, String lastName, String username, String password, String country, String address, List<Transaction> listOfTransactions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.country = country;
        this.address = address;
        this.listOfTransactions = listOfTransactions;
    }

    public List<Transaction> getListOfTransactions() {
        return listOfTransactions;
    }

    public void setListOfTransactions(List<Transaction> listOfTransactions) {
        this.listOfTransactions = listOfTransactions;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addTransaction(Transaction transaction) {
        listOfTransactions.add(transaction);
    }

}
