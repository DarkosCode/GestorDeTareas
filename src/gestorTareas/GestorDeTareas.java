package gestorTareas;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class GestorDeTareas {

    // Atributo de instancia (NO static) y usando la interfaz List
    private final Map<Integer, Tarea> mapaTareas = new HashMap<>();
    // * Private para mantener el "Encapsulamiento"

    public void mostrarMenu() {
        // ? Menu
        System.out.println("~~~ GESTOR DE TAREAS 2.0 ~~~");
        System.out.println("1 - Agregar nueva tarea");
        System.out.println("2 - Ver tareas pendientes");
        System.out.println("3 - Eliminar una tarea");
        System.out.println("4 - Editar una tarea");
        System.out.println("5 - Limpiar toda la lista de tareas");

        System.out.println("6 - Salir");
    }

    public int leerOpcion(Scanner s) {
        while (true) {
        System.out.println("\nElige una opcion a continuacion: ");
            var entrada = s.nextLine().trim();
            try {
//                int casiOpcion = Integer.parseInt(entrada);
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un número válido.");
            }
        }

    }

    public void motorTareas(int op, Scanner teclado) {
        switch (op) {

            case 1 -> agregarTarea1(teclado);
            // * El break está implícito en la propia flecha. Es imposible que "caiga" al siguiente case.
            case 2 -> verTareas2();
            case 3 -> eliminarTarea3(teclado);
            case 4 -> editarTarea4(teclado);
            case 5 -> limpiarLista5(teclado);
            case 6 -> salir6();
            default -> {
                System.out.println("No existe esa opcion de tarea!, vuelve a intentarlo.");
                System.out.println();
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }
        }
    }

    public void agregarTarea1(Scanner t) {


        // ! Nombre
        String nombreNuevaTarea;
        while(true) {
            System.out.println("\nAgrega el nombre de la tarea:");
            nombreNuevaTarea = t.nextLine().trim();
            if(nombreNuevaTarea.length() > 1) {
                System.out.printf("\nNombre de tarea \"%s\" confirmado.", nombreNuevaTarea);
                break;
            } else {System.out.println("Error: Debes ingresar un nombre de tarea válido, de mas de un caracter. \nPrueba de nuevo:");}
        }

        // ! Prioridad
        String prioridad;
        while (true) {
            System.out.println("\n\n¿Prioridad de la tarea? del 5 (mas urgente) al 1 (menos urgente)");
            prioridad = t.nextLine().trim();
//             t.nextLine(); // limpiar buffer1

            try {
                if (Integer.parseInt(prioridad) < 1 || Integer.parseInt(prioridad) > 5) {
                    System.out.println("Prioridad invalida. Debe ser un nivel entre 1 y 5.");
                } else {
                    System.out.println("\nEs una prioridad de tarea valida!");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error " + prioridad + " : Debes ingresar un número de prioridad válido.");
            }
        }

        // ! Id
        int idt;

        if(mapaTareas.isEmpty()) {
            idt = 1;
        } else {
            int idMaximo = mapaTareas.keySet().stream()
                .max(Integer::compare)
                .get();
             idt = idMaximo + 1;}

        //! Armador y asignador de Id
        Tarea tareaNueva = new Tarea(nombreNuevaTarea, Integer.parseInt(prioridad));
        mapaTareas.put(idt, tareaNueva);

        tareaNueva.ejecutar(); // "Mensaje de Confirmacion"
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    public void verTareas2() {

        if (mapaTareas.isEmpty()) {
            System.out.println("\nNo existen tareas cargadas.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }

        System.out.println("\n===Lista de tareas: ===");

            mapaTareas.forEach((id, tarea) -> {
                System.out.println("\nID: " + id + " - " + tarea);
            });

            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

    }

    public void eliminarTarea3(Scanner t) {

        if (mapaTareas.isEmpty()) {
            System.out.println("\nNo existen tareas para eliminar.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
        }

        while (true) {
            System.out.print("\nLos ids de las tareas son: ");
            mapaTareas.forEach((id, tarea) -> {
                System.out.print(id + " • ");
            });
//            System.out.println();

            var longitudDelMapa = mapaTareas.size();
            System.out.printf("\n¿Qué tarea deseas eliminar? Existen %d tareas. Indica el id:%n",
                    longitudDelMapa);

            // validar que venga un int:
            if (!t.hasNextInt()) {

                System.out.println("Por favor ingresa un número válido.");
                t.nextLine(); // limpiar lo que haya

                continue;
            }

            var idElegido = t.nextInt();
            t.nextLine(); // limpiar buffer

            if (!mapaTareas.containsKey(idElegido)) {
                System.out.println("Id inexistente. Intenta de nuevo.");
                continue;
            }

            while (true) {
                System.out.println("\nLa tarea a eliminar es:\n\n" + mapaTareas.get(idElegido)
                        + "\n\n¿Estás seguro? Responde 'si' o 'no'.");
                var respuesta = t.nextLine();

                if (respuesta.equalsIgnoreCase("si")) {
                    // listaDeTareas.remove(pos);
                    mapaTareas.remove(idElegido);
                    System.out.println("¡Tarea eliminada!\n");
                    break;
                } else if (respuesta.equalsIgnoreCase("no")) {
                    System.out.println("\nOk, no se eliminó la tarea.");
                    break;
                } else {
                    System.out.println("\nNo ingresaste una respuesta valida; es si o no.");
                }
//                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }
            break;
        }
    }

    public void editarTarea4(Scanner t) {

        if (mapaTareas.isEmpty()) {
            System.out.println("\nNo existen tareas para editar.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
        }

        while (true) {

            System.out.print("\nLos ids de las tareas son: ");
            mapaTareas.forEach((id, tarea) -> {
                System.out.print(id + " • ");
            });

            var longitudDelMapa = mapaTareas.size();
            System.out.printf("\n¿Qué tarea deseas editar? Existen %d tareas. Indica el id:%n",
                    longitudDelMapa);

            if (!t.hasNextInt()) {
                System.out.println("Por favor ingresa un número válido.");
                t.nextLine(); // limpiar lo que haya
                continue;
            }

            var idElegido = t.nextInt();
            t.nextLine(); // limpiar buffer

            if (!mapaTareas.containsKey(idElegido)) {
                System.out.println("Id inexistente. Intenta de nuevo.");
                continue;
            }

            System.out.println(
                    "\nLa tarea a editar es: " + mapaTareas.get(idElegido) + ". ¿Estás seguro? Responde 'si' o 'no'.");
            String respuesta = t.nextLine();

            if (respuesta.equalsIgnoreCase("si")) {
                // Obtener la tarea del Map usando el ID
                Tarea tareaAEditar = mapaTareas.get(idElegido);

                while (true) {
                    // ! Editar nombre o prioridad
                    System.out.println("Escoge 0 si quieres editar el nombre, 1 si quieres cambiar la prioridad: ");
                    int eleccion = t.nextInt();
                    t.nextLine(); // limpiar buffer

                    // Validacion de eleccion entre nombre y prioridad
                    if (eleccion != 0 && eleccion != 1) {
                        System.out.println("Eleccion incorrecta, intenta de nuevo.");
                        continue;
                    }

                    if (eleccion == 0) {
                        System.out.println("Introduce el nuevo nombre de la tarea: ");
                        var nuevoNombre = t.nextLine();
                        tareaAEditar.setNombre(nuevoNombre);

                        System.out.println("¡Tarea editada! Se cambio el nombre a \"" + nuevoNombre + "\"\n");
                        break;
                    } else {
                        System.out.println("Introduce la nueva prioridad (1-5) de la tarea: ");
                        var nuevaPrioridad = t.nextInt();
                        tareaAEditar.setPrioridad(nuevaPrioridad);
                        System.out.println("¡Tarea editada! Se cambio la prioridad a \"" + nuevaPrioridad + "\"\n");
                        break;
                    }
                }
            } else {
                System.out.println("Ok, no se editó la tarea.");
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            break;
        }

    }

    public void limpiarLista5(Scanner t) {

        if (mapaTareas.isEmpty()) {
            System.out.println("No existen tareas cargadas.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

            return;
        }

        System.out.println("Estas seguro que quieres limpiar toda la lista? Responda 'si' o 'no'.\n Contiene "
                + mapaTareas.size() + " tareas.");
        var respuesta = t.nextLine();

        if (respuesta.equalsIgnoreCase("si")) {
            mapaTareas.clear();
            System.out.println("La lista de tareas esta limpia! Quedaron " + mapaTareas.size() + " tareas dentro.");

        } else {
            System.out.println("Ok, no se borraron las tareas.");
        }

    }

    public void salir6() {
        System.out.println("Gracias por usar el gestor de tareas 2.0, vuelva prontos!");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }
}
