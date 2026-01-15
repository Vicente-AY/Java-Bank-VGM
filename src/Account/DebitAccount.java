package Account;

import Exceptions.InputNumberException;
import Person.User;


import java.util.InputMismatchException;
import java.util.Scanner;

public class DebitAccount extends BankAccount {

    Scanner sc  = new Scanner(System.in);

    public DebitAccount(String entity, String office, String accNumber, String dc, String IBAN, String accountAlias) {
        super(entity, office, accNumber, dc, IBAN, accountAlias);
    }
    public DebitAccount(String entity, String office, String accNumber, String dc, String IBAN) {
        super(entity, office, accNumber, dc, IBAN);
    }

    @Override
    public void deposit(int amount, BankAccount account) {

        account.balance += amount;
        System.out.println("Deposited " + amount);
        System.out.println("New Balance: " + account.balance);
    }

    @Override
    public void withdraw(int amount, BankAccount account) {

        if (account.balance <= 0 || account.balance - amount < 0){
            System.out.println("Insufficient funds");
        }
        else{
            account.balance -= amount;
            System.out.println("Operation successful");
            System.out.println("New balance in " + account.accNumber + " is: " + account.balance);
        }
    }

    @Override
    public void transfer(double amount, BankAccount account) {


        try{
            String sourceAcc =  account.accNumber;
            System.out.println("Please enter the destination account number\n");
            String destinationAcc =  sc.nextLine();
            System.out.println("Please enter the amount to be transferred (With decimals)\n");
            double ammount = sc.nextDouble();

            if(ammount > account.balance){
                System.out.println("Insufficient funds");
            }
            else{
                account.balance -= ammount;
                BankAccount destAcc = null;
                for(int i = 0; i < accounts.size(); i++){
                    if(accounts.get(i).accNumber.equals(destinationAcc)){
                        accounts.get(i).balance += ammount;
                        destAcc = accounts.get(i);
                    }
                }
                System.out.println("Operation successful");
                System.out.println("New balance in " + sourceAcc + " is: " + account.balance);
                System.out.println("New balance in " + destinationAcc + " is: " + destAcc.balance);
            }
        }
        catch(InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void rechargeSIM(int amount, BankAccount account) {
        System.out.println("Input the destination phone number\n");
        try{
            String number =  sc.nextLine();
            while( number.length() != 9){
                System.out.println("Please enter a valid phone number (9 digits)\n");
                number = sc.nextLine();
            }
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void selectAccount(User user) {

        BankAccount foundBankAccount = null;
        System.out.println("Select the account you want to use by typing the number of the option");
        for(int i = 0; i < user.bankAccounts.size(); i++) {
            String aliasBA = user.bankAccounts.get(i).accountAlias;
            System.out.println("Option " + (i + 1) + ": " + aliasBA);
        }
        try {
            int option = sc.nextInt();
            sc.nextLine();
            foundBankAccount = user.bankAccounts.get(option - 1);
            System.out.println("Selected account: " + foundBankAccount.accNumber + " Balance: " + foundBankAccount.balance);

        }
        catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }

    }
}
