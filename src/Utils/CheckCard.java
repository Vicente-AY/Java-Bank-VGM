package Utils;

import Account.*;
import Person.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Clase que comprueba si una tarjeta ha cumplido su fecha de caducidad
 */
public class CheckCard {

    /**
     * Clase que cancela las tarjetas que lleguen a su fecha de caducidad
     * @param persons
     */
    public static void cardCheck(ArrayList<Person> persons){

        String expiration = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yyyy"));

        for(Person person : persons){
            if(person instanceof User){
                User user = (User)person;
                for(BankAccount bankAccount : user.getBankAccounts()){
                    if(bankAccount.getCards() != null){
                        for(Card card : bankAccount.getCards()){
                            //de cumplir la condicion cancelamos la tarjeta
                            if(card.getExpirationDate().equals(expiration)){
                                card.setActive(false);
                            }
                        }
                    }
                }
            }
        }
    }
}
