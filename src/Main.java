import Access.AccessScreen;

/**
 * Clase principal que actúa como el punto de entrada.
 * Se encarga de arrancar la aplicación a la pantalla de acceso inicial.
 */
public class Main {

    /**
     * Método principal que inicia la ejecución del programa.
     * @param args Argumentos de la línea de comandos (no utilizados en este sistema).
     */
    public static void main(String[] args) {
        AccessScreen accessScreen = new AccessScreen();
        accessScreen.menu();
    }
}