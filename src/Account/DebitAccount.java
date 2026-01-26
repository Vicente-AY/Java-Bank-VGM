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
    Data dataAccess = new Data();
    Scanner sc  = new Scanner(System.in);

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
     * @param account Instancia de la cuenta que recibe el depósito.
     */
    @Override
    public void deposit(int amount, BankAccount account) {

        //capturamos la fecha con el formato
        String transactionDate = dateFormat.format(new Date());
        //guardamos el balance previo a la operacion
        double previousBalance =  account.getBalance();

        account.balance += amount;
        //informamos al usuario del movimiento realizado
        System.out.println("Deposited " + amount);
        System.out.println("New Balance: " + account.balance);

        //guardamos en el historial de movimientos de la cuenta la operación realizada
        account.getHistory().add(new BankAccountHistory(previousBalance, "Deposit", amount, account.balance, transactionDate));
    }

    /**
     * Extrae fondos de la cuenta siempre que haya saldo suficiente.
     * @param amount  Cantidad entera a retirar.
     * @param account Cuenta desde la cual se realiza la retirada.
     */
    @Override
    public void withdraw(int amount, BankAccount account) {

        //si la cuenta no tiene fondos no puede hacer el movimiento
        if (account.balance <= 0 || account.balance - amount < 0){
            System.out.println("Insufficient funds");
        }
        else{
            String transactionDate = dateFormat.format(new Date());
            double previousBalance =  account.getBalance();
            account.balance -= amount;
            System.out.println("Withdrawn " + amount);
            System.out.println("New balance in " + account.accNumber + " is: " + account.balance);
            account.getHistory().add(new BankAccountHistory(previousBalance, "Withdraw", amount, account.balance, transactionDate));
        }
    }

    /**
     * Realiza una transferencia de fondos a otra cuenta mediante el número de cuenta destino.
     * @param amount  Cantidad decimal a transferir.
     * @param account Cuenta de origen (emisora).
     * @throws InputMismatchException Si el usuario introduce un formato de monto inválido.
     */
    @Override
    public void transfer(double amount, BankAccount account, ArrayList<Person> persons) {

        String transactionDate = dateFormat.format(new Date());
        double previousBalance = account.getBalance();
        try{
            String sourceAcc =  account.accNumber;
            System.out.println("Please enter the destination account number\n");
            String destinationAcc =  sc.nextLine();
            System.out.println("Please enter the amount to be transferred (With decimals)\n");
            double ammount = sc.nextDouble();

            //si la cuenta no tiene suficiente balance no podrá hacer el movimiento
            if(ammount > account.balance){
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
                    account.balance -= ammount;
                    destAcc.balance += ammount;
                    System.out.println("Operation successful");
                    System.out.println("New balance in " + sourceAcc + " is: " + account.balance);
                    System.out.println("New balance in " + destinationAcc + " is: " + destAcc.balance);
                    account.getHistory().add(new BankAccountHistory(previousBalance, "Transference", amount, account.balance, transactionDate, destAcc));
                    destAcc.getHistory().add(new BankAccountHistory(destAcPreviousBalance, "Transference", amount, destAcc.balance, transactionDate, destAcc));
                }
                else{
                    System.out.println("Destination account does not exist");
                    return;
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
     * @param account Cuenta que pagará la recarga.
     */
    @Override
    public void rechargeSIM(int amount, BankAccount account) {

        String transactionDate = dateFormat.format(new Date());
        System.out.println("Input the destination phone number\n");
        try{
            //pedimos al usuario un numero de telefono de 9 digitos
            String number =  sc.nextLine();
            while(number.length() != 9){
                System.out.println("Please enter a valid phone number (9 digits)\n");
                number = sc.nextLine();
            }
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        if(account.balance >= amount || account.balance - amount < 0){
            System.out.println("Insufficient funds");
        }
        else {
            double previousBalance = account.balance;
            account.balance -= amount;
            System.out.println("Operation successful");
            System.out.println("New balance in " + account.accNumber + " is: " + account.balance);
            account.getHistory().add(new BankAccountHistory(previousBalance, "Recharge", amount, account.balance, transactionDate));
        }
    }

    /**
     * Muestra las cuentas vinculadas a un usuario y permite seleccionar una para operar.
     * @param user El usuario cliente cuya cuenta se desea seleccionar.
     */
    @Override
    public void selectAccount(User user) {

        //mostramos por pantalla las cuentas bancarias asociadas para que el usuario pueda seleccionarla
        BankAccount foundBankAccount = null;
        System.out.println("Select the account you want to use by typing the number of the option");
        for(int i = 0; i < user.bankAccounts.size(); i++) {
            String aliasBA = user.bankAccounts.get(i).accountAlias;
            System.out.println("Option " + (i + 1) + ": " + aliasBA);
        }
        try {
            int option = sc.nextInt();
            sc.nextLine();
            //seleccionamos la cuenta que el usuario quiere utilizar
            foundBankAccount = user.bankAccounts.get(option - 1);
            System.out.println("Selected account: " + foundBankAccount.accNumber + " Balance: " + foundBankAccount.balance);

        }
        catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Proceso de creación de una nueva cuenta de débito.
     * Calcula los parámetros bancarios y persiste la información en el sistema.
     * @param newDebitAccount Instancia base para obtener configuraciones.
     * @param currentUser      Usuario al que se le asignará la cuenta.
     * @return La nueva cuenta de débito creada y vinculada.
     */
    public DebitAccount  createDebitAccount(DebitAccount newDebitAccount, Person currentUser) {
        String entity="", office="", dc="", accNumber="", IBAN="", alias ="";

        entity = newDebitAccount.getEntity();
        office = newDebitAccount.getOffice();
        accNumber = newDebitAccount.accountNumber();

        dc = newDebitAccount.calcDC(entity, office, accNumber);
        IBAN = newDebitAccount.calcIBAN(entity, office, accNumber);
        alias = newDebitAccount.accountAlias();

        newDebitAccount = new DebitAccount(entity, office, accNumber, dc, IBAN, alias);
        ((User) currentUser).getBankAccounts().add(newDebitAccount);
        System.out.println("Your account has been created");
        return newDebitAccount;
    }
}
