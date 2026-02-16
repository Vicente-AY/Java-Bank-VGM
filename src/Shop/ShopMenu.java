package Shop;
import Person.User;
import Utils.Data;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que funciona como menú de la tienda del banco
 */
public class ShopMenu {

    Data dataAccess = new Data();
    Buy buyMenu = new Buy();
    Scanner sc = new Scanner(System.in);


    /**
     * Menú de la clase que permite seleccioner las diferentes secciones de la tienda
     * @param currentUser Usuario actual usando la tienda
     */
    public void shopMenu(User currentUser) {
        ArrayList<ShopItem> shopItems = dataAccess.chargeItems();
        int option = 0;
        while(true) {
            try {
                System.out.println("Welcome to Java Shop");
                System.out.println("Please select the section you want to access");
                System.out.println("1. Electronics | 2. Home | 3. Mobility");
                System.out.println("4. Fitness | 5. Outlet | 6. Exit");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        electronicsShop(currentUser, shopItems);
                        break;
                    case 2:
                        homeShop(currentUser, shopItems);
                        break;
                    case 3:
                        mobilityShop(currentUser, shopItems);
                        break;
                    case 4:
                        fitnessShop(currentUser, shopItems);
                        break;
                    case 5:
                        outletShop(currentUser, shopItems);
                        break;
                    case 6:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Please, select a valid option");
                        break;
                }
            }
            catch(InputMismatchException e){
                System.err.println("Please enter a number");
            }
        }
    }

    /**
     * Apartado de electronica de la tienda
     * @param currentUser Usuario actual usando la tienda
     * @param shopItems Lista de todos los objetos de la tienda
     */
    public void electronicsShop(User currentUser, ArrayList<ShopItem> shopItems) {

        ShopItem selectedItem = null;
        ArrayList<ShopItem> electronicItems = new ArrayList<ShopItem>();

        //Recorremos todos los articulos y si coincide con la seecion marcada los guardamos
        for(ShopItem item : shopItems) {
            if(item.getSection().equals("Electronics")){
                electronicItems.add(item);
            }
        }

        //Mostramos por consola los articulos disponibles para la seccion
        System.out.println("Electronics Shop");
        System.out.println("Select the Item you want to buy");
        for(int i = 0; i < electronicItems.size(); i++) {
            System.out.println("Option " + (i + 1) + ": " + electronicItems.get(i).getName()
                                + " : " + electronicItems.get(i).getPrice());
        }

        int option = sc.nextInt();
        sc.nextLine();

        selectedItem = electronicItems.get(option - 1);

        buyMenu.menu(currentUser, selectedItem);
    }

    /**
     * Apartado de Hogar de la tienda
     * @param currentUser Usuario actual usando la tienda
     * @param shopItems Lista de todos los objetos de la tienda
     */
    public void homeShop(User currentUser, ArrayList<ShopItem> shopItems) {

        ShopItem selectedItem = null;
        ArrayList<ShopItem> homeItems = new ArrayList<ShopItem>();

        //Recorremos todos los articulos y si coincide con la seecion marcada los guardamos
        for(ShopItem item : shopItems) {
            if(item.getSection().equals("Home")){
                homeItems.add(item);
            }
        }

        //Mostramos por consola los articulos disponibles para la seccion
        System.out.println("Home Shop");
        System.out.println("Select the Item you want to buy");
        for(int i = 0; i < homeItems.size(); i++) {
            System.out.println("Option " + (i + 1) + ": " + homeItems.get(i).getName()
                    + " : " + homeItems.get(i).getPrice());
        }

        int option = sc.nextInt();
        sc.nextLine();

        selectedItem = homeItems.get(option - 1);

        buyMenu.menu(currentUser, selectedItem);
    }

    /**
     * Apartado de movilidad de la tienda
     * @param currentUser Usuario actual usando la tienda
     * @param shopItems Lista de todos los objetos de la tienda
     */
    public void mobilityShop(User currentUser, ArrayList<ShopItem> shopItems) {

        ShopItem selectedItem = null;
        ArrayList<ShopItem> mobilityItems = new ArrayList<ShopItem>();

        //Recorremos todos los articulos y si coincide con la seecion marcada los guardamos
        for(ShopItem item : shopItems) {
            if(item.getSection().equals("Mobility")){
                mobilityItems.add(item);
            }
        }

        //Mostramos por consola los articulos disponibles para la seccion
        System.out.println("Mobility Shop");
        System.out.println("Select the Item you want to buy");
        for(int i = 0; i < mobilityItems.size(); i++) {
            System.out.println("Option " + (i + 1) + ": " + mobilityItems.get(i).getName()
                    + " : " + mobilityItems.get(i).getPrice());
        }

        int option = sc.nextInt();
        sc.nextLine();

        selectedItem = mobilityItems.get(option - 1);

        buyMenu.menu(currentUser, selectedItem);
    }

    /**
     * Apartado de fitness de la tienda
     * @param currentUser Usuario actual usando la tienda
     * @param shopItems Lista de todos los objetos de la tienda
     */
    public void fitnessShop(User currentUser, ArrayList<ShopItem> shopItems) {

        ShopItem selectedItem = null;
        ArrayList<ShopItem> fitnessItems = new ArrayList<ShopItem>();

        //Recorremos todos los articulos y si coincide con la seecion marcada los guardamos
        for(ShopItem item : shopItems) {
            if(item.getSection().equals("Fitness")){
                fitnessItems.add(item);
            }
        }

        //Mostramos por consola los articulos disponibles para la seccion
        System.out.println("Fitness Shop");
        System.out.println("Select the Item you want to buy");
        for(int i = 0; i < fitnessItems.size(); i++) {
            System.out.println("Option " + (i + 1) + ": " + fitnessItems.get(i).getName()
                    + " : " + fitnessItems.get(i).getPrice());
        }

        int option = sc.nextInt();
        sc.nextLine();

        selectedItem = fitnessItems.get(option - 1);

        buyMenu.menu(currentUser, selectedItem);
    }

    /**
     * Apartado de outlet de la tienda
     * @param currentUser Usuario actual usando la tienda
     * @param shopItems Lista de todos los objetos de la tienda
     */
    public void outletShop(User currentUser, ArrayList<ShopItem> shopItems) {

        ShopItem selectedItem = null;
        ArrayList<ShopItem> outletItems = new ArrayList<ShopItem>();

        //Recorremos todos los articulos y si coincide con la seecion marcada los guardamos
        for(ShopItem item : shopItems) {
            if(item.getSection().equals("Outlet")){
                outletItems.add(item);
            }
        }

        //Mostramos por consola los articulos disponibles para la seccion
        System.out.println("Electronics Shop");
        System.out.println("Select the Item you want to buy");
        for(int i = 0; i < outletItems.size(); i++) {
            System.out.println("Option " + (i + 1) + ": " + outletItems.get(i).getName()
                    + " : " + outletItems.get(i).getPrice());
        }

        int option = sc.nextInt();
        sc.nextLine();

        selectedItem = outletItems.get(option - 1);

        buyMenu.menu(currentUser, selectedItem);
    }
}
