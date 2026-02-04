package Account;
import Utils.Data;
import Person.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    String numero;
    String titular;
    String fechaCaducidad;
    String codigoSeguridad;

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
            this.getHistory().add(new BankAccountHistory(previousBalance, "Withdraw", amount, this.balance, transactionDate));
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
                    this.getHistory().add(new BankAccountHistory(previousBalance, "Transference", amount, this.balance, transactionDate, destAcc));
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
            this.getHistory().add(new BankAccountHistory(previousBalance, "Recharge", amount, this.balance, transactionDate));
        }
    }

    /**
     * Muestra las cuentas vinculadas a un usuario y permite seleccionar una para operar.
     * @param user El usuario cliente cuya cuenta se desea seleccionar.
     */
    @Override
    public void selectAccount(User user) {

        Scanner sc = new Scanner(System.in);
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
     * @return La nueva cuenta de débito creada y vinculada.
     */
    public void  createDebitAccount(ArrayList<Person> persons, Boolean debit) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Please introduce de ID of the client the new bank account is for");
        String id = sc.nextLine();
        setDebit (debit);
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
    /**
     * Metodo que forma los 16 dígitos que conforman el PAN
     * @return valor del PAN
     */
    public String getPAN() {
        String VisaNumber = "4";
        String bin = VisaNumber + entity + "100";
        StringBuilder panParcial = new StringBuilder(bin);
        for (int i = 0; i < 7; i++) {
            panParcial.append((int)(Math.random() * 10));
        }
        int digitoControl = calcularDigitoLuhn(panParcial.toString());
        String PAN = panParcial.append(digitoControl).toString();

        return PAN;
    }
    @Override
    public int calcularDigitoLuhn(String cadena) {
        int suma = 0;
        boolean duplicar = true;
        for (int i = cadena.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(cadena.charAt(i));
            if (duplicar) {
                digito *= 2;
                if (digito > 9) digito -= 9;
            }
            suma += digito;
            duplicar = !duplicar;
        }
        return (10 - (suma % 10)) % 10;
    }
    /**
     * Metodo que calcula el tiempo restante que tardará la tarjeta en caducar
     * @return valor de la fecha de caducidad
     */
    public String getFechaCaducidad() {
        LocalDate caducidad = LocalDate.now().plusYears(5);
        return caducidad.format(DateTimeFormatter.ofPattern("MM/yy"));
    }
    /**
     * Metodo que calcula los 3 dígitos de seguridad
     * @param PAN
     * @param fechaCaducidad
     * @return valor del código de seguridad
     */
    public String getCodigoSeguridad(String PAN, String fechaCaducidad) {
        String claveSecreta ="CLAVE_SECRETA";
        String data = PAN + fechaCaducidad + claveSecreta;
        int hash = data.hashCode();
        return String.format("%03d", Math.abs(hash % 1000));
    }
    /**
     * Metodo que agrupa toda la información de la tarjeta
     * @param PAN
     * @param accountAlias
     * @return información de la tarjeta
     */
    public String getInfoDebito(String PAN, String accountAlias) {
        this.numero = getPAN();
        this.titular = accountAlias.toUpperCase();
        this.fechaCaducidad = getFechaCaducidad();
        this.codigoSeguridad = getCodigoSeguridad(PAN, this.fechaCaducidad);
        return String.format(numero+" "+" "+titular+" "+" "+fechaCaducidad+" "+" "+codigoSeguridad);
    }
}
