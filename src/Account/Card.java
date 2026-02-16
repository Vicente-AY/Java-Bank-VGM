package Account;

import Shop.ShopItem;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

/**
 * Clase que representa una tarjeta bancaria. Vincula las operaciones y restricciones
 * de la cuenta que tiene asociada
 */
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;
    private String accountIBAN;
    private String cardNumber;
    private String cvv;
    private String cardHolder;
    private String expirationDate;
    private boolean active = true;
    BankAccount associatedAccount;

    /**
     * Constructor interno de la clase
     * @param accountIBAN numero descendiente de la cuenta bancaria asociada
     * @param cardHolder usuario dueño de la tarjeta
     * @param cardNumber numero de la tarjeta calculado por el algoritmo internacional estandard
     * @param cvv digitos de control generados de forma aleatoria
     * @param associatedAccount cuenta bancaria que usará la tarjeta al ser usada
     */
    public Card(String accountIBAN, String cardHolder, String cardNumber, String cvv, BankAccount associatedAccount, String expirationDate) {
        this.accountIBAN = accountIBAN;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.associatedAccount = associatedAccount;
    }

    /**
     * Crea una nueva tarjeta calculando los datos necesarios automaticamente y vinculandola
     * la cuenta bancaria seleccionada
     * @param bankAccount cuenta asociada a la tarjeta
     * @param owner nombre del titular
     */
    public static void createNewCard(BankAccount bankAccount, String owner){

        //Calculamos la fecha de caducidad de la tarjeta (cuatro años de vigencia)
        LocalDate today = LocalDate.now();
        LocalDate expiration = today.plusYears(4);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        String expirDate = expiration.format(formatter);

        //generamos los datos mediante los metodos que implementan los algoritmos
        String generatedNumber = generateLuhnNumber();
        String generatedCVV = generateCVV();

        //Creamos la tarjeta con los datos proporcionados y calculados
        Card newCard = new Card(bankAccount.getIBAN(), owner, generatedNumber, generatedCVV, bankAccount, expirDate);
        //añadimos la tarjeta al arrayList de tarjetas de las cuentas bancarias
        bankAccount.getCards().add(newCard);

        //mostramos los datos por consola
        System.out.println("Card created successfully. Data:");
        System.out.println("Associated Bank account: " + bankAccount.getIBAN());
        System.out.println("Card number: " + generatedNumber);
        System.out.println("Card CVV: " + generatedCVV);
        System.out.println("Card holder: " + owner);
        System.out.println("Expiration Date: " + expirDate);
    }

    /**
     * Genera un numero de tarjeta de 16 digitos utilizando el algoritmo estandard Luhn
     * @return numero de la tarjeta
     */
    private static String generateLuhnNumber(){
        Random rand = new Random();
        int[] digits = new int[16];

        //primer digito es el identificador de industria
        digits[0] = rand.nextBoolean() ? 4 : 5;

        //Llenamos el array con digitos aleatorios entre 1 y 14
        for(int i = 1; i < 15; i++) {
            digits[i] = rand.nextInt(10);
        }

        //aplicamos la logica del algoritmo Luhn para poder calcular el ultimo digito
        int sum = 0;
        for(int i = 0; i < 15; i++) {
            int n = digits[i];
            //Posiciones pares se duplican
            if(i % 2 == 0){
                n *= 2;
                if(n > 9){
                    n -= 9;
                }
            }
            sum += n;
        }

        //calculo del ultimo digito utilizando la suma anterior
        digits[15] = (10 - sum % 10) % 10;

        //convertimos el array a String y devolvemos el numero de la tarjeta
        StringBuilder sb = new StringBuilder();
        for(int digit : digits){
            sb.append(digit);
        }
        return sb.toString();
    }

    /**
     * Genera el CVV de la tarjeta
     * @return String de 3 digitos entre 100 y 999
     */
    private static String generateCVV(){
        Random rand = new Random();
        return String.format("0%3d", rand.nextInt(1000));
    }

    //Getters y Setters

    public String getCardNumber(){
        return cardNumber;
    }
    public String getCVV(){
        return cvv;
    }
    public String getCardHolder(){
        return cardHolder;
    }

    /**
     * Metodo que envia a la cuenta bancaria asociada a la tarjeta para realizar un pago con la misma
     * @param amount Cantidad a pagar
     * @param item Objeto que el usuario ha decidido adquierir
     */
    public void cardPayment(double amount, ShopItem item, Card card){

        this.associatedAccount.cardPayment(amount, item, card);
    }

    public String getExpirationDate(){
        return expirationDate;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean getActive(){
        return this.active;
    }
}
