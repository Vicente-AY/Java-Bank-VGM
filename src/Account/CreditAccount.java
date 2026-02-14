package Account;
import Person.*;
import Shop.ShopItem;
import Utils.*;
import Person.User;
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

    private static final long serialVersionUID = 1L;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    double creditLimit;
    double availableCredit;
    private transient boolean debtor;

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
        this.availableCredit = creditLimit;
    }

    /**
     * Metodo que carga la variable debto del usuario para ver si está marcado como deudor y limitar el uso de sus
     * cuentas de credito
     * @param debtor booleano que identifica a un usuario deudor
     */
    public static void debtorUser(boolean debtor){
        debtor = debtor;
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

        double totalAvailable = this.balance + this.availableCredit;
        double previousBalance = this.getBalance();
        String transactionDate = dateFormat.format(new Date());

        //si no es deudor la operacion se realizará normalmente
        if(!debtor) {
            if (amount > totalAvailable) {
                System.out.println("Not enough credit");
            }
            else {
                if (amount <= this.balance) {
                    this.balance -= amount;
                }
                else {
                    double remaining = amount - this.balance;
                    this.balance = 0;
                    this.availableCredit -= remaining;
                }
            }
        }
        //de serlo limitamos la operación al balance
        else{
            if(amount > this.balance){
                System.out.println("Insufficient funds");
            }
            else {
                this.balance -= amount;
            }
        }

        System.out.println("Withdrawn " + amount);
        System.out.println("New balance in " + this.accNumber + " is: " + this.balance);
        this.getHistory().add(new BankAccountHistory(previousBalance, "Withdraw", -amount, this.balance, transactionDate));
    }

    /**
     * Transfiere fondos desde esta cuenta de crédito hacia otra.
     * @param persons ArrayList para buscar la cuenta bancaria destino
     */
    @Override
    public void transfer(ArrayList<Person> persons) {

        Scanner sc = new Scanner(System.in);
        String transactionDate = dateFormat.format(new Date());
        double previousBalance = this.balance;
        double totalAvailable = this.balance + this.availableCredit;
        BankAccount destAcc = null;
        double destAcPreviousBalance = destAcc.getBalance();

        try {
            String sourceAcc = this.accNumber;
            System.out.println("Please enter the destination account number");
            String destinationAcc = sc.nextLine();
            if(destinationAcc.equals(sourceAcc)){
                System.out.println("You cannot transfer to the active account");
                return;
            }
            System.out.println("Please enter the amount to be transferred");
            double amount = sc.nextDouble();
            sc.nextLine();
            //si la cuenta no tiene suficiente credito no podrá hacer el movimiento
            if(!debtor) {
                if(amount > totalAvailable) {
                    System.out.println("Not enough credit");
                    return;
                }
            }
            //de ser deudor además se bloqueara el credito
            else if(debtor){
                if(amount > this.balance){
                    System.out.println("Insufficient funds");
                    return;
                }
            }
            else {
                //buscamos la cuenta introducida por el usuario previamente
                for (int i = 0; i < persons.size(); i++) {
                    if (persons.get(i) instanceof User) {
                        for (BankAccount bankAccount : ((User) persons.get(i)).getBankAccounts()) {
                            if (bankAccount.accNumber.equals(destinationAcc)) {
                                destAcc = bankAccount;
                            }
                        }
                    }
                }
                //si encontramos la cuenta en el porceo anterior realizamos la operacion
                if (destAcc != null) {
                    //de no ser deudor la operación se ralizara normalmente
                    if (!debtor) {
                        if (amount <= this.balance) {
                            this.balance -= amount;
                        } else {
                            double remaining = amount - this.balance;
                            this.balance = 0;
                            this.availableCredit -= remaining;
                        }
                        destAcc.balance += amount;

                    }
                    //de no ser deudor limitamos al uso de su balance
                    else{
                        this.balance -= amount;
                    }
                }
                else {
                    System.out.println("Destination account does not exist");
                }
            }
            System.out.println("Operation successful");
            System.out.println("New balance in " + sourceAcc + " is: " + this.balance);
            System.out.println("New balance in " + destinationAcc + " is: " + destAcc.balance);
            this.getHistory().add(new BankAccountHistory(previousBalance, "Transference to", -amount, this.balance, transactionDate, destAcc));
            destAcc.getHistory().add(new BankAccountHistory(destAcPreviousBalance, "Receibed transference from", amount, destAcc.balance, transactionDate, this));
        }
        catch(InputMismatchException e) {
            System.err.println("Error |Invalid Amount format. Cancelling operation");
            sc.nextLine();
        }
    }

    /**
     * Permite pagar la recarga de una tarjeta SIM utilizando el crédito disponible.
     * @param amount  Costo de la recarga.
     */
    @Override
    public void rechargeSIM(double amount) {

        double totalAvailable = this.balance + this.availableCredit;
        double previousBalance = this.getBalance();

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
        //si no es deudor la operacion se realizara con normalidad
        if(!debtor) {
            if(amount > totalAvailable){
                System.out.println("Not enough credit");
            }
            else {
                if (amount <= this.balance) {
                    this.balance -= amount;
                }
                else {
                    double remaining = amount - this.balance;
                    this.balance = 0;
                    this.availableCredit -= remaining;
                }
            }
        }
        //de serlo limitamos la operación a su balance
        else{
            if(amount > this.balance){
                System.out.println("Insufficient funds");
            }
            else{
                this.balance -= amount;
            }
        }

        this.balance -= amount;
        System.out.println("Operation successful");
        System.out.println("New balance in " + this.accNumber + " is: " + this.balance);

        this.getHistory().add(new BankAccountHistory(previousBalance, "Recharge", -amount, this.balance, transactionDate));
    }

    @Override
    /**
     * Metodo que gestiona el pago en la tienda
     * @param amount cantidad que el usuario paga por el producto
     * @param shopItem producto que el usuario compra
     */
    public void shopPayment(double amount, ShopItem shopItem){

        double totalAvailable = this.balance + this.availableCredit;
        String transactionDate = dateFormat.format(new Date());
        double previousBalance = this.balance;

        //si no es deudor la operación se realizara nomalmente
        if(!debtor) {
            if (amount > totalAvailable) {
                System.out.println("Not enough credit");
            } else {
                if (amount <= this.balance) {
                    this.balance -= amount;
                } else {
                    double remaining = amount - this.balance;
                    this.balance = 0;
                    this.availableCredit -= remaining;
                }
            }
        }
        //de ser deudor limitamos la operación al balance disponible
        else{
            if(amount > this.balance){
                System.out.println("Insufficient funds");
            }
            else{
                this.balance -= amount;
            }
        }
        System.out.println("Bought " + shopItem.getName() + " for: "+ amount);
        System.out.println("New balance in " + this.accNumber + " is: " + this.balance + " Available Credit is: " + this.availableCredit);
        this.getHistory().add(new BankAccountHistory(previousBalance, "Shop payment", -amount, this.balance, transactionDate));
    }

    @Override
    /**
     * Metodo qeu gestiona el pago con tarjeta
     */
    public void cardPayment(double amount, ShopItem shopItem, Card card){

        double totalAvailable = this.balance + this.availableCredit;
        String transactionDate = dateFormat.format(new Date());
        double previousBalance =  this.balance;
        Scanner sc = new Scanner(System.in);

        //si la tarjeta esta caducada cancelamos la operacion
        if(!card.getActive()){
            System.out.println("Selected Card has expired");
            return;
        }

        System.out.println("Please input the CVV of your card");
        String cvv = sc.nextLine();

        //Preguntamos por el cvv de la tarjeta, de no ser correcto cancelamos la operación
        if(!cvv.equals(card.getCVV())){
            System.out.println("Invalid CVV");
            return;
        }

        //si no es deudor podrá usar su credito
        if(!debtor) {
            if (amount > totalAvailable) {
                System.out.println("Not enough credit");
            } else {
                if (amount <= this.balance) {
                    this.balance -= amount;
                } else {
                    double remaining = amount - this.balance;
                    this.balance = 0;
                    this.availableCredit -= remaining;
                }
            }
        }
        //de serlo se limitara al uso de su propio balance
        else{
            if(amount > this.balance){
                System.out.println("Insufficient funds");
            }
            else{
                this.balance -= amount;
            }
        }
        this.getHistory().add(new BankAccountHistory(previousBalance, "Shop payment", -amount, this.balance, transactionDate));
    }


    /**
     * Metodo para registrar una nueva cuenta de crédito en el sistema.
     * Calcula los datos bancarios necesarios y vincula la cuenta al perfil del usuario actual.
     * * @param newCreditAccount Instancia temporal de la cuenta con los datos de configuración.
     */
    public static void  createCreditAccount(ArrayList<Person> persons) {

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

    public static double selectLimit(){

        Scanner sc = new Scanner(System.in);
        double fiveH = 500;
        double thousand = 1000;
        double fiveT = 5000;

        while(true) {
            int option = 0;
            try {
                System.out.println("Choose the limit for this account");
                System.out.println("1. 500");
                System.out.println("2. 1000");
                System.out.println("3. 5000");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
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
            catch(InputMismatchException e) {
                System.err.println("Error, please introduce a number");
                sc.nextLine();
                option = 0;

            }
        }
    }

    public double getCreditLimit(){
        return creditLimit;
    }
    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
    public double getAvailableCredit(){
        return availableCredit;
    }
    public void setAvailableCredit(double availableCredit) {
        this.availableCredit = availableCredit;
    }
}