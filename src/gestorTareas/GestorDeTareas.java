package gestorTareas;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//TODO: Implementar que los id de las tareas se pongan solos (del 1 en adelante).
// Si se elimina una tarea igual no deberian modificarse los ID de la tarea por que podria generar problemas

public class GestorDeTareas {

    // Atributo de instancia (NO static) y usando la interfaz List
    private Map<Integer, Tarea> mapaTareas = new HashMap<>();
    // * Private para mantener el "Encapsulamiento"

    public void mostrarMenu() {
        // ? Menu
        System.out.println("\n~~~ GESTOR DE TAREAS 2.0 ~~~");
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
                int casiOpcion = Integer.parseInt(entrada);
                return casiOpcion;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un número válido.");
            }
        }

    }

    public void motorTareas(int op, Scanner teclado) {
        switch (op) {

            // case 1:
            // agregarTarea1(teclado);
            // break;
            // ? ↖ asi los tenia antes

            case 1 -> agregarTarea1(teclado); // * FORMA MAS MODERNA DE ESCRIBIR SWITCH
            // * El break está implícito en la propia flecha. Es imposible que "caiga" al
            // siguiente case.
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
        int idt;

        // ! id
        while (true) {
            System.out.println("Agregar ID de la tarea:");
            var entrada = t.nextLine().trim();
            //trim "recorta" los espacios de los costados

            try {
                var idNumero = Integer.parseInt(entrada);
                if (mapaTareas.containsKey(idNumero)) {
                    System.out.println("Ya existe una tarea con ese ID, prueba de nuevo");
                } else {
                    idt = idNumero;
                    System.out.println("ID de tarea confirmado.");
                    break; // Sale del while
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un número válido.");
            }
        }

        // ! Nombre
        System.out.println("Agrega el nombre de la tarea:");
        String nombreNuevaTarea = t.nextLine().trim();

        // ! Prioridad
        while (true) {
            System.out.println("¿Prioridad de la tarea? (1 a 5)");
            var prioridad = t.nextLine().trim();
            // t.nextLine(); // limpiar buffer1

            try {
                if (Integer.parseInt(prioridad) < 1 || Integer.parseInt(prioridad) > 5) {
                    System.out.println("Prioridad invalida. Debe ser un nivel entre 1 y 5.");
                } else {
                    System.out.println("Es una prioridad de tarea valida!");
                    Tarea tareaNueva = new Tarea(nombreNuevaTarea, Integer.parseInt(prioridad));
                    mapaTareas.put(idt, tareaNueva);

                    tareaNueva.ejecutar(); // "Mensaje de Confirmacion"
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println();
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error " + prioridad + " : Debes ingresar un número de prioridad válido.");
            }
        }
    }

    public void verTareas2() {

        if (mapaTareas.isEmpty()) {
            System.out.println("No existen tareas cargadas.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
            // ? Ese return se llama "Clausula de Guardia" y hace terminar la ejecución del
            // método de inmediato
            // ? (verTareas2) y devolver el control a la línea de código que llamó a
            // verTareas2().

        } else {
            System.out.println("===Lista de tareas: ===");

            //TODO: lambda + forEach
            mapaTareas.forEach((id, tarea) -> {
                System.out.println("ID: " + id + " - " + tarea);
            });

            // * Listando el mapa de tareas
//            for (Map.Entry<Integer, Tarea> tarea : mapaTareas.entrySet()) {
//                System.out.println("ID: " + tarea.getKey() + " - " + tarea.getValue());
//            }

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
    }

    public void eliminarTarea3(Scanner t) {

        if (mapaTareas.isEmpty()) {
            System.out.println("No existen tareas para eliminar.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
        }

        while (true) {

            var longitudDelMapa = mapaTareas.size();
            System.out.printf("¿Qué tarea deseas eliminar? Existen %d tareas. Indica el id:%n",
                    longitudDelMapa);

            // validar que venga un int:
            if (!t.hasNextInt()) {
                // * El hasNextInt no lee el valor si no que solo valida que sea de tipo entero
                // (int), y devuelve True or False

                System.out.println("Por favor ingresa un número válido.");
                t.nextLine(); // limpiar lo que haya

                continue;
                // * El continue dice "Ignora el código restante en esta iteración y salta al
                // comienzo de la próxima iteración del bucle (while (true)"
            }

            var idElegido = t.nextInt();
            t.nextLine(); // limpiar buffer

            if (!mapaTareas.containsKey(idElegido)) {
                System.out.println("Id inexistente. Intenta de nuevo.");
                continue;
            }

            System.out.println("La tarea a eliminar es:\n" + mapaTareas.get(idElegido)
                    + " \n¿Estás seguro? Responde 'si' o 'no'.");
            var respuesta = t.nextLine();

            if (respuesta.equalsIgnoreCase("si")) {
                // listaDeTareas.remove(pos);
                mapaTareas.remove(idElegido);
                System.out.println("¡Tarea eliminada!\n");
                break;
            } else {
                System.out.println("Ok, no se eliminó la tarea.");
            }

            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        }
    }

    public void editarTarea4(Scanner t) {

        if (mapaTareas.isEmpty()) {
            System.out.println("No existen tareas para editar.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
        }

        while (true) {
            var longitudDelMapa = mapaTareas.size();
            System.out.printf("¿Qué tarea deseas editar? Existen %d tareas. Indica el id:%n",
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
                    "La tarea a editar es: " + mapaTareas.get(idElegido) + ". ¿Estás seguro? Responde 'si' o 'no'.");
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
