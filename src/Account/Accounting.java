package Account;
import Person.Person;
import Person.User;
import java.util.ArrayList;

interface Accounting
{
    void deposit(double amount);
    void withdraw(double amount);
    void transfer(ArrayList<Person> persons);
    void rechargeSIM(double amount);
}
/**
 * Interfaz con métodos
 * @param deposit
 * @param withdraw
 * @param transfer
 * @param rechargeSIM
 * @param selectAccount
 */