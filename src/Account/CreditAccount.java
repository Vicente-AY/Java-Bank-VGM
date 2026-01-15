package Account;

import Person.User;

public class CreditAccount extends BankAccount {
    double creditLimit = 0.0;
    double creditPercentage = 0.0;
    public CreditAccount(String entity, String office, String accNumber, String dc, String IBAN, String accountAlias, double creditLimit, double creditPercentage){
        super(entity, office, accNumber, dc, IBAN, accountAlias);
        this.creditLimit = creditLimit;
        this.creditPercentage = creditPercentage;
    }

    public CreditAccount(String entity, String office, String accNumber, String dc, String IBAN, double creditLimit, double creditPercentage){
        super(entity, office, accNumber, dc, IBAN);
        this.creditLimit = creditLimit;
        this.creditPercentage = creditPercentage;
    }

    @Override
    public void deposit(int amount, BankAccount account) {

    }

    @Override
    public void withdraw(int amount, BankAccount account) {

    }

    @Override
    public void transfer(double amount, BankAccount account) {

    }

    @Override
    public void rechargeSIM(int amount, BankAccount account) {

    }

    @Override
    public void selectAccount(User user) {

    }
}
