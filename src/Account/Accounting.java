package Account;

import Person.User;

interface Accounting
{
    void deposit(int amount, BankAccount account);
    void withdraw(int amount, BankAccount account);
    void transfer(double amount, BankAccount account);
    void rechargeSIM(int amount, BankAccount account);
    void selectAccount(User user);
}