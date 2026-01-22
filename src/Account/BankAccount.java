package Account;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import Person.*;
import Utils.Data;

import static java.lang.Integer.parseInt;

/**
 * Clase abstracta que define la estructura y comportamiento base de una cuenta bancaria.
 * Implementa la interfaz Accounting y calcula el IBAN y codigos de control
 */
public abstract class BankAccount implements Accounting {

    public String entity = "9999", office = "8888";
    public String dc = "", accNumber = "";
    public String IBAN = "";
    public String accountAlias = "";
    public double balance = 0.0;
    int numNewAccount = 0;
    ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
    Scanner sc = new Scanner(System.in);
    Data dataAccess = new Data();

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
    public void createBankAccount() {

        BankAccount newBankAccount;
        String entity = "", office = "", dc = "", accNumber = "", IBAN = "", alias = "";

        //Obtiene los datos que ya tenemos
        entity = getEntity();
        office = getOffice();
        accNumber = accountNumber();

        //Usa los datos base obtenidos para hacer el calculo del resto
        dc = calcDC(entity, office, accNumber);
        IBAN = calcIBAN(entity, office, accNumber);
        alias = accountAlias();
        System.out.println("Your account has been created");

    }

    /**
     * Gestiona la asignación de un alias a la cuenta mediante interacción con el usuario.
     * @return El alias elegido o uno por defecto si se rechaza la opción.
     */
    public String accountAlias() {
        String alias = "";
        System.out.println("Do you want to give an alias to your account?");
        String check = sc.nextLine();
        if (check.equalsIgnoreCase("yes") || check.equalsIgnoreCase("si")) {
            System.out.println("Introduce the account alias: ");
            alias = sc.nextLine();
        //Comportamiento por defecto si el usuario rechaza no poner nada
        } else if (alias.isEmpty() || check.equalsIgnoreCase("no")) {
            System.out.println("The account name will default to its number.");
            alias = "Account " + IBAN;
        } else {
            alias = check;
        }
        return alias;
    }

    /**
     * Genera un nuevo número de cuenta único.
     * Busca en el archivo binario el número de cuenta más alto actual e incrementa en 1.
     * * @return Un String de 10 dígitos representando el nuevo número de cuenta.
     */
    public String accountNumber () {
        String accNum = "";
        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
        ArrayList<Person> persons = dataAccess.chargeData();
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
}
