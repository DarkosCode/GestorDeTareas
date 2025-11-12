package gestorTareas;

public class Tarea extends Actividad {
    private int nivelDePrioridad;

    // !Constructor de Tarea
    public Tarea(String nombre, int prioridad) {
        super(nombre);
        this.nivelDePrioridad = prioridad;
    }

    // ? Metodos Get propios de Tarea

    // getNombre() ya esta en .Actividad

    public int getPrioridad() {
        return nivelDePrioridad;
    }

    public void getTarea() {
        System.out.println("Tarea: \"" + getNombre() + "\". Prioridad nivel: " + getPrioridad());
    }

    // ** Setters

    public void setPrioridad(int nivelDePrioridad) {
        if (nivelDePrioridad >= 1 && nivelDePrioridad <= 5) {
            this.nivelDePrioridad = nivelDePrioridad;
        } else {
            System.out.println("Prioridad invalida. Debe ser un nivel entre 1 y 5.");
        }
    }

    /// Metodo implementado del abstracto en Actividad.java
    @Override
    public void ejecutar() {
        System.out.println("La opcion fue ejecutada con exito.");
    }

    @Override
    public String toString() {
        return ("Tarea: " + getNombre() + "\n Nivel de prioridad: " + getPrioridad());
    }

}