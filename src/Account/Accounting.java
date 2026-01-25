package Account;
import Person.Person;
import Person.User;
import java.util.ArrayList;

interface Accounting
{
    void deposit(int amount, BankAccount account);
    void withdraw(int amount, BankAccount account);
    void transfer(double amount, BankAccount account, ArrayList<Person> persons);
    void rechargeSIM(int amount, BankAccount account);
    void selectAccount(User user);
}
/**
 * Interfaz con métodos
 * @param deposit
 * @param withdraw
 * @param transfer
 * @param rechargeSIM
 * @param selectAccount
 */