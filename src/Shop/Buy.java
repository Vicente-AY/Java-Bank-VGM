package Shop;

import Account.*;
import Person.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Buy {

    Scanner sc = new Scanner(System.in);

    /**
     * Metodo que guia al usuariao entre los diferentes metodos de pago de la tienda
     * @param currentUser
     * @param selectedItem
     */
    public void menu(User currentUser, ShopItem selectedItem){

        int option = 0;
        while(true){
            try {
                System.out.println("Select the payment option");
                System.out.println("1. Bank Account | 2. Card | 3. Bizum");
                option = sc.nextInt();
                sc.nextLine();
                switch (option) {
                    case 1:
                        bankAccountPayment(currentUser, selectedItem);
                        return;
                    case 2:
                        cardPayment(currentUser, selectedItem);
                        return;
                    case 3:
                        //bizumPayment(currentUser, selectedItem);
                        return;
                    case 4:
                        System.out.println("Cancelling Operation");
                        return;
                    default:
                        System.out.println("Invalid option");
                }
            }
            catch (InputMismatchException e) {
                System.err.println("Please enter a number");
            }
        }
    }

    /**
     * Metodo que permite al usuario pagar en la tienda atraves de su cuenta bancaria
     * @param currentUser usuario actual utilizando la tienda
     * @param selectedItem articulo que el usuario desea comprar
     */
    public void bankAccountPayment(User currentUser, ShopItem selectedItem){

        BankAccount selectedBankAccount = null;
        System.out.println("Please select the Bank account you want to pay with");

        for(int i = 0; i < currentUser.getBankAccounts().size(); i++) {
            if(currentUser.getBankAccounts().get(i) instanceof DebitAccount){
                System.out.println("Option: " + (i + 1) + currentUser.getBankAccounts().get(i).getAccNumber()
                                  + " Balance: " + currentUser.getBankAccounts().get(i).getBalance());
            }
            else if(currentUser.getBankAccounts().get(i) instanceof CreditAccount){
                System.out.println("Option: " + (i + 1) + currentUser.getBankAccounts().get(i).getAccNumber()
                                  +  " Balance: " + currentUser.getBankAccounts().get(i).getBalance()
                                  + " Avaiable Credit: " + ((CreditAccount) currentUser.getBankAccounts().get(i)).getAvailableCredit());
            }
        }

        int option = 0;
        while(true) {
            try {
                option = sc.nextInt();
                sc.nextLine();
                break;
            }
            catch (InputMismatchException e) {
                System.err.println("Please enter a number");
            }
        }

        selectedBankAccount = currentUser.getBankAccounts().get(option -1);

        selectedBankAccount.shopPayment(selectedItem.getPrice(), selectedItem);
    }

    /**
     * Metodo que permite pagar con tarjeta bancaria
     * @param currentUser usuario actual utilizando la tienda
     * @param selectedItem articulo elegido por el usuario
     */
    public void cardPayment(User currentUser, ShopItem selectedItem){

        ArrayList<Card> cards = new ArrayList<>();

        System.out.println("Please select the card you want to pay with");
        for(int i = 0; i < currentUser.getBankAccounts().size(); i++){
            if(!currentUser.getBankAccounts().get(i).getCards().isEmpty()){
                for(Card c : currentUser.getBankAccounts().get(i).getCards()){
                    cards.add(c);
                }
            }
        }

        for(int i = 0; i < cards.size(); i++){
            System.out.println("Option " + (i + 1) + " :" + cards.get(i).getCardNumber());
        }
        int option = 0;
        while(true) {
            try {
                option = sc.nextInt();
                sc.nextLine();
                break;
            }
            catch (InputMismatchException e) {
                System.err.println("Please enter a number");
            }
        }

        Card selectedCard = cards.get(option -1);

        selectedCard.cardPayment(selectedItem.getPrice(), selectedItem, selectedCard);


    }

    /*public void bizumPayment(User currentUser, ShopItem selectedItem){

    }*/
}
