package Person;

import Account.BankAccount;

import java.time.Year;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Scanner;

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

    @Override
    public String getId() {
        return "";
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

    private void deleteBankAccount(){
        ArrayList<User> users = new ArrayList<>();
        //Aqui cargar datos de usuarios
        System.out.println("Enter the User ID number you want to select");
        Scanner sc = new Scanner(System.in);
        String userSelection = sc.nextLine();
        User selectedUser = null;
        BankAccount eraseAccount = null;
        for(User user : users){
            if(userSelection.equals(user.getId())){
                selectedUser = user;
                break;
            }
        }
        if(selectedUser != null){
            for(int i = 0; i < selectedUser.getBankAccounts().size(); i++){
                System.out.println((i + 1) + ". " + selectedUser.getBankAccounts().get(i));
            }
            System.out.println("Select the account you want to delete. Type 0 to cancell");
            Scanner sc2 = new Scanner(System.in);
            int accountSelection = sc2.nextInt();
            if(accountSelection == 0){
                System.out.println("Operation aborted");
                return;
            }
            else {
                eraseAccount = selectedUser.getBankAccounts().get(accountSelection -1);
                if (eraseAccount.getBalance() > 0) {
                    System.out.println("Cancelling operation, the account must be at 0 before deletion");
                    return;
                }
                else {
                    System.out.println("Are you sure do you want to erase Y/N");
                    String choice = sc.nextLine().toLowerCase();
                    if (choice.equals("y") || choice.equals("yes")) {
                        //borrar cuenta bancaria
                    }
                    else{
                        System.out.println("Operation aborted");
                        return;
                    }
                }
            }
        }
    }

    private void unlockAccount(User blockedUser){
        blockedUser.setActive(true);
        System.out.println(blockedUser.getName() + " Has been unlocked");
    }

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

    private boolean canManageEmployees() {
        return canManageEmployees();
    }
}
