package Menu;
import Person.*;
import Account.*;
import Shop.ShopMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que gestiona el menú interactivo para los usuarios
 * Permite al usuario final administrar sus cuentas bancarias
 */
public class UsersMenu {

    BankAccount selectedBankAccount = null;
    Scanner sc = new Scanner(System.in);
    double amount;

    /**
     * Proporciona acceso a las funcionalidades disponibles para el cliente.
     * @param currentUser El cliente que ha iniciado sesión.
     * @param persons Lista de clientes de la aplicacion
     */
    public void menuAccess(Person currentUser, ArrayList<Person> persons){

        //Cargamos en la clase de cuentas de credito si el usuario es deudor
        CreditAccount.debtorUser(((User) currentUser).getDebtor());

        selectedBankAccount = null;
        //Imprimimos el menú, si tiene cuenta seleccionada la mostramos por consola también
        int option = 0;
        while (true) {
            try {
                System.out.println("\nWelcome " + currentUser.name);
                if (selectedBankAccount != null) {
                    System.out.println("Selected account: " + selectedBankAccount.accNumber + " | Balance: " + selectedBankAccount.getBalance());
                    if (selectedBankAccount instanceof DebitAccount) {
                        System.out.println("Type: Debit Account");
                    } else {
                        CreditAccount ca =  (CreditAccount) selectedBankAccount;
                        System.out.println("Available Credit: " + ca.getAvailableCredit() + "/" + ca.getCreditLimit());
                    }
                }
                System.out.println("1. Select a BankAccount | 2. Make a deposit | 3. Withdraw");
                System.out.println("4. Transfer Money | 5. Recharge SIM card | 6. See Bank Account History");
                System.out.println("7. Java Shop | 8. Simulate Debt | 9. Log Out");

                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        selectedBankAccount = selectAccount((User) currentUser);
                        break;
                    case 2, 3, 5:
                        if (selectedBankAccount == null) {
                            System.out.println("Please select and account first");
                            break;
                        }
                        if(((User) currentUser).getBloquedAccounts()){
                            System.out.println("Your accounts are  BLOQUED. Contact an Employee");
                            break;
                        }
                        System.out.println("Enter the amount you want to perform the operation");
                        amount = sc.nextDouble();
                        sc.nextLine();
                        if(option == 2) {
                            selectedBankAccount.deposit(amount);
                        }
                        if(option == 3) {
                            selectedBankAccount.withdraw(amount);
                        }
                        if(option == 5) {
                            selectedBankAccount.rechargeSIM(amount);
                        }
                        break;
                    case 4:
                        if(((User) currentUser).getBloquedAccounts()){
                            System.out.println("Your accounts are BLOQUED. Contact an Employee");
                            break;
                        }
                        if (selectedBankAccount == null) {
                            System.out.println("Please select and account first");
                            break;
                        }
                        selectedBankAccount.transfer(persons);
                        break;
                    case 6:
                        if (selectedBankAccount == null) {
                            System.out.println("Please select and account first");
                            break;
                        }
                        bankAccountHistory(selectedBankAccount);
                        break;
                    case 7:
                        ShopMenu shopMenu = new ShopMenu();
                        shopMenu.shopMenu((User) currentUser);
                        break;
                    case 8:
                        simulation((User) currentUser);
                        break;
                    case 9:
                        return;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.err.println("Please enter a valid number");
                sc.nextLine();
                option = 0;
            }
            catch (NullPointerException e) {
                System.err.println("System Error");
            }
            catch (Exception e) {
                System.err.println("Unexpected Error");
            }
        }
    }

    /**
     * Muestra las cuentas vinculadas a un usuario y permite seleccionar una para operar.
     * @param user El usuario cliente cuya cuenta se desea seleccionar.
     */
    public BankAccount selectAccount(User user) {

        //mostramos por pantalla las cuentas bancarias asociadas para que el usuario pueda seleccionarla
        BankAccount foundBankAccount = null;
        if(user.bankAccounts.isEmpty()){
            System.out.println("You need to create a bank account first. Contact an Employee");
            return null;
        }
        System.out.println("Select the account you want to use by typing the number of the option");
        for(int i = 0; i < user.bankAccounts.size(); i++) {
            String aliasBA = user.bankAccounts.get(i).accountAlias;
            if(user.bankAccounts.get(i) instanceof DebitAccount){
                System.out.println("Option " + (i + 1) + ": " + aliasBA + " Type: Debit Account Balance: " + user.getBankAccounts().get(i).getBalance());
            }
            else{
                System.out.println("Option " + (i + 1) + ": " + aliasBA + " Type: Credit Account Balance: " + user.getBankAccounts().get(i).getBalance());
            }
        }
        while(true) {
            try {
                int option = sc.nextInt();
                sc.nextLine();
                //seleccionamos la cuenta que el usuario quiere utilizar
                foundBankAccount = user.bankAccounts.get(option - 1);
                if (foundBankAccount instanceof DebitAccount) {
                    System.out.println("Selected account: " + foundBankAccount.accNumber + " Balance: " + foundBankAccount.balance + " Type: Debit Account");
                    break;
                } else {
                    System.out.println("Selected account: " + foundBankAccount.accNumber + " Balance: " + foundBankAccount.balance + " Type: Credit Account. Limit: " + -((CreditAccount) foundBankAccount).getCreditLimit());
                    break;
                }
            }
            catch (InputMismatchException e) {
                System.err.println("Error please introduce a number");
            }
            catch (IndexOutOfBoundsException e) {
                System.err.println("Error please introduce a valid option");
            }
        }
        return foundBankAccount;
    }

    public void bankAccountHistory(BankAccount bankAccount) {

        if (bankAccount.getHistory().isEmpty()) {
            System.out.println("This account does not have any history yet");
        }
        else {
            System.out.println("- - - Bank Account History - - -");
            String headerFormat = "%-20s | %16s | %-30s | %12s | %12s%n";
            String rowFormat = "%-20s | %16.2f | %-30s | %12.2f | %12.2f%n";
            System.out.printf(headerFormat, "Date", "Previous Balance", "Operation Type", "Amount", "Balance");
            for (BankAccountHistory history : bankAccount.getHistory()) {
                String operation = history.getOperationType();
                if (history.getDestinationAccount() != null) {
                    operation += ": " + history.getDestinationAccount().getAccNumber();
                }
                    System.out.printf(rowFormat,
                            history.getTransactionDate(),
                            history.getPreviousBalance(),
                            operation,
                            history.getTransactionAmount(),
                            history.getNewBalance());
                }
            System.out.println("- - - - - - - - - - - - -  - - -");
        }
    }

    /**
     * Metodo que permite al usuario  simular el pago automatico de la deuda y ver si tendrá balance suficiente para
     * pagarla
     * @param currentUser usuario que paga la deuda
     */
    public void simulation(User currentUser) {

        double totalCreditUsed = 0;

        //buscamos deuda entre las diferentes cuentas bancarias
        for(BankAccount bankAccount : currentUser.getBankAccounts()){
            if(bankAccount instanceof CreditAccount) {
                CreditAccount creditAcc = (CreditAccount) bankAccount;
                //restamos el credito diponible al limite para ver cuanto a gastado realmente
                totalCreditUsed += creditAcc.getCreditLimit() - creditAcc.getAvailableCredit();
                            }
        }
        //de tener deuda restamos al valor total los balances de las cuetnas que tenga
        if(totalCreditUsed > 0) {
            for(BankAccount bankAccount : currentUser.getBankAccounts()){
                if(bankAccount.getBalance() > 0){
                    totalCreditUsed -= bankAccount.getBalance();
                }
            }
        }
        //si es menor o igual a cero no tendrá deuda
        if(totalCreditUsed <= 0){
            System.out.println("You wont have any Debts");
        }
        //de tener mostramos la deuda total que tendrá el usuario
        else{
            System.out.println("You will have " + totalCreditUsed + " total debt");
        }
    }
}