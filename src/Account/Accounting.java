package Account;
import Person.Person;
import Person.User;
import Shop.ShopItem;

import java.util.ArrayList;

interface Accounting
{
    void deposit(double amount);
    void withdraw(double amount);
    void transfer(ArrayList<Person> persons);
    void rechargeSIM(double amount);
    void shopPayment(double amount, ShopItem item);
    void cardPayment(double amount, ShopItem item, Card card);
}
/**
 * Interfaz con métodos
 * @param deposit
 * @param withdraw
 * @param transfer
 * @param rechargeSIM
 * @param selectAccount
 */