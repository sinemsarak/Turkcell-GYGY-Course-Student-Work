package com.bankingapplication;

import java.util.List;

public class Account {

    private static int counter = 0;
    private static final double TRANSFER_FEE = 6.0;

    private String accountNumber;
    private double balance;

    public Account() {
        counter++;
        this.accountNumber = "A" + String.format("%03d", counter);
        this.balance = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }
        balance += amount;
        System.out.println("Deposit successful. Current balance: " + balance + " TL");
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return false;
        }
        balance -= amount;
        System.out.println("Withdrawal successful. Current balance: " + balance + " TL");
        return true;
    }

    public void transfer(String targetAccountNumber, String targetOwnerFullName, double amount, List<Customer> customers) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be greater than zero.");
            return;
        }

        double totalDeduction = amount + TRANSFER_FEE;
        if (totalDeduction > balance) {
            System.out.println("Insufficient balance. Amount + transfer fee (" + TRANSFER_FEE + " TL) exceeds your balance.");
            return;
        }

        Account targetAccount = null;
        Customer targetOwner = null;

        for (Customer customer : customers) {
            for (Account account : customer.getAccounts()) {
                if (account.getAccountNumber().equals(targetAccountNumber)) {
                    targetAccount = account;
                    targetOwner = customer;
                    break;
                }
            }
            if (targetAccount != null) break;
        }

        if (targetAccount == null) {
            System.out.println("Account not found.");
            return;
        }

        if (!targetOwner.getFullName().equalsIgnoreCase(targetOwnerFullName)) {
            System.out.println("Account owner name does not match. Transfer cancelled.");
            return;
        }

        if (targetAccount.getAccountNumber().equals(this.accountNumber)) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }

        balance -= totalDeduction;
        targetAccount.balance += amount;

        System.out.println("Transfer successful.");
        System.out.println("Transferred: " + amount + " TL | Fee: " + TRANSFER_FEE + " TL");
        System.out.println("Your remaining balance: " + balance + " TL");
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account No: " + accountNumber + " | Balance: " + balance + " TL";
    }
}
