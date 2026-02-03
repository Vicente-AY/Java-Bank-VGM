package Account;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import Person.*;
import static java.lang.Integer.parseInt;

/**
 * Clase abstracta que define la estructura y comportamiento base de una cuenta bancaria.
 * Implementa la interfaz Accounting y calcula el IBAN y codigos de control
 */
public abstract class BankAccount implements Accounting, Serializable {

    private static final long serialVersionUID = 1L;
    ArrayList<BankAccountHistory>  history = new ArrayList<BankAccountHistory>();
    public String entity = "9999", office = "8888";
    public String dc = "", accNumber = "";
    public String IBAN = "";
    public String accountAlias = "";
    public double balance = 0.0;

    /**
     * Constructor completo para inicializar una cuenta bancaria
     *
     * @param entity       Código de la entidad bancaria (4 dígitos).
     * @param office       Código de la sucursal/oficina (4 dígitos).
     * @param accNumber    Número de cuenta individual (10 dígitos).
     * @param dc           Dígitos de control calculados.
     * @param IBAN         Código IBAN completo.
     * @param accountAlias Apodo descriptivo de la cuenta.
     */
    public BankAccount(String entity, String office, String accNumber, String dc, String IBAN, String accountAlias) {
        this.entity = entity;
        this.office = office;
        this.accNumber = accNumber;
        this.dc = dc;
        this.IBAN = IBAN;
        this.accountAlias = accountAlias;
        this.balance = 0.0;
    }

    /**
     * Calcula los dos dígitos de control (DC) de una cuenta bancaria según el estándar español.
     *
     * @param entidad Código de la entidad (4 dígitos).
     * @param oficina Código de la oficina (4 dígitos).
     * @param cuenta  Número de cuenta (10 dígitos).
     * @return Una cadena de dos caracteres representando los dígitos de control.
     */
    public static String calcDC(String entidad, String oficina, String cuenta) {
        entidad = String.format("%04d", parseInt(entidad));
        oficina = String.format("%04d", parseInt(oficina));
        cuenta = String.format("%010d", Long.parseLong(cuenta));

        //pesos para el calculo de los digitos de control que se multiplicaran a entidad y oficina o a la cuenta bancaria
        int[] w1 = {4, 8, 5, 10, 9, 7, 3, 6};
        int[] w2 = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};

        //Calculo del primer digito de control usando entidad y oficina
        String bloque1 = entidad + oficina;
        int suma1 = 0;
        for (int i = 0; i < 8; i++) suma1 += (bloque1.charAt(i) - '0') * w1[i];
        int r1 = suma1 % 11;
        int d1 = 11 - r1;
        if (d1 == 11) d1 = 0;
        else if (d1 == 10) d1 = 1;

        //Calculo del segundo digito de control usando la el numero de cuenta bancaria
        String bloque2 = cuenta;
        int suma2 = 0;
        for (int i = 0; i < 10; i++) suma2 += (bloque2.charAt(i) - '0') * w2[i];
        int r2 = suma2 % 11;
        int d2 = 11 - r2;
        if (d2 == 11) d2 = 0;
        else if (d2 == 10) d2 = 1;

        return "" + d1 + d2;
    }

    /**
     * Calcula el código IBAN completo prefijo ES + 2 dígitos.
     *
     * @param entity    Código de la entidad.
     * @param office    Código de la oficina.
     * @param accNumber Número de cuenta.
     * @return El código IBAN completo en formato String.
     */
    public static String calcIBAN(String entity, String office, String accNumber) {

        String dc = calcDC(entity, office, accNumber);

        entity = String.format("%04d", parseInt(entity));
        office = String.format("%04d", parseInt(office));
        accNumber = String.format("%010d", Long.parseLong(accNumber));

        String bban = entity + office + dc + accNumber;

        // ES -> 14 28, más "00"
        String numeric = bban + "142800";

        //Uso de BigInteger dado que el numero es mas largo que un Long
        BigInteger num = new BigInteger(numeric);
        int resto = num.mod(BigInteger.valueOf(97)).intValue();
        int cd = 98 - resto;
        String cdStr = String.format("%02d", cd);

        return "ES" + cdStr + bban;
    }

    /**
     * Crea cuentas bancarias a través de diferentes metodos
     */
    public void createBankAccount(ArrayList<Person> persons) {

        BankAccount newBankAccount;
        String entity = "", office = "", dc = "", accNumber = "", IBAN = "", alias = "";

        //Obtiene los datos que ya tenemos
        entity = getEntity();
        office = getOffice();
        accNumber = accountNumber(persons);

        //Usa los datos base obtenidos para hacer el calculo del resto
        dc = calcDC(entity, office, accNumber);
        IBAN = calcIBAN(entity, office, accNumber);
        alias = accountAlias();
        System.out.println("The account has been created");

    }

    /**
     * Gestiona la asignación de un alias a la cuenta mediante interacción con el usuario.
     * @return El alias elegido o uno por defecto si se rechaza la opción.
     */
    public String accountAlias() {
        Scanner sc = new Scanner(System.in);
        String alias = "";
        System.out.println("Introduce the Account Alias (Empty for Default)");
        alias = sc.nextLine();
        //comportamiento por defecto del alias si este está vacio
        if(alias.isEmpty()){
            System.out.println("The account Alias will default to its number");
            alias = "Account "+ this.IBAN;
        }
        return alias;
    }

    /**
     * Genera un nuevo número de cuenta único.
     * Busca en el archivo binario el número de cuenta más alto actual e incrementa en 1.
     * * @return Un String de 10 dígitos representando el nuevo número de cuenta.
     */
    public String accountNumber (ArrayList<Person> persons) {
        String accNum = "";
        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
        //recopila todas las cuentas bancarias
        for(Person person : persons){
            if(person instanceof User){
                User user = (User) person;
                for(BankAccount bankAccount : user.getBankAccounts()){
                    bankAccounts.add(bankAccount);
                }
            }
        }
        int maxId = 0;
        //encuentra el valor maximo y suma uno
        if (!bankAccounts.isEmpty()) {
            for (BankAccount bankAccount : bankAccounts) {
                int currentNum = Integer.parseInt(bankAccount.accNumber);
                if (currentNum > maxId) {
                    maxId = currentNum;
                }
            }
        }
        maxId++;
        return String.format("%010d", maxId);
    }

    /**
     * Metodo que permite al usuario  pagar sus deudas
     * @param currentUser usuario que paga la deuda
     */
    public void payDebts(Person currentUser){

        //mostramos las dudas pendientes del usuario
        System.out.println("Select the account you want to pay the debt");
        for(int i = 0; i < ((User) currentUser).getBankAccounts().size(); i++){
            if(((User) currentUser).getBankAccounts().get(i).getBalance() < 0){
                System.out.println("Option: " + (i +1) + ": " + ((User) currentUser).getBankAccounts().get(i).getAccNumber()
                                    + " Balance: " + ((User) currentUser).getBankAccounts().get(i).getBalance());
            }
        }
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        sc.nextLine();
        BankAccount debtBankAccount = ((User) currentUser).getBankAccounts().get(choice);
        int option = 0;
        while(true) {
            try {
                System.out.println("Select the option of how do you want to pay the debt");
                //el usuario podra usar un deposito para pagar la deuda o una transferencia de otra cuenta
                System.out.println("1. Deposit | 2. Transfer");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        System.out.println("Introduce the amount you want to deposit");
                        int amount = sc.nextInt();
                        sc.nextLine();
                        debtBankAccount.setBalance(debtBankAccount.getBalance() + amount);
                        System.out.println("You repayed: " + amount + ". New balance: " + debtBankAccount.getBalance());
                        if (debtBankAccount.getBalance() >= 0) {
                            System.out.println("You have successfully payed the debt. Next month you will be able to access the credit again");
                        }
                        return;
                    //Mostramos las cuentas donde el usuario tiene balance positivo
                    case 2:
                        System.out.println("Introduce the option to select the Bank account");
                        for (int i = 0; i < ((User) currentUser).getBankAccounts().size(); i++) {
                            if (((User) currentUser).getBankAccounts().get(i).getBalance() > 0) {
                                System.out.println("Option: " + (i + 1) + ": " + ((User) currentUser).getBankAccounts().get(i).getAccNumber()
                                        + " Balance: " + ((User) currentUser).getBankAccounts().get(i).getBalance());
                            }
                        }
                        //Al elegir cuenta, extraerá la mayor cantidad posible para pagar la deuda
                        BankAccount selectedBankAccount = ((User) currentUser).getBankAccounts().get(choice);
                        if (selectedBankAccount.getBalance() < debtBankAccount.getBalance()) {
                            debtBankAccount.setBalance(debtBankAccount.getBalance() - selectedBankAccount.getBalance());
                            System.out.println("You repayed: " + selectedBankAccount.getBalance());
                            System.out.println("New debt: " + debtBankAccount.getBalance());
                            selectedBankAccount.setBalance(0);
                            return;
                        } else if (selectedBankAccount.getBalance() > debtBankAccount.getBalance()) {
                            selectedBankAccount.setBalance(selectedBankAccount.getBalance() + debtBankAccount.getBalance());
                            debtBankAccount.setBalance(0);
                            System.out.println("You have successfully payed the debt. Next month you will be able to access the credit again");
                            return;
                        }
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
            catch(InputMismatchException e){
                System.out.println("Please introduce a number");
                sc.nextLine();
                option = 0;
            }
        }
    }

    /// Getters y Setters

    /** @return Código de la entidad. */
    public String getEntity () {
        return this.entity;
    }

    /** @return Código de la oficina. */
    public String getOffice () {
        return this.office;
    }

    /** @return Dígitos de control. */
    public String getDc () {
        return dc;
    }

    /**
     * Devuelve el número de la cuenta bancaria
     * @return número de la cuenta bancaria
     */
    public String getAccNumber () {
        return accNumber;
    }

    /** @return Número de cuenta de 10 dígitos. */
    public String getIBAN () {
        return IBAN;
    }

    /** @return Código IBAN completo. */
    public double getBalance () {
        return balance;
    }

    /** @return Saldo disponible en la cuenta. */
    public void setDc (String dc){
        this.dc = dc;
    }

    /** @param accNumber Nuevo número de cuenta. */
    public void setAccNumber (String accNumber){
        this.accNumber = accNumber;
    }

    /** @param IBAN Nuevo código IBAN. */
    public void setIBAN (String IBAN){
        this.IBAN = IBAN;
    }

    /** @param accountAlias Nuevo alias para la cuenta. */
    public void setAccountAlias (String accountAlias){
        this.accountAlias = accountAlias;
    }

    /** @param balance Nuevo saldo de la cuenta. */
    public void setBalance ( double balance){
        this.balance = balance;
    }

    public ArrayList<BankAccountHistory> getHistory(){
        return history;
    }

    public String getAccountAlias() {
        return accountAlias;
    }
}
