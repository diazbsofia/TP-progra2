package entidades;

public class Tarea {
    private String titulo;
    private String descripcion;
    private double diasNecesarios;
    private double diasRetraso;
    private Empleado empleado;
    private boolean finalizada;

    public Tarea(String titulo, String descripcion, double diasNecesarios) {
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (diasNecesarios <= 0) {
            throw new IllegalArgumentException("Los días necesarios tienen que ser mayores a 0");
        }
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.diasNecesarios = diasNecesarios;
        this.diasRetraso = 0;
        this.empleado = null;
        this.finalizada = false;
    }

    public void asignarEmpleado(Empleado empleado) {
        if (this.empleado != null) {
            throw new IllegalArgumentException("La tarea ya tiene un empleado asignado");
        }
        if (!empleado.estaLibre()) {
            throw new IllegalArgumentException("El empleado no está disponible");
        }
        this.empleado = empleado;
        empleado.asignar();
    }

    public void reasignarEmpleado(Empleado nuevoEmpleado) {
        if (this.empleado == null) {
            throw new IllegalArgumentException("No existe un empleado asignado");
        }
        if (!nuevoEmpleado.estaLibre()) {
            throw new IllegalArgumentException("El empleado no está disponible");
        }
        this.empleado.liberar();
        this.empleado = nuevoEmpleado;
        nuevoEmpleado.asignar();
    }

    public void registrarRetraso(double dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de retraso tienen que ser mayores a 0");
        }
        this.diasRetraso += dias;
        if (this.empleado != null) {
            this.empleado.registrarRetraso();
        }
    }

    public void finalizarTarea() {
        if (finalizada) {
            throw new IllegalArgumentException("La tarea ya está finalizada");
        }
        this.finalizada = true;
        if (this.empleado != null) {
            this.empleado.liberar();
        }
    }

    public double obtenerDuracion() {
        return diasNecesarios + diasRetraso;
    }

    public boolean estaAsignada() {
        return empleado != null;
    }

    public boolean estaFinalizada() {
        return finalizada;
    }

    public Empleado obtenerEmpleado() {
        return empleado;
    }

    public double obtenerDiasRetraso() {
        return diasRetraso;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getDiasNecesarios() {
        return diasNecesarios;
    }

    @Override
    public String toString() {
        return titulo;
    }
}