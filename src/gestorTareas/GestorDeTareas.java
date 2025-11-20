package gestorTareas;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GestorDeTareas {

    private final Map<Integer, Tarea> mapaTareas = new HashMap<>();
    private int ultimoId = 0;

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
                // int casiOpcion = Integer.parseInt(entrada);
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un número válido.");
            }
        }

    }

    public void motorTareas(int op, Scanner teclado) {
        switch (op) {

            case 1 -> agregarTarea1(teclado);
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
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            }
        }
    }

    public void agregarTarea1(Scanner t) {

        // ! Nombre
        String nombreNuevaTarea;
        while (true) {
            System.out.println("\nAgrega el nombre de la tarea:");
            nombreNuevaTarea = t.nextLine().trim();
            if (nombreNuevaTarea.length() > 1) {
                System.out.printf("Nombre de tarea \"%s\" confirmado.", nombreNuevaTarea);
                break;
            } else {
                System.out.println(
                        "Error: Debes ingresar un nombre de tarea válido, de mas de un caracter. \nPrueba de nuevo:");
            }
        }

        // ! Prioridad
        String prioridad;
        while (true) {
            System.out.println("\n¿Prioridad de la tarea? del 5 (mas urgente) al 1 (menos urgente):");
            prioridad = t.nextLine().trim();
            // t.nextLine(); // limpiar buffer1

            try {
                if (Integer.parseInt(prioridad) < 1 || Integer.parseInt(prioridad) > 5) {
                    System.out.println("Prioridad invalida. Debe ser un nivel entre 1 y 5.");
                } else {
                    System.out.println("Es una prioridad de tarea valida!");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error " + prioridad + " : Debes ingresar un número de prioridad válido.");
            }
        }

        // ! Id
        ultimoId++;
        int idt = ultimoId;

        // ! Armador y asignador de Id
        Tarea tareaNueva = new Tarea(nombreNuevaTarea, Integer.parseInt(prioridad));
        mapaTareas.put(idt, tareaNueva);

        tareaNueva.ejecutar(); // "Mensaje de Confirmacion"
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();
    }

    public void verTareas2() {

        if (mapaTareas.isEmpty()) {
            System.out.println("\nNo existen tareas cargadas.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
        }

        System.out.println("\n===Lista de tareas: ===");

        mapaTareas.forEach((id, tarea) -> {
            System.out.println("\nID: " + id + " - " + tarea);
        });

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

    }

    public void eliminarTarea3(Scanner t) {

        if (mapaTareas.isEmpty()) {
            System.out.println("\nNo existen tareas para eliminar.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }

        while (true) {
            System.out.print("\nLos ids de las tareas son: ");
            mapaTareas.forEach((id, tarea) -> {
                System.out.print(id + " • ");
            });
            // System.out.println();

            var longitudDelMapa = mapaTareas.size();
            System.out.printf("\n¿Qué tarea deseas eliminar? Existen %d tareas. Indica el id: ",
                    longitudDelMapa);

            //?
            int idElegido = validarSiIdEsNumero(t);
            t.nextLine(); // limpiar buffer

            if (!mapaTareas.containsKey(idElegido)) {
                System.out.println("Id inexistente. Intenta de nuevo.");
                continue;
            }

            while (true) {
                System.out.println("\nLa tarea a eliminar es:\n" + mapaTareas.get(idElegido)
                        + "\n¿Estás seguro? Responde 'si' o 'no'.");
                var respuesta = t.nextLine();

                if (respuesta.equalsIgnoreCase("si")) {
                    // listaDeTareas.remove(pos);
                    mapaTareas.remove(idElegido);
                    System.out.println("¡Tarea eliminada!\n");
                    break;
                } else if (respuesta.equalsIgnoreCase("no")) {
                    System.out.println("Ok, no se eliminó la tarea.\n");
                    break;
                } else {
                    System.out.println("\nNo ingresaste una respuesta valida; es si o no.");
                }
            }
            break;
        }
    }

    public void editarTarea4(Scanner t) {
        if (mapaTareas.isEmpty()) {
            System.out.println("\nNo existen tareas para editar.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            return;
        }
        while (true) {
            System.out.print("\nLos ids de las tareas son: ");
            mapaTareas.forEach((id, tarea) -> {
                System.out.print(id + " • ");
            });
            System.out.print("\nIngrese el ID de la tarea a editar (0 para cancelar): ");

            int idElegido = validarSiIdEsNumero(t);
                t.nextLine(); // limpiar buffer

            // Salir si el usuario ingresa 0
            if (idElegido == 0) {
                System.out.println("Operación cancelada.");
                return;
            }

            // Verificar si el ID existe
            if (!mapaTareas.containsKey(idElegido)) {
                System.out.println("Error: No existe una tarea con el ID " + idElegido);
                continue;
            }

            Tarea tareaAEditar = mapaTareas.get(idElegido);
            boolean seguirEditando = true;

            // Bucle de confirmación de edición
            while (seguirEditando) {
                System.out.println("\nTarea seleccionada:");
                System.out.println(tareaAEditar);
                System.out.print("¿Desea editar esta tarea? (si/no): ");
                String respuesta = t.nextLine().trim().toLowerCase();
//?
                if (respuesta.equals("no")) {
                    System.out.println("Operación cancelada.");
                    seguirEditando = false;
                    continue;
                } else if (!respuesta.equals("si")) {
                    System.out.println("Respuesta no válida. Por favor responda 'si' o 'no'.");
                    continue;
                }

                // Menú de edición
                boolean enMenuEdicion = true;
                while (enMenuEdicion) {
                    System.out.println("\n¿Qué desea editar?");
                    System.out.println("1. Nombre de la tarea");
                    System.out.println("2. Prioridad");
                    System.out.println("0. Volver al menú principal");
                    //?

                    if (!t.hasNextInt()) {
                        System.out.println("\nError: Debe ingresar un número.");
                        t.nextLine(); // Limpiar buffer
                        continue;
                    }

                    int opcion = t.nextInt();
                    t.nextLine(); // Limpiar buffer

                    switch (opcion) {
                        case 0 -> {
                            System.out.println("\nVolviendo al menú principal...");
                            enMenuEdicion = false;
                            seguirEditando = false;
                        }
                        case 1 -> {
                            System.out.print("\nNuevo nombre: ");
                            String nuevoNombre = t.nextLine().trim();
                            if (!nuevoNombre.isEmpty()) {
                                tareaAEditar.setNombre(nuevoNombre);
                                System.out.println("¡Nombre actualizado correctamente!");
                            } else {
                                System.out.println("El nombre no puede estar vacío.");
                            }
                        }
                        case 2 -> {
                            System.out.print("\nNueva prioridad (1-5): ");
                            if (t.hasNextInt()) {
                                int nuevaPrioridad = t.nextInt();
                                t.nextLine(); // Limpiar buffer
                                if (nuevaPrioridad >= 1 && nuevaPrioridad <= 5) {
                                    tareaAEditar.setPrioridad(nuevaPrioridad);
                                    System.out.println("¡Prioridad actualizada correctamente!");
                                } else {
                                    System.out.println("La prioridad debe estar entre 1 y 5.");
                                }
                            } else {
                                System.out.println("Debe ingresar un número.");
                                t.nextLine(); // Limpiar buffer
                            }
                        }
                        default -> System.out.println("Opción no válida. Intente de nuevo.");
                    }
                }

                // Preguntar si desea editar otra tarea
                System.out.print("\n¿Desea editar otra tarea? (si/no): ");
                String continuar = t.nextLine().trim().toLowerCase();
                if (continuar.equals("no")) {
                    System.out.println("Volviendo al menú principal...");
                    return;
                } else if (!continuar.equals("si")) {
                    System.out.println("Respuesta no válida. Volviendo al menú principal...");
                    return;
                }

                // Mostrar la lista de tareas nuevamente
                verTareas2();
                seguirEditando = true;
            }
        }
    }

    public void limpiarLista5(Scanner t) {

        if (mapaTareas.isEmpty()) {
            System.out.println("No existen tareas cargadas.");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

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
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }

    private static int validarSiIdEsNumero(Scanner t) {
        while (true) {
            if (!t.hasNextInt()) {
                System.out.println("Error: Debe ingresar un número.");
                t.nextLine(); // Limpiar buffer
            } else {
                int id = t.nextInt();
                return id;
            }
        }
    }

    // ? Cierre de toda la public class
}
