package gestorTareas;

import java.util.Scanner;

public class app {
//TODO: Crear una excepcion personalizada para probar

    public static void main(String[] args) {

        GestorDeTareas gestor = new GestorDeTareas();

        try (Scanner scTeclado = new Scanner(System.in)) {
            int opcionElegida;

            do {
                gestor.mostrarMenu();
                opcionElegida = gestor.leerOpcion(scTeclado);
//                scTeclado.nextLine(); // limpiar buffer
                gestor.motorTareas(opcionElegida, scTeclado);
            } while (opcionElegida != 6);
        }
    }

}