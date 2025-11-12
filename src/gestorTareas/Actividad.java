package gestorTareas;

public abstract class Actividad {
    private String nombre;

    // !Constructor abstracto
    public Actividad(String nombre) {
        this.nombre = nombre;
    }

    // ? Metodo Get abstracto
    public String getNombre() {
        return nombre;
    }

    // ? Setter de nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // * Metodo abstracto
    public abstract void ejecutar();
}