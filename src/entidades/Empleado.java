package entidades;

/**
 * Clase abstracta Empleado
 *
 * Representa un empleado genérico del sistema.
 *
 * IREP:
 * - legajo > 0
 * - nombre no nulo ni vacío
 * - cantidadRetrasos >= 0
 * - Si ocupado = true, está asignado a una tarea activa
 * - Si ocupado = false, no está asignado a ninguna tarea
 */
public abstract class Empleado {
    protected String nombre;
    protected int legajo;
    protected boolean ocupado;
    protected int cantidadRetrasos;

    public Empleado(String nombre, int legajo) {
        if (nombre == null || nombre.isEmpty() || legajo <= 0)
            throw new IllegalArgumentException("Datos de empleado inválidos.");
        this.nombre = nombre;
        this.legajo = legajo;
        this.ocupado = false;
        this.cantidadRetrasos = 0;
    }

    public void asignar() {
        ocupado = true;
    }

    public void liberar() {
        ocupado = false;
    }

    public boolean estaLibre() {
        return !ocupado;
    }

    public void registrarRetraso() {
        cantidadRetrasos++;
    }

    public int getCantidadRetrasos() {
        return cantidadRetrasos;
    }

    public int getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean estaOcupado() {
        return ocupado;
    }

    // Método abstracto: se sobreescribe en subclases
    public abstract double calcularPago(int diasTrabajados);

    @Override
    public String toString() {
        return legajo + " - " + nombre + " (Retrasos: " + cantidadRetrasos + ")";
    }
}
