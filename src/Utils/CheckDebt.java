package Utils;
import Account.*;
import Person.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Clase comprueba las deudas de los usuarios y las ejecuta de cumplir las condiciones
 */
public class CheckDebt {

    Data dataAcc = new Data();
    HashMap<String, String> debtors = new HashMap<String, String>();

    public void collectDebts(ArrayList<Person> persons, String date) {

        BankAccount debtAccount = null;
        BankAccount payBankAccount = null;

        for(Person person : persons) {
            if(person instanceof User){
                for(BankAccount bankAccount : ((User) person).getBankAccounts()){
                    if(bankAccount.getBalance() < 0){
                        debtAccount = bankAccount;
                        double debtAmount = -(debtAccount.getBalance());
                        for(BankAccount bankAccount2 : ((User) person).getBankAccounts()){
                            if(bankAccount2.getBalance() > 0){
                                payBankAccount = bankAccount2;
                                double amount = payBankAccount.getBalance();
                                if(debtAmount <= amount){
                                    payBankAccount.setBalance(amount - debtAmount);
                                    debtAccount.setBalance(0);
                                    break;
                                }
                                else{
                                    debtAccount.setBalance(debtAccount.getBalance() + amount);
                                    payBankAccount.setBalance(0);
                                }
                            }
                        }
                    }
                }
            }
        }
        applyRestrictions(persons, date);
    }

    public void applyRestrictions(ArrayList<Person> persons, String date) {

        String regex = "[,//.\\s]";
        String[] splitDate = date.split(regex);
        int month = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);

        debtors = dataAcc.chargeDebtors();

        for(Person person : persons){
            if(person instanceof User){
                User user = (User) person;
                boolean hasActiveDebt = false;
                for(BankAccount bankAccount : user.getBankAccounts()){
                    if(bankAccount.getBalance() < 0){
                        hasActiveDebt = true;
                        break;
                    }
                }
                if(hasActiveDebt){
                    if(!debtors.containsKey(user.getId())){
                    user.setDebtor(true);
                    debtors.put(user.getId(), date);
                    }
                    else{
                        String[] splitDateDebt = debtors.get(user.getId()).split(regex);
                        int monthDebt = Integer.parseInt(splitDateDebt[1]);
                        int yearDebt = Integer.parseInt(splitDateDebt[2]);
                        int monthsPassed = (year - yearDebt) * 12 +  (month - monthDebt);

                        if(monthsPassed == 1){
                            user.setBloquedAccounts(true);
                        }
                        if(monthsPassed >= 2){
                            user.setBloquedAccounts(true);
                            user.setActive(true);
                        }
                    }
                }
                else {
                    debtors.remove(user.getId());
                    user.setDebtor(false);
                    user.setBloquedAccounts(false);
                }
            }
        }
        dataAcc.saveDebtors(debtors);
    }
}