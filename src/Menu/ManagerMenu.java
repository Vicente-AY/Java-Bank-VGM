package Menu;

import Person.Person;

/**
 * Clase encargada de gestionar la interfaz de usuario y las operaciones
 * exclusivas para los usuarios con rol de Gerente (Manager).
 */
public class ManagerMenu {

    /**
     * Proporciona acceso al menú administrativo del gerente.
     * Permite la gestión de cuentas bancarias
     * con privilegios elevados.
     * @param currentManager El Gerente que ha iniciado sesión.
     */
    public void menuAccess(Person currentManager){

        int option = 0;
        System.out.println("Welcome " + currentManager.name);
        System.out.println("1. Create BankAccount");
        System.out.println("2. Make a deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Money");
        System.out.println("5. Recharge SIM card");
        System.out.println("6. Log Out");
        System.out.println("Please enter your numbered choice (1, 2, 3, 4, 5 or 6)");
        while (option != 6) {
            switch (option) {
                case 1:
                    //bankAccount  newBA = new bankAccount(dummyBankAccount.getEntity(), dummyBankAccount.getOffice(),  dummyBankAccount.calcDC(), null, null, null);
                    break;
                case 2:

                    break;
                case 3:
                    return;
                case 4:
                    return;
                case 5:
                    return;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;

            }
        }
    }
}
