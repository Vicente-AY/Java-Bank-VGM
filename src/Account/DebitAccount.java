package Account;
import Utils.Data;
import Person.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que representa una cuenta de débito bancaria.
 * Proporciona implementaciones para las operaciones transaccionales estándar
 * como depósitos, retiros, transferencias y recargas, operando siempre bajo
 * el saldo disponible de forma directa.
 * @see BankAccount
 */
public class DebitAccount extends BankAccount {

    //formateamos la fecha para guardar el historial de movimientos bancarios
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    /**
     * Constructor para inicializar una cuenta de débito con sus datos identificativos.
     * @param entity       Código de la entidad (4 dígitos).
     * @param office       Código de la oficina (4 dígitos).
     * @param accNumber    Número de cuenta (10 dígitos).
     * @param dc           Dígitos de control.
     * @param IBAN         Código IBAN completo.
     * @param accountAlias Nombre o apodo asignado a la cuenta.
     */
    public DebitAccount(String entity, String office, String accNumber, String dc, String IBAN, String accountAlias) {
        super(entity, office, accNumber, dc, IBAN, accountAlias);
    }

    /**
     * Incrementa el saldo de la cuenta indicada.
     * @param amount  Cantidad entera a depositar.
     */
    @Override
    public void deposit(double amount) {

        //capturamos la fecha con el formato
        String transactionDate = dateFormat.format(new Date());
        //guardamos el balance previo a la operacion
        double previousBalance =  this.getBalance();

        this.balance += amount;
        //informamos al usuario del movimiento realizado
        System.out.println("Deposited " + amount);
        System.out.println("New Balance: " + this.balance);

        //guardamos en el historial de movimientos de la cuenta la operación realizada
        this.getHistory().add(new BankAccountHistory(previousBalance, "Deposit", amount, this.balance, transactionDate));
    }

    /**
     * Extrae fondos de la cuenta siempre que haya saldo suficiente.
     * @param amount  Cantidad entera a retirar.
     */
    @Override
    public void withdraw(double amount) {

        //si la cuenta no tiene fondos no puede hacer el movimiento
        if (this.balance <= 0 || this.balance - amount < 0){
            System.out.println("Insufficient funds");
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
     * Realiza una transferencia de fondos a otra cuenta mediante el número de cuenta destino.
     * @throws InputMismatchException Si el usuario introduce un formato de monto inválido.
     */
    @Override
    public void transfer(ArrayList<Person> persons) {

        Scanner sc = new Scanner(System.in);
        String transactionDate = dateFormat.format(new Date());
        double previousBalance = this.getBalance();
        try{
            String sourceAcc =  this.accNumber;
            System.out.println("Please enter the destination account number");
            String destinationAcc =  sc.nextLine();
            System.out.println("Please enter the amount to be transferred");
            double amount = sc.nextDouble();
            sc.nextLine();
            //si la cuenta no tiene suficiente balance no podrá hacer el movimiento
            if(amount > this.balance){
                System.out.println("Insufficient funds");
            }
            else{
                BankAccount destAcc = null;
                //buscamos la cuenta introducida por el usuario
                for(int i = 0; i < persons.size(); i++){
                    if(persons.get(i) instanceof User){
                        for(BankAccount bankAccount : ((User) persons.get(i)).getBankAccounts()){
                            if(bankAccount.accNumber.equals(destinationAcc)){
                                destAcc = bankAccount;
                            }
                        }
                    }
                }
                //si encontramos la cuenta en el proceso anterior realizamos la operación
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
            System.out.println(e.getMessage());
        }
    }

    /**
     * Simula la recarga de una tarjeta SIM.
     * Valida que el número de teléfono tenga una longitud de 9 dígitos.
     * @param amount  Monto de la recarga.
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
        if(amount >= this.balance || this.balance - amount < 0){
            System.out.println("Insufficient funds");
        }
        else {
            double previousBalance = this.balance;
            this.balance -= amount;
            System.out.println("Operation successful");
            System.out.println("New balance in " + this.accNumber + " is: " + this.balance);
            this.getHistory().add(new BankAccountHistory(previousBalance, "Recharge", -amount, this.balance, transactionDate));
        }
    }

    /**
     * Proceso de creación de una nueva cuenta de débito.
     * Calcula los parámetros bancarios y persiste la información en el sistema.
     * @return La nueva cuenta de débito creada y vinculada.
     */
    public void  createDebitAccount(ArrayList<Person> persons) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please introduce de ID of the client the new bank account is for");
        String id = sc.nextLine();
        Person currentUser = null;
        DebitAccount newDebitAccount = new DebitAccount("9999", "8888", null, null, null, null);
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

        entity = newDebitAccount.getEntity();
        office = newDebitAccount.getOffice();
        accNumber = newDebitAccount.accountNumber(persons);

        dc = newDebitAccount.calcDC(entity, office, accNumber);
        IBAN = newDebitAccount.calcIBAN(entity, office, accNumber);
        alias = newDebitAccount.accountAlias();

        DebitAccount debitAcc = new DebitAccount(entity, office, accNumber, dc, IBAN, alias);
        ((User) currentUser).getBankAccounts().add(debitAcc);
        System.out.println("The Debit account " + debitAcc.getAccNumber() + "has been created for " + currentUser.getName());
    }
}
