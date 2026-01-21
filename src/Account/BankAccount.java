package Account;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;
import Utils.Data;

import static java.lang.Integer.parseInt;

public abstract class BankAccount implements Accounting {
    /**
     * Clase que gestiona la cuenta bancaria
     * @param entity Número generado al crear una cuenta
     * @param office Número referente a la oficina en la que se creó la cuenta
     * @param dc Dígitos de control formados siguiendo un algoritmo en específico
     * @param accNumber Número asignado a la cuenta bancaria
     * @param IBAN Serie de dígitos formado a través de un algortimo en específico
     * @param accountAlias Apodo asignado al dueño de la cuenta
     * @param balance Cantidad de dinero que posee la cuenta bancaria
     * @param numNewAccount Número asignado a la nueva cuenta creada
     * @param accounts Lista referente a las cuentas de un usuario
     * @param sc Variable que llama al escaner
     */
   public String entity="9999", office="8888";
   public String dc="", accNumber="";
   public String IBAN="";
   public String accountAlias="";
   public double balance=0.0;
   int numNewAccount=0;
   ArrayList<BankAccount> accounts=new ArrayList<BankAccount>();
   Scanner sc =new Scanner(System.in);
    /**
     * Constructor con parámetros
     * @param entity Cadena con el número de la entidad
     * @param office Cadena con el número de la oficina
     * @param accNumber Cadena con el número de la cuenta
     * @param dc Cadena con los dígitos de control
     * @param IBAN Cadena con los dígitos del IBAN
     * @param accountAlias Cadena con el apodo del usuario
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
     * Constructor con parámetros algo más específico
     * @param entity Cadena con el número de la entidad
     * @param office Cadena con el número de la oficina
     * @param accNumber Cadena con el número de la cuenta
     * @param dc Cadena con los dígitos de control
     * @param IBAN Cadena con los dígitos del IBAN
     */
    public BankAccount(String entity, String office, String accNumber, String dc, String IBAN) {
        this.entity = entity;
        this.office = office;
        this.accNumber = accNumber;
        this.dc = dc;
        this.IBAN = IBAN;
        this.accountAlias = "Account "+ accNumber;
        this.balance=0.0;
    }
    /**
     * Metodo para calcular el algoritmo para obtener los dos dígitos de control
     * @param entidad Cadena con el número de la entidad
     * @param oficina Cadena que posee el número de la oficina
     * @param cuenta Cadena con el número de la cuenta bancaria
     * @return devuelve ambos dígitos de control
     */
    public static String calcDC(String entidad, String oficina, String cuenta) {
        entidad = String.format("%04d", parseInt(entidad));
        oficina = String.format("%04d", parseInt(oficina));
        cuenta  = String.format("%010d", Long.parseLong(cuenta));
/**
 * @param w1 Entero que mide la densidad del primer dígito de control
 * @param w2 Entero que mide la densidad del segundo dígito de control
 * @param bloque1 Cadena que une los números de la entidad y la oficina
 * @param suma1 Entero resultado de multiplicar el bloque1 por w1
 * @param r1 Entero que obtiene el módulo de la suma previamente realizada
 * @param d1 Entero que representa el primer dígito de control
 * @param bloque2 Cadena que almacena el valor de cuenta
 * @param suma2 Entero resultado de multiplicar el bloque2 por w2
 * @param r2 Entero que obtiene el módulo de la suma previamente realizada
 * @param d2 Entero que representa el segundo dígito de control
 */
        int[] w1 = {4,8,5,10,9,7,3,6};
        int[] w2 = {1,2,4,8,5,10,9,7,3,6};

        String bloque1 = entidad + oficina;
        int suma1 = 0;
        for (int i = 0; i < 8; i++) suma1 += (bloque1.charAt(i) - '0') * w1[i];
        int r1 = suma1 % 11;
        int d1 = 11 - r1;
        if (d1 == 11) d1 = 0;
        else if (d1 == 10) d1 = 1;

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
     * Metodo para calcular el algoritmo para obtener los números del IBAN
     * @param entity Cadena con el número de la entidad
     * @param office Cadena con el número de la oficina
     * @param accNumber Cadena con el número de la cuenta bancaria
     * @return devuelve el número completo del IBAN
     */
    public static String calcIBAN(String entity, String office, String accNumber) {
        /**
         * @param dc Cadena que llama al metodo para obtener los dígitos de control
         * @param bban Cadena que combina todos los dígitos asignados
         * @param numeric Cadena que almacena el número al completo tras obtener el número del país
         * @param num Variable que convierte la cadena previa en un entero de valores altos
         * @param resto Resultado de realizar el módulo de 97 de num
         * @param cd Entero con los dígitos necesarios para formar el IBAN
         * @param cdStr Variable cd convertida en cadena
         */
        String dc  = calcDC(entity, office, accNumber);

        entity = String.format("%04d", parseInt(entity));
        office = String.format("%04d", parseInt(office));
        accNumber  = String.format("%010d", Long.parseLong(accNumber));

        String bban = entity + office + dc + accNumber;

        // ES -> 14 28, más "00"
        String numeric = bban + "142800";

        BigInteger num = new BigInteger(numeric);
        int resto = num.mod(BigInteger.valueOf(97)).intValue();
        int cd = 98 - resto;
        String cdStr = String.format("%02d", cd);

        return "ES" + cdStr + bban;
    }

    /**
     * Metodo para crear una cuenta bancaria desde cero
     */
    public void  createBankAccount() {
        /**
         * @param entity Llama a un metodo para obtener la entidad
         * @param office Llama a un metodo para obtener la oficina
         * @param dc Llama al metodo para obtener los dos dígitos de control
         * @param IBAN Llama al metodo para obtener el IBAN
         * @param alias Llama a un metodo para establecer un apodo para la cuenta bancaria
         */
        BankAccount newBankAccount;
        String entity="", office="", dc="", accNumber="", IBAN="", alias ="";

        entity = getEntity();
        office = getOffice();
        accNumber = accountNumber();

        dc = calcDC(entity, office, accNumber);
        IBAN = calcIBAN(entity, office, accNumber);
        alias = accountAlias();
        System.out.println("Your account has been created");

    }

    public String accountAlias(){
    /**
     * Metodo para establecer un alias en la cuenta bancaria
     * @return Devuelve el apodo introducido
     */
    public String changeAccountAlias(){
        /**
         * @param alias Cadena que representa el apodo asignado a la cuenta bancaria
         * @param check Cadena que actua como medida de comprobación
         */
        String alias ="";
        System.out.println("Do you want to give an alias to your account?");
        String check = sc.nextLine();
        if (check.equalsIgnoreCase("yes") || check.equalsIgnoreCase("si")) {
            System.out.println("Introduce the account alias: ");
            alias = sc.nextLine();
        }
        else if(alias.isEmpty() || check.equalsIgnoreCase("no")){
            System.out.println("The account name will default to its number.");
            alias = "Account " + IBAN;
        }
        else {
            alias = check;
        }
        return alias;
    }

    public String accountNumber(){
        Data bankAccountData = new Data();
        String accNum = "";
        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
        bankAccounts = bankAccountData.readBankAccounts();
        int maxId = 0;
        if(!bankAccounts.isEmpty()) {
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
    /**
     * Devuelve el número de la entidad
     * @return número de la entidad
     */
    public String getEntity(){
        return this.entity;

    }

    /**
     * Devuelve el número de la oficina
     * @return número de la oficina
     */
    public String getOffice(){
        return this.office;
    }

    /**
     * Devuelve los dígitos de control
     * @return dígitos de control
     */
    public String getDc() {
        return dc;
    }

    /**
     * Devuelve el número de la cuenta bancaria
     * @return número de la cuenta bancaria
     */
    public String getAccNumber() {
        return accNumber;
    }

    /**
     * Devuelve el número del IBAN
     * @return número del IBAN
     */
    public String getIBAN() {
        return IBAN;
    }

    /**
     * Devuelve el número de ingresos de la cuenta
     * @return ingresos de la cuenta
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Establece los dígitos de control
     * @param dc dígitos de control
     */
    public void setDc(String dc) {
        this.dc = dc;
    }

    /**
     * Establece el número de la cuenta bancaria
     * @param accNumber número de cuenta bancaria
     */
    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    /**
     * Establece el IBAN de la cuenta bancaria
     * @param IBAN IBAN de la cuenta bancaria
     */
    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    /**
     * Establece el apodo de la cuenta bancaria
     * @param accountAlias apodo de la cuenta
     */
    public void setAccountAlias(String accountAlias) {
        this.accountAlias = accountAlias;
    }

    /**
     * Establece el número de ingresos de la cuenta bancaria
     * @param balance número de ingresos
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }
}
