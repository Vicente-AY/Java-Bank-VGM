package Account;

import Exceptions.InputNumberException;
import Person.User;


import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que permite interactuar con la cuenta bancaria en débito
 */
public class DebitAccount extends BankAccount {
    /**
     * @param sc Variable para llamar al escaner
     */
    Scanner sc  = new Scanner(System.in);
    /**
     * Constructores de la clase con parámetros
     * @see BankAccount
     */
    public DebitAccount(String entity, String office, String accNumber, String dc, String IBAN, String accountAlias) {
        super(entity, office, accNumber, dc, IBAN, accountAlias);
    }
    public DebitAccount(String entity, String office, String accNumber, String dc, String IBAN) {
        super(entity, office, accNumber, dc, IBAN);
    }

    /**
     * Metodo para depositar dinero en la cuenta bancaria
     * @param amount Entero que indica la cantidad expresada
     * @param account Llama a otra clase
     */
    @Override
    public void deposit(int amount, BankAccount account) {

        account.balance += amount;
        System.out.println("Deposited " + amount);
        System.out.println("New Balance: " + account.balance);
    }

    /**
     * Metodo para retirar dinero de la cuenta bancaria
     * @param amount Entero que indica la cantidad expresada
     * @param account Llama a otra clase
     */
    @Override
    public void withdraw(int amount, BankAccount account) {

        if (account.balance <= 0 || account.balance - amount < 0){
            System.out.println("Insufficient funds");
        }
        else{
            account.balance -= amount;
            System.out.println("Operation successful");
            System.out.println("New balance in " + account.accNumber + " is: " + account.balance);
        }
    }

    /**
     * Metodo para transferir dinero entre cuentas
     * @param amount Doble que indica la cantidad expresada
     * @param account Llama a otra clase
     */
    @Override
    public void transfer(double amount, BankAccount account) {
/**
 * @param sourceAcc Cadena que representa la cuenta de la que se va a sacar el dinero
 * @param destinationAcc Cadena que representa la cuenta que recibirá el dinero
 * @param ammount Doble que indica la cantidad expresada
 * @param destAcc Llama a otra clase para establecer la cuenta receptora
 */
        try{
            String sourceAcc =  account.accNumber;
            System.out.println("Please enter the destination account number\n");
            String destinationAcc =  sc.nextLine();
            System.out.println("Please enter the amount to be transferred (With decimals)\n");
            double ammount = sc.nextDouble();

            if(ammount > account.balance){
                System.out.println("Insufficient funds");
            }
            else{
                account.balance -= ammount;
                BankAccount destAcc = null;
                for(int i = 0; i < accounts.size(); i++){
                    if(accounts.get(i).accNumber.equals(destinationAcc)){
                        accounts.get(i).balance += ammount;
                        destAcc = accounts.get(i);
                    }
                }
                System.out.println("Operation successful");
                System.out.println("New balance in " + sourceAcc + " is: " + account.balance);
                System.out.println("New balance in " + destinationAcc + " is: " + destAcc.balance);
            }
        }
        catch(InputMismatchException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo para recargar la tarjeta SIM del usuario
     * @param amount Entero que indica la cantidad expresada
     * @param account Llama a otra clase
     */
    @Override
    public void rechargeSIM(int amount, BankAccount account) {
        /**
         * @param number Cadena que almacena el número de teléfono introducido
         */
        System.out.println("Input the destination phone number\n");
        try{
            String number =  sc.nextLine();
            while( number.length() != 9){
                System.out.println("Please enter a valid phone number (9 digits)\n");
                number = sc.nextLine();
            }
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que selecciona la cuenta del usuario
     * @param user Atributo que llama a otra clase
     */
    @Override
    public void selectAccount(User user) {
/**
 * @param foundBankAccount Variable de otra clase que indica si se ha encontrado la cuenta
 * @param aliasBA Cadena que muestra el apodo asociado a la cuenta bancaria
 * @param option Entero que recibe el número de la elección
 */
        BankAccount foundBankAccount = null;
        System.out.println("Select the account you want to use by typing the number of the option");
        for(int i = 0; i < user.bankAccounts.size(); i++) {
            String aliasBA = user.bankAccounts.get(i).accountAlias;
            System.out.println("Option " + (i + 1) + ": " + aliasBA);
        }
        try {
            int option = sc.nextInt();
            sc.nextLine();
            foundBankAccount = user.bankAccounts.get(option - 1);
            System.out.println("Selected account: " + foundBankAccount.accNumber + " Balance: " + foundBankAccount.balance);

        }
        catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }

    }

    public DebitAccount  createDebitAccount(DebitAccount newDebitAccount, User currentUser) {
        String entity="", office="", dc="", accNumber="", IBAN="", alias ="";

        entity = newDebitAccount.getEntity();
        office = newDebitAccount.getOffice();
        accNumber = newDebitAccount.accountNumber();

        dc = newDebitAccount.calcDC(entity, office, accNumber);
        IBAN = newDebitAccount.calcIBAN(entity, office, accNumber);
        alias = newDebitAccount.accountAlias();
        System.out.println("Your account has been created");

        newDebitAccount = new DebitAccount(entity, office, accNumber, dc, IBAN, alias);
        currentUser.getBankAccounts().add(newDebitAccount);
        dataAccess.writeBankAccounts(currentUser.getBankAccounts());
        System.out.println("Your account has been created");
        return newDebitAccount;
    }
}
