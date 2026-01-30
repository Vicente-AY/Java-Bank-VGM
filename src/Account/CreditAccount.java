package Account;
import Person.*;
import Utils.*;
import Person.User;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase que representa una cuenta de crédito bancaria.
 * Hereda de {BankAccount y añade funcionalidades específicas para
 * la gestión de límites de crédito y tasas de interés/porcentaje.
 */
public class CreditAccount extends BankAccount {

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
     * @param creditPercentage Porcentaje de interés o comisión aplicable.
     * @see BankAccount
     */
    public CreditAccount(String entity, String office, String accNumber, String dc, String IBAN, String accountAlias, double creditLimit, double creditPercentage){
        super(entity, office, accNumber, dc, IBAN, accountAlias);
        this.creditLimit = creditLimit;
        this.creditPercentage = creditPercentage;
    }

    /**
     * Realiza un depósito de efectivo en la cuenta de crédito.
     * * @param amount  Cantidad a depositar.
     */
    @Override
    public void deposit(double amount) {
    }

    /**
     * Realiza una retirada de efectivo, validando el límite de crédito.
     * * @param amount  Cantidad a retirar.
     */
    @Override
    public void withdraw(double amount) {

    }

    /**
     * Transfiere fondos desde esta cuenta de crédito hacia otra.
     * @param persons ArrayList para buscar la cuenta bancaria destino
     */
    @Override
    public void transfer(ArrayList<Person> persons) {

    }

    /**
     * Permite pagar la recarga de una tarjeta SIM utilizando el crédito disponible.
     * @param amount  Costo de la recarga.
     */
    @Override
    public void rechargeSIM(double amount) {

    }

    /**
     * Permite al usuario interactuar con la cuenta seleccionada.
     * @param user Usuario que realiza la acción.
     */
    @Override
    public void selectAccount(User user) {

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
        CreditAccount newCreditAccount = new CreditAccount("9999", "8888", null, null, null, null, 0.0, 0.0);
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

        limit = newCreditAccount.creditLimit;
        percentage = newCreditAccount.creditPercentage;

        newCreditAccount = new CreditAccount(entity, office, accNumber, dc, IBAN, alias, limit, percentage);
        ((User) currentUser).getBankAccounts().add(newCreditAccount);
        System.out.println("The account has been created");
    }
    public String createCreditCard(BankAccount entity, ArrayList<Person> persons) {
        String VisaNumber = "4";
        String bin = VisaNumber + entity + "200";
        StringBuilder panParcial = new StringBuilder(bin);
        for (int i = 0; i < 7; i++) {
            panParcial.append((int)(Math.random() * 10));
        }
        int digitoControl = calcularDigitoLuhn(panParcial.toString());

        return panParcial.append(digitoControl).toString();
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
}
