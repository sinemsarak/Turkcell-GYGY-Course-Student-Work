package com.bankingapplication;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private static int counter = 0;

    private String fullName;
    private String customerNumber;
    private String password;
    private List<Account> accounts = new ArrayList<>();

    public Customer(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
        counter++;
        this.customerNumber = "C" + String.format("%03d", counter);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public static int getCounter() {
        return counter;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{fullName='" + fullName + "', customerNumber='" + customerNumber + "'}";
    }
}
