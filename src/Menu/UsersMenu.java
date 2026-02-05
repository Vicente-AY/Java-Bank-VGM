package Menu;
import Person.*;
import Account.*;

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
        double outstandingDebt = 0;

        selectedBankAccount = null;
        //Imprimimos el menú, si tiene cuenta seleccionada la mostramos por consola también
        int option = 0;
        while (true) {

            outstandingDebt = 0;

            for(BankAccount bankAccount : ((User) currentUser).getBankAccounts()){
                if(bankAccount instanceof CreditAccount){
                    CreditAccount creditAccount = (CreditAccount) bankAccount;
                    outstandingDebt += (creditAccount.getCreditLimit() - creditAccount.getAvailableCredit());
                }
            }

            try {
                System.out.println("Welcome " + currentUser.name);
                if (selectedBankAccount != null) {
                    System.out.println("Selected account: " + selectedBankAccount.accNumber + " | Balance: " + selectedBankAccount.getBalance());
                    if (selectedBankAccount instanceof DebitAccount) {
                        System.out.println("Type: Debit Account");
                    } else {
                        CreditAccount ca =  (CreditAccount) selectedBankAccount;
                        System.out.println("Available Credit: " + ca.getAvailableCredit() + "/" + ca.getCreditLimit());
                    }
                }
                if(((User) currentUser).getDebtor() && outstandingDebt > 0){
                    System.out.println("0. PAY DEBT");
                }
                System.out.println("1. Select a BankAccount");
                System.out.println("2. Make a deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Transfer Money");
                System.out.println("5. Recharge SIM card");
                System.out.println("6. See Bank Account History");
                System.out.println("7. Log Out");

                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 0:
                        if(outstandingDebt > 0){
                            payDebts(currentUser);
                            break;
                        }
                        else{
                            System.out.println("Invalid Option");
                            break;
                        }
                    case 1:
                        selectedBankAccount = selectAccount((User) currentUser);
                        break;
                    case 2, 3, 5:
                        if (selectedBankAccount == null) {
                            System.out.println("Please select and account first");
                            break;
                        }

                        if(((User) currentUser).getBloquedAccounts()){
                            System.out.println("Your accounts are  BLOQUED. Pay your debt at option 0");
                            break;
                        }

                        if(((User) currentUser).getDebtor() && selectedBankAccount instanceof CreditAccount && option != 2){
                            System.out.println("Your account has credit suspended. Pay your debt to gain complete access again");
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
                            System.out.println("Your accounts have been bloqued. Pay your debt to gain complete access again");
                            break;
                        }
                        if(((User) currentUser).getDebtor() && selectedBankAccount instanceof CreditAccount){
                            System.out.println("Your account has credit suspended. Pay your debt to gain complete access again");
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
     * Metodo que permite al usuario  pagar sus deudas
     * @param currentUser usuario que paga la deuda
     */
    public void payDebts(Person currentUser){

        Scanner sc = new Scanner(System.in);

        ArrayList<BankAccount> debtAccounts = new ArrayList<>();
        ArrayList<BankAccount> positiveAccounts = new ArrayList<>();

        //filtramos las listas enter las que tienen deudas y las que tienen balance positivo
        for(BankAccount bankAccount : ((User) currentUser).getBankAccounts()){
            if(bankAccount.getBalance() > 0){
                positiveAccounts.add(bankAccount);
            }
            if(bankAccount instanceof CreditAccount && ((CreditAccount) bankAccount).getAvailableCredit() < ((CreditAccount) bankAccount).getCreditLimit()){
                debtAccounts.add(bankAccount);
            }
        }

        //mostramos las dudas pendientes del usuario
        System.out.println("Select the account you want to pay the debt");
        for(int i = 0; i < debtAccounts.size(); i++){
            CreditAccount ca = (CreditAccount) debtAccounts.get(i);
            double debt = ca.getCreditLimit() -  ca.getAvailableCredit();
                System.out.println("Option: " + (i +1) + ": " + debtAccounts.get(i).getAccNumber()
                        + " Pending Debt: " + debt);
            }
        int choice = sc.nextInt();
        sc.nextLine();
        CreditAccount debtBankAccount = (CreditAccount) debtAccounts.get(choice -1);
        double debtAmount = debtBankAccount.getCreditLimit() - debtBankAccount.getAvailableCredit();

        int option = 0;
        while(true) {
            try {
                System.out.println("Select the option of how do you want to pay the debt");
                //el usuario podra usar un deposito para pagar la deuda o una transferencia de otra cuenta
                System.out.println("1. Deposit | 2. Transfer | 3. Cancell");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        System.out.println("Introduce the amount you want to deposit");
                        double amount = sc.nextInt();
                        sc.nextLine();

                        if(amount >= debtAmount){
                            debtBankAccount.setAvailableCredit(debtBankAccount.getCreditLimit());
                            debtBankAccount.setBalance(debtBankAccount.getBalance() + (amount - debtAmount));
                            System.out.println("You have successfully payed the debt. Next month you will be able to access the credit again");
                        }
                        else{
                            debtBankAccount.setAvailableCredit(debtBankAccount.getAvailableCredit() + amount);
                            System.out.println("You repayed: " + amount);
                        }
                        return;
                    //Mostramos las cuentas donde el usuario tiene balance positivo
                    case 2:
                        if (positiveAccounts.isEmpty()) {
                            System.out.println("You dont have any Bank Account with positive balance");
                            return;
                        }
                        System.out.println("Introduce the option to select the Bank account");
                        for (int i = 0; i < positiveAccounts.size(); i++) {
                            System.out.println("Option: " + (i + 1) + ": " + positiveAccounts.get(i).getAccNumber()
                                    + " Balance: " + positiveAccounts.get(i).getBalance());
                        }
                        int choice2 = sc.nextInt();
                        sc.nextLine();
                        //Al elegir cuenta, extraerá la mayor cantidad posible para pagar la deuda
                        BankAccount selectedBankAccount = positiveAccounts.get(choice2 - 1);

                        if (selectedBankAccount.getBalance() < debtAmount) {
                            double payment = selectedBankAccount.getBalance();
                            debtBankAccount.setAvailableCredit(debtBankAccount.getAvailableCredit() + payment);
                            selectedBankAccount.setBalance(0);
                            System.out.println("Repayed: " + payment);
                            return;

                        }
                        else{
                            selectedBankAccount.setBalance(selectedBankAccount.getBalance() - debtAmount);
                            debtBankAccount.setAvailableCredit(debtBankAccount.getCreditLimit());
                            System.out.println("You have successfully payed the debt usint: " + selectedBankAccount.getAccNumber() + " Next month you will be able to access the credit again");
                            return;
                        }
                    case 3:
                        System.out.println("Cancelling operation");
                        return;
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
}