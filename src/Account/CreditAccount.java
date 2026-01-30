package Account;
import Person.*;
import Utils.*;
import Person.User;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;

/**
 * Clase que representa una cuenta de crédito bancaria.
 * Hereda de {BankAccount y añade funcionalidades específicas para
 * la gestión de límites de crédito y tasas de interés/porcentaje.
 */
public class CreditAccount extends BankAccount {

    private static final long serialVersionUID = 1744944698033905474L;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

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
     * @see BankAccount
     */
    public CreditAccount(String entity, String office, String accNumber, String dc, String IBAN, String accountAlias, double creditLimit){
        super(entity, office, accNumber, dc, IBAN, accountAlias);
        this.creditLimit = creditLimit;
    }

    /**
     * Realiza un depósito de efectivo en la cuenta de crédito.
     * * @param amount  Cantidad a depositar.
     */
    @Override
    public void deposit(double amount) {

        //guardamos la fecha actual con el formato
        String transactionDate = dateFormat.format(new Date());
        //almacenamos el balance previo a la operacion
        double previousBalance = this.getBalance();

        this.balance += amount;
        //informamos al usuario del movimiento realizado
        System.out.println("Deposited " + amount);
        System.out.println("New Balance: " + this.balance);

        //guardamos en el historial de movimientos de la cuenta la operación realizada
        this.getHistory().add(new BankAccountHistory(previousBalance, "Deposit", amount, this.balance, transactionDate));
    }

    /**
     * Realiza una retirada de efectivo, validando el límite de crédito.
     * * @param amount  Cantidad a retirar.
     */
    @Override
    public void withdraw(double amount) {

        if(this.balance - amount < creditLimit){
            System.out.println("Not enough credit");
        }
        else{
            String transactionDate = dateFormat.format(new Date());
            double previousBalance =  this.getBalance();
            this.balance -= amount;
            System.out.println("Withdrawn " + amount);
            System.out.println("New balance in " + this.accNumber + " is: " + this.balance);
            this.getHistory().add(new BankAccountHistory(previousBalance, "Withdraw", -amount, this.balance, transactionDate));
        }
    }

    /**
     * Transfiere fondos desde esta cuenta de crédito hacia otra.
     * @param persons ArrayList para buscar la cuenta bancaria destino
     */
    @Override
    public void transfer(ArrayList<Person> persons) {

        Scanner sc = new Scanner(System.in);
        String transactionDate = dateFormat.format(new Date());
        double previousBalance = this.getBalance();
        try {
            String sourceAcc = this.accNumber;
            System.out.println("Please enter the destination account number");
            String destinationAcc = sc.nextLine();
            System.out.println("Please enter the amount to be transferred");
            double amount = sc.nextDouble();
            sc.nextLine();
            //si la cuenta no tiene suficiente balance no podrá hacer el movimiento
            if (this.balance - amount < this.creditLimit) {
                System.out.println("Not enought credit");
            }
            else{
                BankAccount destAcc = null;
                //buscamos la cuenta introducida por el usuario previamente
                for(int i = 0; i < persons.size(); i++){
                    if(persons.get(i) instanceof User){
                        for (BankAccount bankAccount : ((User) persons.get(i)).getBankAccounts()) {
                            if (bankAccount.accNumber.equals(destinationAcc)) {
                                destAcc = bankAccount;
                            }
                        }
                    }
                }
                //si encontramos la cuenta en el porceo anterior realizamos la operacion
                if(destAcc != null){
                    double destAcPreviousBalance =  destAcc.getBalance();
                    this.balance -= amount;
                    destAcc.balance += amount;
                    System.out.println("Operation successful");
                    System.out.println("New balance in " + sourceAcc + " is: " + this.balance);
                    System.out.println("New balance in " + destinationAcc + " is: " + destAcc.balance);
                    this.getHistory().add(new BankAccountHistory(previousBalance, "Transference", -amount, this.balance, transactionDate, destAcc));
                    destAcc.getHistory().add(new BankAccountHistory(destAcPreviousBalance, "Transference", amount, destAcc.balance, transactionDate, this));
                }
                else{
                    System.out.println("Destination account does not exist");
                }
            }
        }
        catch(InputMismatchException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Permite pagar la recarga de una tarjeta SIM utilizando el crédito disponible.
     * @param amount  Costo de la recarga.
     */
    @Override
    public void rechargeSIM(double amount) {

        Scanner sc = new Scanner(System.in);
        String transactionDate = dateFormat.format(new Date());
        System.out.println("Input the destination phone number");
        try{
            //pedimos al usuario un numero de telefono de 9 digitos
            String number =  sc.nextLine();
            while(number.length() != 9){
                System.out.println("Please enter a valid phone number (9 digits)");
                number = sc.nextLine();
            }
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        if(this.balance - amount < this.creditLimit){
            System.out.println("Not enought credit");
        }
        else{
            double previousBalance = this.balance;
            this.balance -= amount;
            System.out.println("Operation successful");
            System.out.println("New balance in " + this.accNumber + " is: " + this.balance);
            this.getHistory().add(new BankAccountHistory(previousBalance, "Recharge", -amount, this.balance, transactionDate));
        }
    }

    /**
     * Metodo para registrar una nueva cuenta de crédito en el sistema.
     * Calcula los datos bancarios necesarios y vincula la cuenta al perfil del usuario actual.
     * * @param newCreditAccount Instancia temporal de la cuenta con los datos de configuración.
     */
    public void  createCreditAccount(ArrayList<Person> persons) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please introduce de ID of the client the new bank account is for");
        String id = sc.nextLine();
        Person currentUser = null;
        CreditAccount newCreditAccount = new CreditAccount("9999", "8888", null, null, null, null, 0.0);
        for(Person person : persons) {
            if (id.equals(person.getId())) {
                currentUser = person;
                break;
            }
        }
        if(currentUser == null) {
            System.out.println("Client ID not found");
            return;
        }
        if(currentUser instanceof Employee || currentUser instanceof Gerente){
            System.out.println("This ID is not linked to an User");
            return;
        }

        String entity="", office="", dc="", accNumber="", IBAN="", alias ="";
        double limit = 0.0, percentage = 0.0;

        entity = newCreditAccount.getEntity();
        office = newCreditAccount.getOffice();
        accNumber = newCreditAccount.accountNumber(persons);

        dc = newCreditAccount.calcDC(entity, office, accNumber);
        IBAN = newCreditAccount.calcIBAN(entity, office, accNumber);
        alias = newCreditAccount.accountAlias();

        limit = selectLimit();

        newCreditAccount = new CreditAccount(entity, office, accNumber, dc, IBAN, alias, limit);
        ((User) currentUser).getBankAccounts().add(newCreditAccount);
        System.out.println("The account has been created. Data: ");
        System.out.println("Alias: " + newCreditAccount.getAccountAlias());
        System.out.println("IBAN: " + newCreditAccount.getIBAN());
    }

    public double selectLimit(){

        Scanner sc = new Scanner(System.in);
        double fiveH = -500;
        double thousand = -1000;
        double fiveT = -5000;

        while(true){
            System.out.println("Choose the limit for this account");
            System.out.println("1. 500");
            System.out.println("2. 1000");
            System.out.println("3. 5000");
            int option = sc.nextInt();
            sc.nextLine();
            switch(option){
                case 1:
                    return fiveH;
                case 2:
                    return thousand;
                case 3:
                    return fiveT;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public double getCreditLimit(){
        return creditLimit;
    }
}
