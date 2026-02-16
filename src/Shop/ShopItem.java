package Shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que permite la creación y guardado de los articulos de la tienda
 */
public class ShopItem implements Serializable {
    int id;
    String name;
    String description;
    double price;
    String section;

    /**
     * Constructor de la clase
     * @param id entero auto incremental diferenciador
     * @param name identificador del articulo
     * @param description pequeño texto explicativo del articulo
     * @param price double con el valor del articulo
     * @param section sección a la que pertenece al articulo
     */
    public ShopItem(int id, String name, String description, double price, String section) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.section = section;
    }

    /**
     * Metodo que crea articulos para la tienda
     * @param shopItems Lista con todos los articulos. Los nuevos articulos se introducirán en esta lista
     */
    public static void createItem(ArrayList<ShopItem> shopItems) {

        int option = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("Creating new item");
        System.out.println("Introduce a name for the item");
        String name = sc.nextLine();

        System.out.println("Introduce a description for the item");
        String description = sc.nextLine();

        System.out.println("Introduce a price for the item");
        double price = sc.nextDouble();
        sc.nextLine();

        //Utilizamos este menú con las secciones preconstruidas para evitar errores
        System.out.println("Introduce a section for the item");
        System.out.println("1. Electronics | 2. Home | 3. Movility");
        System.out.println("4. Fitness | 5. Outlet | 6. Cancell");
        boolean stop = false;
        String section = "";
        while(!stop){
            try {
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        section = "Electronics";
                        stop = true;
                        break;
                    case 2:
                        section = "Home";
                        stop = true;
                        break;
                    case 3:
                        section = "Movility";
                        stop = true;
                        break;
                    case 4:
                        section = "Fitness";
                        stop = true;
                        break;
                    case 5:
                        section = "Outlet";
                        stop = true;
                        break;
                    case 6:
                        System.out.println("Cancelling");
                        return;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
            catch (InputMismatchException e) {
                System.err.println("Please, introduce a number");
                sc.nextLine();
            }
        }

        int id = 0;
        for (ShopItem item : shopItems) {
            if (item.getId() > id) {
                id = item.getId();
            }
        }
        id = id + 1;

        ShopItem item = new ShopItem(id, name, description, price, section);
        shopItems.add(item);
    }




    //Getters y Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }
}
