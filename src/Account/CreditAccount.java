package Account;
import Person.Person;
import Utils.*;
import Person.User;

import java.util.ArrayList;

/**
 * Clase que representa una cuenta de crédito bancaria.
 * Hereda de {BankAccount y añade funcionalidades específicas para
 * la gestión de límites de crédito y tasas de interés/porcentaje.
 */
public class CreditAccount extends BankAccount {

    Data dataAccess = new Data();
    double creditLimit = 0.0;
    double creditPercentage = 0.0;

    /**
     * Constructor para inicializar una cuenta de crédito con todos sus parámetros.
     * @param entity           Código de la entidad (4 dígitos).
     * @param office           Código de la oficina (4 dígitos).
     * @param accNumber        Número de cuenta (10 dígitos).
     * @param dc               Dígitos de control.
     * @param IBAN             Código IBAN completo.
     * @param accountAlias     Apodo de la cuenta.
     * @param creditLimit      Límite de crédito otorgado.
     * @param creditPercentage Porcentaje de interés o comisión aplicable.
     * @see BankAccount
     */
    public CreditAccount(String entity, String office, String accNumber, String dc, String IBAN, String accountAlias, double creditLimit, double creditPercentage){
        super(entity, office, accNumber, dc, IBAN, accountAlias);
        this.creditLimit = creditLimit;
        this.creditPercentage = creditPercentage;
    }

    /**
     * Realiza un depósito de efectivo en la cuenta de crédito.
     * * @param amount  Cantidad a depositar.
     */
    @Override
    public void deposit(double amount) {
    }

    /**
     * Realiza una retirada de efectivo, validando el límite de crédito.
     * * @param amount  Cantidad a retirar.
     */
    @Override
    public void withdraw(double amount) {

    }

    /**
     * Transfiere fondos desde esta cuenta de crédito hacia otra.
     * @param persons ArrayList para buscar la cuenta bancaria destino
     */
    @Override
    public void transfer(ArrayList<Person> persons) {

    }

    /**
     * Permite pagar la recarga de una tarjeta SIM utilizando el crédito disponible.
     * @param amount  Costo de la recarga.
     */
    @Override
    public void rechargeSIM(double amount) {

    }

    /**
     * Permite al usuario interactuar con la cuenta seleccionada.
     * @param user Usuario que realiza la acción.
     */
    @Override
    public void selectAccount(User user) {

    }

    /**
     * Metodo para registrar una nueva cuenta de crédito en el sistema.
     * Calcula los datos bancarios necesarios y vincula la cuenta al perfil del usuario actual.
     * * @param newCreditAccount Instancia temporal de la cuenta con los datos de configuración.
     */
    public void  createCreditAccount(ArrayList<Person> persons) {

        System.out.println("Please introduce de ID of the client the new bank account is for");
        String id = sc.nextLine();
        Person currentUser = null;
        CreditAccount newCreditAccount = null;
        for(Person person : persons){
            if(person instanceof User){
                if(person.getId().equals(id)){
                    currentUser = person;
                }
                else{
                    System.out.println("Invalid ID");
                    return;
                }
            }
            else{
                System.out.println("This ID is not an user");
                return;
            }
        }



        String entity="", office="", dc="", accNumber="", IBAN="", alias ="";
        double limit = 0.0, percentage = 0.0;

        entity = newCreditAccount.getEntity();
        office = newCreditAccount.getOffice();
        accNumber = newCreditAccount.accountNumber();

        dc = newCreditAccount.calcDC(entity, office, accNumber);
        IBAN = newCreditAccount.calcIBAN(entity, office, accNumber);
        alias = newCreditAccount.accountAlias();

        limit = newCreditAccount.creditLimit;
        percentage = newCreditAccount.creditPercentage;

        newCreditAccount = new CreditAccount(entity, office, accNumber, dc, IBAN, alias, limit, percentage);
        ((User) currentUser).getBankAccounts().add(newCreditAccount);
        System.out.println("The account has been created");
    }
}
