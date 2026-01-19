import Access.AccessScreen;

/**
 * Clase que desencade la función del programa en su totalidad
 */
public class Main {
    public static void main(String[] args){
        /**
         * @param accessScreen Variable que llama a otra clase y sus metodos
         */
        AccessScreen accessScreen = new AccessScreen();
        accessScreen.menu();
    }
}