package Account;
import Utils.*;
import Person.User;

/**
 * Clase que permite realizar acciones con la cuenta bancaria
 */
public class CreditAccount extends BankAccount {
    /**
     * @param creditLimit Doble que representa el límite de crédito del usuario
     * @param creditPercentage Doble que representa el porcentaje de crédito del usuario
     */
    Data dataAccess = new Data();
    double creditLimit = 0.0;
    double creditPercentage = 0.0;

    /**
     * Constructores con parámetros
     * @param creditLimit Establece el límite de crédito del usuario
     * @param creditPercentage Establece el porcentaje de crédito del usuario
     * @see BankAccount
     */

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

    /**
     * Metodo que permite el depósito en la cuenta bancaria
     * @see DebitAccount
     */
    @Override
    public void deposit(int amount, BankAccount account) {

    }

    /**
     * Metodo que permite retirar ingresos de la cuenta bancaria
     * @see DebitAccount
     */
    @Override
    public void withdraw(int amount, BankAccount account) {

    }

    /**
     * Metodo que permite transferir ingresos a otra cuenta
     * @see DebitAccount
     */
    @Override
    public void transfer(double amount, BankAccount account) {

    }

    /**
     * Metodo que permite recargar la tarjeta SIM del usuario
     * @see DebitAccount
     */
    @Override
    public void rechargeSIM(int amount, BankAccount account) {

    }

    /**
     * Metodo que permite seleccionar la cuenta del usuario
     * @see DebitAccount
     */
    @Override
    public void selectAccount(User user) {

    }

    public CreditAccount  createCreditAccount(CreditAccount newCreditAccount, User currentUser) {
        String entity="", office="", dc="", accNumber="", IBAN="", alias ="";

        entity = newCreditAccount.getEntity();
        office = newCreditAccount.getOffice();
        accNumber = newCreditAccount.accountNumber();

        dc = newCreditAccount.calcDC(entity, office, accNumber);
        IBAN = newCreditAccount.calcIBAN(entity, office, accNumber);
        alias = newCreditAccount.accountAlias();


        newCreditAccount = new CreditAccount(entity, office, accNumber, dc, IBAN, alias, 0.0, 0.0);
        currentUser.getBankAccounts().add(newCreditAccount);
        dataAccess.writeBankAccounts(currentUser.getBankAccounts());
        System.out.println("Your account has been created");
        return newCreditAccount;
    }
}
