package Utils;
import Account.*;
import Person.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Clase comprueba las deudas de los usuarios. Las ejecuta de cumplir las condiciones e implementa restricciones a
 * los usuarios que no las liquidan
 */
public class CheckDebt {

    Data dataAcc = new Data();
    HashMap<String, String> debtors = new HashMap<String, String>();

    /**
     * Metodo que recorre todas las cuentas de los usuarios buscando deudas pendientes, de encontrarlas las ejecutará
     * @param persons ArrayList de los usuarios del sistema
     * @param date Dia en el que se ejecuta la deuda o se marca al usurio como deudor
     */
    public void collectDebts(ArrayList<Person> persons, String date) {

        BankAccount debtAccount = null;
        BankAccount payBankAccount = null;

        //buscamos entre los usuarios a los clientes con deudas
        for(Person person : persons) {
            if(person instanceof User){
                for(BankAccount bankAccount : ((User) person).getBankAccounts()){
                    if(bankAccount.getBalance() < 0){
                        debtAccount = bankAccount;
                        //de encontrar deuda almacenamos la misma en positivo
                        double debtAmount = -(debtAccount.getBalance());
                        //volvemos a buscar cuentas bancarias, esta vez con balance positivo y almacenamos su valor
                        for(BankAccount bankAccount2 : ((User) person).getBankAccounts()){
                            if(bankAccount2.getBalance() > 0){
                                payBankAccount = bankAccount2;
                                double amount = payBankAccount.getBalance();
                                //si la deuda es menor que el balance positivo se ejecuta directamente
                                if(debtAmount <= amount){
                                    payBankAccount.setBalance(amount - debtAmount);
                                    debtAccount.setBalance(0);
                                    break;
                                }
                                //si la deuda es mayor, se calcula la diferencia
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
        //llamamos a la funcion applyRestrictions
        applyRestrictions(persons, date);
    }

    /**
     * Clase que se ejecuta después de collectDebs. Comprueba si, despues de pagar las deudas de forma automatica
     * el cliente continua teniendo deudasy se le aplican restriccicones
     * @param persons lista de usuarios del sistema
     * @param date fecha de inicio de la ejecucion de la deuda
     */
    public void applyRestrictions(ArrayList<Person> persons, String date) {

        String regex = "[,//.\\s]";
        String[] splitDate = date.split(regex);
        int month = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);

        //cargamos de Data el HashMap de Deudores
        debtors = dataAcc.chargeDebtors();

        //volvemos a buscar las cuentas de clientes en busca de deuda residual
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
                //de tener deuda y no estar en la lista de dudores, lo introducimos en el HashMap y marcamos como deudor
                if(hasActiveDebt){
                    if(!debtors.containsKey(user.getId())){
                    user.setDebtor(true);
                    debtors.put(user.getId(), date);
                    }
                    /*de ya estar la en la lista sacamos la fecha en la que se contrajo la deuda y realizamos el calculo
                    de los meses pasados*/
                    else{
                        String[] splitDateDebt = debtors.get(user.getId()).split(regex);
                        int monthDebt = Integer.parseInt(splitDateDebt[1]);
                        int yearDebt = Integer.parseInt(splitDateDebt[2]);
                        int monthsPassed = (year - yearDebt) * 12 +  (month - monthDebt);

                        //si la antiguedad de la deuda es de 2 meses bloqueamos sus cuentas
                        if(monthsPassed == 1){
                            user.setBloquedAccounts(true);
                        }
                        //si la antiguedad tiene tres meses o mas bloqueamos sus cuentas ya acceso al sistema
                        if(monthsPassed >= 2){
                            user.setBloquedAccounts(true);
                            user.setActive(true);
                        }
                    }
                }
                /*si el usuario ha saldado sus deudas lo retiramos de la lista de dudores, desbloqueamos sus cuentas
                y no lo marcamos mas como deudor*/
                else {
                    debtors.remove(user.getId());
                    user.setDebtor(false);
                    user.setBloquedAccounts(false);
                }
            }
        }
        //guardamos la lista de dudores
        dataAcc.saveDebtors(debtors);
    }
}