package Seguro;

import Account.BankAccount;
import Person.Person;
import Person.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase para poder comprar un seguro de coche
 */
public class Coche {
    public int precioBase = 0;
    public int suplementoEdad = 0;
    public int suplementoPotencia = 0;
    public int suplementoUbicacion = 0;
    public int precioFinal = 0;
    public String tipoSeguro = "";
    public int edadConductor = 0;
    public int experienciaConductor = 0;
    public String modeloCoche = "";
    public int potenciaCoche = 0;
    public String ubicacion = "";
    public String id ="";
    public Person usuarioActual;

    /**
     * constructor vacio de la clase coche
     */
    public Coche(){
    }

    /**
     * Metodo que te pregunta el ID del usuario
     * @param persons Usuario que tiene ID
     */
    public void cochePreguntarID(ArrayList<Person> persons) {
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
                Coche(currentPerson);
            }
        }
    }

    /**
     * Metodo de la clase Seguro coche que es un menu donde te deja elegir que tipo de seguro quieres
     */
    public void Coche(Person persons){
        Scanner sc = new Scanner(System.in);
        this.usuarioActual = persons;
        int option = 0;
        while (option !=4) {
            try {
                System.out.println("bienvenido " + persons.name);
                System.out.println("¿Que tipo de seguro quiere?");
                System.out.println("1. Terceros Básicos");
                System.out.println("2. Terceros Ampliado");
                System.out.println("3. Todo Riesgo con Franquicia");
                System.out.println("4. Todo riesgo sin Franquicia");
                System.out.println("5. Volver");
                System.out.println("Please enter your numbered choice (1, 2, 3, 4 or 5)");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        tercerosBasico();
                        break;
                    case 2:
                        tercerosAmpliado();
                        break;
                    case 3:
                        todoRiesgoFranquicia();
                        break;
                    case 4:
                        todoRiesgoSinFranquicia();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid option. Please try again");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Por favor introduzca un número");
                sc.nextLine();
                option = 0;
            }
        }
    }

    /**
     * Metodo de tercero basico que te dice el precio base y te manda a otros metodos
     */
    public void tercerosBasico(){
        Scanner sc = new Scanner(System.in);
        precioBase = 160;
        tipoSeguro = "Tercero Básico";
        System.out.println("Has seleccionado " + tipoSeguro );
        System.out.println("El precio base es de " + precioBase + "€/año");

        recogerDatosConductor(sc);
        recogerDatosVehiculo(sc);
        calcularPrecioTerceros();
        mostrarResumenYConfirmar(sc);
    }

    /**
     * Metodo de tercero ampliado que te dice el precio base y te manda a otros metodos
     */
    public void tercerosAmpliado(){
        Scanner sc = new Scanner(System.in);
        precioBase = 200;
        tipoSeguro = "Tercero Ampliado";
        System.out.println("Has seleccionado " + tipoSeguro );
        System.out.println("El precio base es de " + precioBase + "€/año");

        recogerDatosConductor(sc);
        recogerDatosVehiculo(sc);
        calcularPrecioTerceros();
        mostrarResumenYConfirmar(sc);

    }

    /**
     * Metodo de todo riesgo con franquicia que te dice el precio base y te manda a otros metodos
     */
    public void todoRiesgoFranquicia(){
        Scanner sc = new Scanner(System.in);
        precioBase = 230;
        tipoSeguro = "Todo Riesgo con Franquicia";
        System.out.println("Has seleccionado " + tipoSeguro );
        System.out.println("El precio base es de " + precioBase + "€/año");

        recogerDatosConductor(sc);
        recogerDatosVehiculo(sc);
        calcularPrecioTerceros();
        mostrarResumenYConfirmar(sc);

    }

    /**
     * Metodo de todo riesgo sin fraquicia que te dice el precio base y te manda a otros metodos
     */
    public void todoRiesgoSinFranquicia(){
        Scanner sc = new Scanner(System.in);
        precioBase = 500;
        tipoSeguro = "Todo Riesgo sin Franquicia";
        System.out.println("Has seleccionado " + tipoSeguro );
        System.out.println("El precio base es de " + precioBase + "€/año");

        recogerDatosConductor(sc);
        recogerDatosVehiculo(sc);
        calcularPrecioTerceros();
        mostrarResumenYConfirmar(sc);

    }

    /**
     * metodo que recoge los datos del conductor que nosotros le introducimos
     * @param sc del scanner
     */
    public void recogerDatosConductor(Scanner sc){
        System.out.println("Datos del conductor");
        System.out.println("Edad del conductor");
        edadConductor = sc.nextInt();
        System.out.println("Años de experiencia del conductor");
        experienciaConductor = sc.nextInt();
        sc.nextLine();
    }

    /**
     * metodo que recoge los datos del vehiculo que nosotros le introducimos
     * @param sc del scanner
     */
    public void recogerDatosVehiculo(Scanner sc){
        System.out.println("Datos del vehículo");
        System.out.println("Modelo del coche");
        modeloCoche = sc.nextLine();
        System.out.println("Potencia del coche");
        potenciaCoche = sc.nextInt();
        sc.nextLine();
        System.out.println("ubicación (ciudad)");
        ubicacion = sc.nextLine();
    }

    /**
     * Metodo que calcula el precio total de el seguro a terceros
     */
    public void calcularPrecioTerceros(){
        precioFinal = precioBase;
        if (edadConductor < 25){
            suplementoEdad = 50;
            precioFinal += suplementoEdad;
        }
        else if (edadConductor > 25 && edadConductor < 30){
            suplementoEdad = 25;
            precioFinal += suplementoEdad;
        }
        if (experienciaConductor > 10){
            precioFinal -= 30;
        }
        else if (experienciaConductor > 5){
            precioFinal -= 15;
        }
        if (potenciaCoche > 150){
            suplementoPotencia = 80;
            precioFinal += suplementoPotencia;
        }
        else if (potenciaCoche > 100){
            suplementoPotencia = 40;
            precioFinal += suplementoPotencia;
        }
        if (ubicacion.equalsIgnoreCase("Madrid") || ubicacion.equalsIgnoreCase("Barcelona")){
            suplementoUbicacion = 40;
            precioFinal += suplementoUbicacion;
        }
    }

    /**
     * Metodo que calcula el precio total del seguro a todo riesgo
     */
    public void calcularPrecioTodoRiesgo(){
        precioFinal = precioBase;
        if (edadConductor < 25){
            suplementoEdad = 100;
            precioFinal += suplementoEdad;
        }
        else if (edadConductor > 25 && edadConductor < 30){
            suplementoEdad = 50;
            precioFinal += suplementoEdad;
        }
        if (experienciaConductor > 10){
            precioFinal -= 50;
        }
        else if (experienciaConductor > 5){
            precioFinal -= 25;
        }
        if (potenciaCoche > 150){
            suplementoPotencia = 120;
            precioFinal += suplementoPotencia;
        }
        else if (potenciaCoche > 100){
            suplementoPotencia = 60;
            precioFinal += suplementoPotencia;
        }
        if (ubicacion.equalsIgnoreCase("Madrid") || ubicacion.equalsIgnoreCase("Barcelona")){
            suplementoUbicacion = 60;
            precioFinal += suplementoUbicacion;
        }
    }

    /**
     * Metodo que ve si el usuario tiene cuentas bancarias y por defecto usar la primera que tiene
     * @param monto
     */
    public void realizarCobro(double monto) {
        if (usuarioActual instanceof User) {
            User cliente = (User) usuarioActual;

            if (cliente.getBankAccounts().isEmpty()) {
                System.out.println("Error: El usuario no tiene cuentas bancarias.");
                return;
            }

            BankAccount cuenta = cliente.getBankAccounts().get(0);

            System.out.println("Cobrando " + monto + "€ de la cuenta: " + cuenta.getAccountAlias());
            cuenta.withdraw(monto);
        } else {
            System.out.println("Este usuario no es un Cliente (User) y no tiene cuentas.");
        }
    }

    /**
     * metodo final que te dice los datos finales y para confirmar o rechazar el pago del seguro
     * @param sc del scanner
     */
    public  void mostrarResumenYConfirmar(Scanner sc){
        System.out.println("Resumen del seguro");
        System.out.println("Tipo de seguro " + tipoSeguro);
        System.out.println("Conductor " + edadConductor + " años, " + experienciaConductor + " años de experiencia");
        System.out.println("Vehiculo " + modeloCoche + " " + potenciaCoche);
        System.out.println("Ubicacion " + ubicacion);
        System.out.println("Desglose de precio");
        System.out.println("Precio base " + precioBase + "€");
        if (suplementoEdad > 0) System.out.println("Suplemento por edad + " + suplementoEdad + "€" );
        if (suplementoPotencia > 0) System.out.println("Suplemento por potencia +" + suplementoPotencia + "€");
        if (suplementoUbicacion > 0) System.out.println("Suplemento por ubicación +" + suplementoUbicacion + "€");
        System.out.println("Precio final " + precioFinal + "€/año");
        System.out.print("¿Desea contratar este seguro? (Y/N): ");
        String confirmacion = sc.nextLine().toUpperCase();
        if (confirmacion.equals("Y")){
            realizarCobro(precioFinal);
            System.out.println("Seguro contratado exitosamente");
        }
        else if (confirmacion.equals("N")){
            System.out.println("Seguro rechazado");
            System.out.println("volviendo al menú...");
            return;
        }
        else {
            System.out.println("Opción inválida. Por favor, introduzca 'Y' o 'N'");
            mostrarResumenYConfirmar(sc);
        }
    }
}