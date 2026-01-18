package Utils;
import Account.BankAccount;
import Person.*;
import java.io.*;

import java.util.ArrayList;

public class Data {
    ArrayList<Person> personsArray = new ArrayList<Person>();
    ArrayList<User> usersArray = new ArrayList<User>();
    ArrayList<Employee> employeesArray = new ArrayList<Employee>();
    ArrayList<BankAccount> bankAccountsArray = new ArrayList<BankAccount>();
    File personsList = new File("Persons.dat");
    File bankAccountsList = new File("BankAccounts.dat");

    public ArrayList<Person> readPersons() {
        if(personsList.exists() && personsList.length() > 0){
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(personsList))){
                personsArray = (ArrayList<Person>) input.readObject();
            }
        catch (IOException | ClassNotFoundException e){
            System.err.println(e.getMessage());
            }
        }
        return personsArray;
    }

    public ArrayList<User> readUsers() {
        if(personsList.exists() && personsList.length() > 0){
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(personsList))){
                ArrayList<Person> tempArray = new  ArrayList<Person>();
                tempArray = (ArrayList<Person>) input.readObject();
                for(Person p : tempArray){
                    if(p instanceof User){
                        usersArray.add((User) p);
                    }
                }
            }
            catch (IOException | ClassNotFoundException e){
                System.err.println(e.getMessage());
            }
        }
        return usersArray;
    }

    public ArrayList<Employee> readEmployees() {
        if(personsList.exists() && personsList.length() > 0){
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(personsList))){
                ArrayList<Person> tempArray = new  ArrayList<Person>();
                tempArray = (ArrayList<Person>) input.readObject();
                for(Person p : tempArray){
                    if(p instanceof Employee){
                        employeesArray.add((Employee) p);
                    }
                }
            }
            catch (IOException | ClassNotFoundException e){
                System.err.println(e.getMessage());
            }
        }
        return employeesArray;
    }

    /*
    public ArrayList<Manager> readManagers() {
        if(personsList.exists() && personsList.length() > 0){
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(personsList))){
                ArrayList<Person> tempArray = new  ArrayList<Person>();
                tempArray = (ArrayList<Person>) input.readObject();
                for(Person p : tempArray){
                    if(p instanceof Manager){
                        employeesArray.add((Manager) p);
                    }
                }
            }
            catch (IOException | ClassNotFoundException e){
                System.err.println(e.getMessage());
            }
        }
        return employeesArray;
    }
     */

    public void writeUsers(ArrayList<User> users){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Persons.dat"))){
            output.writeObject(users);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public void writeEmployees(ArrayList<Employee> employees){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Persons.dat"))){
            output.writeObject(employees);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    /*public void writeManagers(ArrayList<Manager> managers){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Persons.dat"))){
            output.writeObject(managers);
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }
    }*/

    public ArrayList<BankAccount> readBankAccounts() {
        if(bankAccountsList.exists() && bankAccountsList.length() > 0){
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(bankAccountsList))){
                bankAccountsArray = (ArrayList<BankAccount>) input.readObject();
            }
            catch(IOException | ClassNotFoundException e){
                System.err.println(e.getMessage());
            }
        }
        return bankAccountsArray;
    }

    public void writeBankAccounts(ArrayList<BankAccount> bankAccounts){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(bankAccountsList))){
            output.writeObject(bankAccountsArray);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
}
