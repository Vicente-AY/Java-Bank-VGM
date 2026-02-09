package Shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class ShopItems implements Serializable {
    int id;
    String name;
    String description;
    double price;
    String section;

    public ShopItems(int id, String name, String description, double price, String section) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.section = section;
    }

    public void createItem(ArrayList<ShopItems> shopItems) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Creating new item");
        System.out.println("Introduce a name for the item");
        String name = sc.nextLine();

        System.out.println("Introduce a description for the item");
        String description = sc.nextLine();

        System.out.println("Introduce a price for the item");
        double price = sc.nextDouble();
        sc.nextLine();

        System.out.println("Introduce a section for the item");
        String section = sc.nextLine();

        int id = 0;
        for (ShopItems item : shopItems) {
            if (item.getId() > id) {
                id = item.getId();
            }
        }
        id = id + 1;

        ShopItems item = new ShopItems(id, name, description, price, section);
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
