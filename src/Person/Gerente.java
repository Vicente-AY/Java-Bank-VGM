package Person;

import Account.BankAccount;

import java.time.Year;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;

public class Gerente extends Person {
    int gerenteid;
    public static int id =0;

    public Gerente(String name, String password, String birthDate, Object o) {
        super(name, password, birthDate);
        this.gerenteid = gerenteid;
    }


    @Override
    public Gerente register() {
        Scanner sc = new Scanner(System.in);
        String name, birthdate, password;
        boolean checkP=false, checkD=false;
        System.out.println("Please Gerente enter your name and surnames");
        name = sc.nextLine();

        System.out.println("Please enter your password");
        password = sc.nextLine();
        checkP = checkPassword(password);
        while (!checkP){
            System.out.println("The password you entered is incorrect");
            System.out.println("The password must contain:");
            System.out.println("* 1 uppercase letter");
            System.out.println("* 1 lowercase letter");
            System.out.println("* 1 number");
            System.out.println("* 1 special character");
            password = sc.nextLine();
            checkP = checkPassword(password);
        }

        System.out.println("Please enter your birthdate (dd/mm/yyyy)");
        birthdate = sc.nextLine();
        checkD = checkDate(birthdate);
        while(!checkD){
            System.out.println("The date you entered is incorrect, please try again");
            System.out.println("Remember to use the following format: dd/mm/yyyy");
            birthdate = sc.nextLine();
            checkD = checkDate(birthdate);
        }
        id += 1;
        String newId = createId(id);
        Gerente newGerente = new Gerente(name, password, birthdate, newId);
        System.out.println("The register process has ended");
        System.out.println("Your data:");
        System.out.println("Name: " + name);
        System.out.println("Birthdate: " + birthdate);
        System.out.println("Password: " + password);
        System.out.println("Id: " + newId);
        return newGerente;
    }

    @Override
    public boolean checkPassword(String password){ //regex password
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if(password.matches(pattern)){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean checkDate(String date){
        String regex = "[,//.\\s]";
        String[] myArray = date.split(regex);
        int element1 = Integer.parseInt(myArray[0]);
        int element2 = Integer.parseInt(myArray[1]);
        int element3 = Integer.parseInt(myArray[2]);
        int year = Year.now().getValue();

        if (element1 > 32 || element1 <0){//check if the day is between 1 and 31
            return false;
        }
        if(  element2 == 4 || element2 == 6 || element2 == 9 ||  element2 == 11 ){//check if it is a 30-day month
            if (element1 >30){
                return false;
            }
        }
        if (element2 == 2  ) { //check if february
            if (element3 % 4 == 0) {
                if (element1 > 29) {//leap year
                    return false;
                }
            } else {
                if (element1 > 28) {//normal year
                    return false;
                }
            }
        }
        if (element3 < 1900 || element3 > year) {
            return false;
        }
        return true;
    }

    public String createId(int id){
        String newId ="";
        for (int i= String.valueOf(id).length(); i < 8; i++){
            newId = "0" + newId;
        }
        return newId;
    }

    public BankAccount createBankAccount(){
        return null;
    }

    // PERMISOS DE GERENTE - ACCESO TOTAL
    public boolean canCreateAccount() {
        return true; // Los gerentes SÍ pueden crear cuentas
    }

    public boolean canDeleteAccount() {
        return true; // Los gerentes SÍ pueden borrar cuentas
    }

    public double getWithdrawalLimit() {
        return Double.MAX_VALUE; // Sin límite
    }

    public boolean canManageEmployees() {
        return true; // Los gerentes SÍ pueden gestionar empleados
    }

    public boolean canUnblockAccounts() {
        return true; // Los gerentes SÍ pueden desbloquear cuentas
    }

    // MÉTODOS ESPECÍFICOS DE GERENTE

    // Crear cuenta bancaria para un cliente
    public BankAccount createBankAccountForClient(User client) {
        if (!canCreateAccount()) {
            System.out.println("You don't have permission to create accounts");
            return null;
        }
        System.out.println("[MANAGER] Creating bank account for client: " + client.name);
        // Aquí iría la lógica real de creación
        return null;
    }

    // Borrar cuenta bancaria
    public boolean deleteBankAccount(BankAccount account) {
        if (!canDeleteAccount()) {
            System.out.println("You don't have permission to delete accounts");
            return false;
        }
        System.out.println("[MANAGER] Deleting account: " + account.getAccNumber());
        System.out.println("Account successfully deleted");
        return true;
    }

    // Desbloquear usuario
    public boolean unblockUser(Person user) {
        if (!canUnblockAccounts()) {
            System.out.println("You don't have permission to unblock accounts");
            return false;
        }

        if (user.active) {
            System.out.println("User " + user.name + " is not blocked");
            return false;
        }

        user.active = true;
        System.out.println("[MANAGER] User " + user.name + " (ID: " + user.id + ") has been unblocked");
        return true;
    }

    // Crear empleado
    public Employee createEmployee(ArrayList<Employee> employeeList) {
        if (!canManageEmployees()) {
            System.out.println("You don't have permission to create employees");
            return null;
        }

        System.out.println("[MANAGER] Creating new employee...");
        Employee dummyEmployee = new Employee(null, null, null, null);
        Employee newEmployee = dummyEmployee.register();

        if (newEmployee != null) {
            employeeList.add(newEmployee);
            System.out.println("[MANAGER] Employee created successfully!");
        }

        return newEmployee;
    }

    // Eliminar empleado
    public boolean deleteEmployee(ArrayList<Employee> employeeList, String employeeId) {
        if (!canManageEmployees()) {
            System.out.println("You don't have permission to delete employees");
            return false;
        }

        for (int i = 0; i < employeeList.size(); i++) {
            if (Objects.equals(employeeList.get(i).id, employeeId)) {
                Employee removed = employeeList.remove(i);
                System.out.println("[MANAGER] Employee " + removed.name + " (ID: " + employeeId + ") has been removed");
                return true;
            }
        }

        System.out.println("Employee with ID " + employeeId + " not found");
        return false;
    }
}
