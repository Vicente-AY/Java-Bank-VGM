package Seguro;
import Account.BankAccount;
import Menu.*;
import Person.Person;
import Person.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase Hogar para crear seguros de hogar
 */
public class Hogar {
    public int contMin = 0;
    public int minusvalia = 0;
    public int piso = 0;
    public int chalet = 0;
    public int casa = 0;
    public int seguroPiso = 0;
    public int seguroChalet = 0;
    public int seguroCasa = 0;
    public int precioMinusvalia = 0;
    public String tipoSeguro = "";
    public String id ="";
    public Person usuarioActual;

    /**
     * constructor vacio de la clase Hogar
     */
    public Hogar(){
    }

    /**
     * Metodo que te pregunta el ID del usuario
     * @param persons Usuario que tiene ID
     */
    public void hogarPreguntarID(ArrayList<Person> persons) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter user ID:");
        id = sc.nextLine();
        Person currentPerson = null;
        for (int i = 0; i < persons.size(); i++) {
            if (id.equals(persons.get(i).getId())) {
                currentPerson = persons.get(i);
                break;
            }
        }
        if (currentPerson == null) {
            System.out.println("Stated ID is not found, please enter a valid id");
        } else {
            //si la cuenta no esta activa no podrá entrar
            if (!currentPerson.active) {
                System.out.println("The account associated with this ID is blocked.\n Contact a system admin for more information.");
            }
            //si la cuenta no esta activa y además tiene deudas pendientes de hace tiempo no podrá entrar y tendra un aviso de embargo
            else if (currentPerson instanceof User && !currentPerson.active && ((User) currentPerson).getBloquedAccounts()) {
                System.out.println("A court order has been issued to seize your assets");
            }
            else{
                Hogar(currentPerson);
            }
        }
    }


    /**
     * Metodo que inicializa la clase hogar que es un menu que nos deja seleccionar que tipo de seguro queremos
     */
    public void Hogar(Person persons){
        this.usuarioActual = persons;
        Scanner sc = new Scanner(System.in);
        int option = 0;
        while (option != 4) {
            try {
                System.out.println("bienvenido " + persons.name);
                System.out.println("¿Que tipo de seguro quiere?");
                System.out.println("1. Basic");
                System.out.println("2. Intermedium");
                System.out.println("3. Premium");
                System.out.println("4. Return");
                System.out.println("Please enter your numbered choice (1, 2, 3 or 4)");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        Basic();
                        break;
                    case 2:
                        Intermedium();
                        break;
                    case 3:
                        Premium();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Error please introduce a number");
                sc.nextLine();
                option = 0;
            }
        }
    }

    /**
     * Metodo del tipo basico de seguro de hogar que nos pregunta si tenemos algun tipo de minusvalia
     * en caso de decir que si se le sumara el dinero al precio
     */
    public void Basic(){
        Scanner sc = new Scanner(System.in);
        contMin = 100;
        minusvalia = 50;
        piso = 20;
        chalet = 50;
        casa = 30;
        tipoSeguro = "Basic";
        System.out.println("Has seleccionado el Seguro Basic");
        System.out.println("¿Su hogar está adaptado para personas con alguna minusvalía?");
        System.out.println("Introduzca 'Y' para confirmar o 'N' para no confirmar para poder continuar");
        String confirmed = sc.nextLine().toUpperCase();;
        switch (confirmed) {
            case "Y":
                precioMinusvalia = contMin + minusvalia;
                minusvalido();
                break;
            case "N":
                System.out.println("En que tipo de hogar vive usted");
                System.out.println("Un piso, un chalet o una casa?");
                System.out.println("1. Piso");
                System.out.println("2. Chalet");
                System.out.println("3. Casa");
                System.out.println("4. Return");
                System.out.println("Please enter your numbered choice (1, 2, 3 or 4)");
                int tipoCasa = sc.nextInt();
                switch (tipoCasa) {
                    case 1:
                        seguroPiso = contMin + piso;
                        piso();
                        break;
                    case 2:
                        seguroChalet = contMin + chalet;
                        chalet();
                        break;
                    case 3:
                        System.out.println("¿Cuantas plantas tiene su casa?");
                        int plantas = sc.nextInt();
                        int npisos = casa * plantas;
                        seguroCasa = contMin + npisos;
                        casa();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again");
                        Basic();
                        break;
                }
            default:
                System.out.println("Opción inválida. Por favor, introduzca 'Y' o 'N'");
                Basic();
                break;
        }
    }

    /**
     * Metodo del tipo intermedio de seguro de hogar que nos pregunta si tenemos algun tipo de minusvalia
     * en caso de decir que si se le sumara el dinero al precio
     */
    public void Intermedium(){
        Scanner sc = new Scanner(System.in);
        contMin = 170;
        minusvalia = 50;
        piso = 20;
        chalet = 50;
        casa = 30;
        tipoSeguro = "Intermedium";
        System.out.println("Has seleccionado el Seguro Intermedium");
        System.out.println("¿Su hogar está adaptado para personas con alguna minusvalía?");
        System.out.println("Introduzca 'Y' para confirmar o 'N' para no confirmar para poder continuar");
        String confirmed = sc.nextLine().toUpperCase();;
        switch (confirmed){
            case "Y":
                precioMinusvalia = contMin + minusvalia;
                minusvalido();
                break;
            case "N":
                System.out.println("En que tipo de hogar vive usted");
                System.out.println("Un piso, un chalet o una casa?");
                System.out.println("1. Piso");
                System.out.println("2. Chalet");
                System.out.println("3. Casa");
                System.out.println("4. Return");
                System.out.println("Please enter your numbered choice (1, 2, 3 or 4)");
                int tipoCasa = sc.nextInt();
                switch (tipoCasa){
                    case 1:
                        seguroPiso = contMin + piso;
                        piso();
                        break;
                    case 2:
                        seguroChalet = contMin + chalet;
                        chalet();
                        break;
                    case 3:
                        System.out.println("¿Cuantas plantas tiene su casa?");
                        int plantas = sc.nextInt();
                        int npisos = casa * plantas;
                        seguroCasa = contMin + npisos;
                        casa();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again");
                        Intermedium();
                        break;
                }
            default:
                System.out.println("Opción inválida. Por favor, introduzca 'Y' o 'N'");
                Intermedium();
                break;
        }
    }
    /**
     * Metodo del tipo premium de seguro de hogar que nos pregunta si tenemos algun tipo de minusvalia
     * en caso de decir que si se le sumara el dinero al precio
     */
    public void Premium(){
        Scanner sc = new Scanner(System.in);
        contMin = 250;
        minusvalia = 50;
        piso = 20;
        chalet = 50;
        casa = 30;
        tipoSeguro = "Premium";
        System.out.println("Has seleccionado el Seguro Premium");
        System.out.println("¿Su hogar está adaptado para personas con alguna minusvalía?");
        System.out.println("Introduzca 'Y' para confirmar o 'N' para no confirmar para poder continuar");
        String confirmed = sc.nextLine().toUpperCase();;
        switch (confirmed){
            case "Y":
                precioMinusvalia = contMin + minusvalia;
                minusvalido();
                break;
            case "N":
                System.out.println("En que tipo de hogar vive usted");
                System.out.println("Un piso, un chalet o una casa?");
                System.out.println("1. Piso");
                System.out.println("2. Chalet");
                System.out.println("3. Casa");
                System.out.println("4. Return");
                System.out.println("Please enter your numbered choice (1, 2, 3 or 4)");
                int tipoCasa = sc.nextInt();
                switch (tipoCasa){
                    case 1:
                        seguroPiso = contMin + piso;
                        piso();
                        break;
                    case 2:
                        seguroChalet = contMin + chalet;
                        chalet();
                        break;
                    case 3:
                        System.out.println("¿Cuantas plantas tiene su casa?");
                        int plantas = sc.nextInt();
                        int npisos = casa * plantas;
                        seguroCasa = contMin + npisos;
                        casa();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again");
                        Premium();
                        break;
                }
            default:
                System.out.println("Opción inválida. Por favor, introduzca 'Y' o 'N'");
                Premium();
                break;
        }
    }

    /**
     * Metodo booleano que nos permite en caso de excedernos del precio pedirnos que cambiemos de tipo de seguro
     * @param precio si el pago final supera el precio maximo no te dejará pagarlo
     * @return en caso de pasarte del precio maximo te manda al menu anterior
     */
    public boolean validarPrecio(int precio){
        int precioMaximo = 0;

        switch(tipoSeguro){
            case "Basic":
                precioMaximo = 190;
                break;
            case "Intermedium":
                precioMaximo = 300;
                break;
            case "Premium":
                return true;
        }

        if (precio > precioMaximo){
            System.out.println("El precio (" + precio + "€) excede el límite del seguro " + tipoSeguro + " (" + precioMaximo + "€)");
            System.out.println("Por favor, seleccione un seguro de mayor cobertura");
            return false;
        }
        return true;
    }

    /**
     * Metodo que ve si el usuario tiene cuentas bancarias y por defecto usar la primera que tiene
     * @param monto
     */
    public void realizarCobro(double monto) {
        if (usuarioActual instanceof User) {
            User cliente = (User) usuarioActual;

            if (cliente.getBankAccounts().isEmpty()) {
                System.out.println("Error: El usuario no tiene cuentas bancarias vinculadas.");
                return;
            }

            BankAccount cuenta = cliente.getBankAccounts().get(0);

            System.out.println("Intentando cobrar " + monto + "€ de la cuenta: " + cuenta.getAccountAlias());
            cuenta.withdraw(monto);
        } else {
            System.out.println("Este tipo de usuario no puede realizar compras.");
        }
    }

    /**
     * Metodo que hace que en caso de tener algun tipo nos minusvalia nos manda a este metod y eso le sumara al precio del seguro
     */
    public void minusvalido() {
        Scanner sc = new Scanner(System.in);
        System.out.println("En que tipo de hogar vive usted");
        System.out.println("Un piso, un chalet o una casa?");
        System.out.println("1. Piso");
        System.out.println("2. Chalet");
        System.out.println("3. Casa");
        System.out.println("4. Return");
        System.out.println("Please enter your numbered choice (1, 2, 3 or 4)");
        int tipoCasa = sc.nextInt();
        switch (tipoCasa) {
            case 1:
                seguroPiso = precioMinusvalia + piso;
                piso();
                break;
            case 2:
                seguroChalet = precioMinusvalia + chalet;
                chalet();
                break;
            case 3:
                System.out.println("¿Cuantas plantas tiene su casa?");
                int plantas = sc.nextInt();
                int npisos = casa * plantas;
                seguroCasa = precioMinusvalia + npisos;
                casa();
                break;
            case 4:
                return;

        default:
        System.out.println("Invalid option. Please try again");
        break;
        }
    }

    /**
     * Metodo que en caso de seleccionar que tienes un piso te manda aquí para sumarle el precio que tiene el piso
     */
    public void piso(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Usted tiene un piso");
        System.out.println("El precio total de su seguro es " + seguroPiso + "€");
        if (!validarPrecio(seguroPiso)){
            return;
        }
        System.out.println("Quiere continuar con el pago? Y/N para aceptar o rechazar");
        String conf = sc.nextLine().toUpperCase();
        if (conf.equals("Y")){
            realizarCobro(seguroPiso);
            System.out.println("Seguro comprado exitosamente");
        }
        else if (conf.equals("N")){
            System.out.println("Seguro rechazado exitosamente");
            System.out.println("Volviendo al menú de seguro de hogar");
            return;
        }
        else{
            System.out.println("Error tienes que Seleccionar 'Y' para confirmar o 'N' para rechazar");
            piso();
        }
    }

    /**
     * Metodo que te manda si tienes un chalet para que así sumarle el precio del chalet
     */
    public void chalet(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Usted tiene un Chalet");
        System.out.println("El precio total de su seguro es " + seguroChalet + "€");
        if (!validarPrecio(seguroChalet)){
            return;
        }
        System.out.println("Quiere continuar con el pago? Y/N para aceptar o rechazar");
        String conf = sc.nextLine().toUpperCase();
        if (conf.equals("Y")){
            realizarCobro(seguroChalet);
            System.out.println("Seguro comprado exitosamente");
        }
        else if (conf.equals("N")){
            System.out.println("Seguro rechazado exitosamente");
            System.out.println("Volviendo al menú de seguro de hogar");
            return;
        }
        else{
            System.out.println("Error tienes que Seleccionar 'Y' para confirmar o 'N' para rechazar");
            chalet();
        }
    }

    /**
     * Metodo de casa que te manda en caso de seleccionar casa y se le sumará el precio
     */
    public void casa(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Usted tiene una casa");
        System.out.println("El precio total de su seguro es " + seguroCasa + "€");
        if (!validarPrecio(seguroCasa)){
            return;
        }
        System.out.println("Quiere continuar con el pago? Y/N para aceptar o rechazar");
        String conf = sc.nextLine().toUpperCase();
        if (conf.equals("Y")){
            realizarCobro(seguroCasa);
            System.out.println("Seguro comprado exitosamente");
            return;
        }
        else if (conf.equals("N")){
            System.out.println("Seguro rechazado exitosamente");
            System.out.println("Volviendo al menú de seguro de hogar");
            return;
        }
        else{
            System.out.println("Error tienes que Seleccionar 'Y' para confirmar o 'N' para rechazar");
            casa();
        }
    }
}