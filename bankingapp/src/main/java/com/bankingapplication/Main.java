package com.bankingapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Customer> customers = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Customer alice = new Customer("Alice Johnson", "alice123");
        alice.addAccount(new Account());
        customers.add(alice);

        Customer bob = new Customer("Bob Smith", "bob456");
        bob.addAccount(new Account());
        customers.add(bob);

        Customer carol = new Customer("Carol White", "carol789");
        carol.addAccount(new Account());
        customers.add(carol);

        boolean running = true;
        while (running) {
            System.out.println("\n=== Banking Application ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> register();
                case "2" -> login();
                case "0" -> {
                    System.out.println("Goodbye.");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    static void register() {
        System.out.println("\n--- Register New Customer ---");

        System.out.print("Full name: ");
        String fullName = scanner.nextLine().trim();

        if (fullName.isEmpty()) {
            System.out.println("Full name cannot be empty.");
            return;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (password.isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }

        Customer newCustomer = new Customer(fullName, password);
        customers.add(newCustomer);

        System.out.println("Registration successful.");
        System.out.println("Your customer number: " + newCustomer.getCustomerNumber());
    }

    static void login() {
        System.out.println("\n--- Login ---");

        System.out.print("Customer number: ");
        String customerNumber = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        for (Customer customer : customers) {
            if (customer.getCustomerNumber().equals(customerNumber) && customer.getPassword().equals(password)) {
                System.out.println("Login successful. Welcome, " + customer.getFullName() + "!");
                customerMenu(customer);
                return;
            }
        }

        System.out.println("Invalid customer number or password.");
    }

    static void customerMenu(Customer customer) {
        boolean active = true;
        while (active) {
            System.out.println("\n--- Account Menu | " + customer.getFullName() + " ---");
            printAccounts(customer);

            System.out.println("\n1. Open new account");
            System.out.println("2. Select account");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> openAccount(customer);
                case "2" -> selectAccount(customer);
                case "0" -> {
                    System.out.println("Logged out.");
                    active = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    static void printAccounts(Customer customer) {
        List<Account> accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            System.out.println("Your accounts:");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + accounts.get(i));
            }
        }
    }

    static void openAccount(Customer customer) {
        Account account = new Account();
        customer.addAccount(account);
        System.out.println("Account created. Account number: " + account.getAccountNumber());
    }

    static void selectAccount(Customer customer) {
        List<Account> accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("You have no accounts.");
            return;
        }

        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine().trim();

        Account selected = null;
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                selected = account;
                break;
            }
        }

        if (selected == null) {
            System.out.println("Account not found.");
            return;
        }

        accountMenu(selected, customer);
    }

    static void accountMenu(Account account, Customer customer) {
        boolean active = true;
        while (active) {
            System.out.println("\n--- " + account.getAccountNumber() + " | Balance: " + account.getBalance() + " TL ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Amount: ");
                    double amount = parseAmount();
                    if (amount > 0) account.deposit(amount);
                }
                case "2" -> {
                    System.out.print("Amount: ");
                    double amount = parseAmount();
                    if (amount > 0) account.withdraw(amount);
                }
                case "3" -> {
                    System.out.print("Target account number: ");
                    String targetAccountNumber = scanner.nextLine().trim();
                    System.out.print("Account owner full name: ");
                    String targetOwnerName = scanner.nextLine().trim();
                    System.out.print("Amount: ");
                    double amount = parseAmount();
                    if (amount > 0) account.transfer(targetAccountNumber, targetOwnerName, amount, customers);
                }
                case "0" -> active = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    static double parseAmount() {
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                System.out.println("Amount must be greater than zero.");
                return -1;
            }
            return amount;
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return -1;
        }
    }
}
