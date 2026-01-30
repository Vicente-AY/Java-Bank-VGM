package Account;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase que representa el historico de movimientos de una cuenta bancaria.
 */
public class BankAccountHistory implements Serializable {

    double previousBalance;
    String operationType;
    double transactionAmount;
    double newBalance;
    String transactionDate;
    BankAccount destinationAccount;

    /**
     * Constructor de la clase con los datos para crear un movimiento bancario.
     * @param operationType Tipo de operación bancaria realizada (ingresar, retirar...).
     * @param transactionAmount Cantidad involucrada en el movimiento.
     * @param transactionDate Fecha de realización del movimiento bancario.
     */
    public BankAccountHistory(double previousBalance, String operationType, double transactionAmount, double newBalance, String transactionDate) {
        this.previousBalance = previousBalance;
        this.operationType = operationType;
        this.transactionAmount = transactionAmount;
        this.newBalance = newBalance;
        this.transactionDate = transactionDate;
    }

    /**
     * Constructor sobrecargado para cuando opere con junto a otra cuenta bancaria.
     * @param operationType Tipo de operación bancaria, en en el caso de uso de este constructor especifico solo sera transferencia.
     * @param transactionAmount Cantidad involucrada en la transferencia.
     * @param transactionDate Fecha de realización de la transferencia.
     * @param destinationAccount Cuenta bancaria a la que se dirige el movimiento.
     */
    public BankAccountHistory(double previousBalance, String operationType, double transactionAmount, double newBalance, String transactionDate, BankAccount destinationAccount){
        this.previousBalance = previousBalance;
        this.operationType = operationType;
        this.newBalance = newBalance;
        this.transactionDate = transactionDate;
        this.destinationAccount = destinationAccount;
    }

    //getters y setters

    public  double getPreviousBalance() {
        return previousBalance;
    }
    public double getTransactionAmount() {
        return transactionAmount;
    }
    public String getOperationType() {
        return operationType;
    }
    public double getNewBalance() {
        return newBalance;
    }
    public String getTransactionDate() {
        return transactionDate;
    }
    public BankAccount getDestinationAccount() {
        return destinationAccount;
    }
}
