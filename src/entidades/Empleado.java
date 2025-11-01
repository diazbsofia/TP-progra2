package entidades;

public abstract class Empleado {
    private String nombre;
    private Integer legajo;
    private boolean ocupado;
    private double valorHora;
    private int cantidadRetrasos;
    
    
	public Empleado(String nombre, Integer legajo, double valorHora) {
	        
	        if (nombre == null || nombre.isEmpty()) {
	            throw new IllegalArgumentException("El nombre no puede estar vacío");
	        }
	        
	        if (legajo == null || legajo <= 0) {
	            throw new IllegalArgumentException("El legajo tiene que ser mayor a 0");
	        }
	        
	        if (valorHora <= 0) {
	            throw new IllegalArgumentException("El valor por hora tiene que ser mayor a 0");
	        }
	        
	        this.nombre = nombre;
	        this.legajo = legajo;
	        this.ocupado = false;
	        this.valorHora = valorHora;
	        this.cantidadRetrasos = 0;
	    }

    public void asignar() {
        this.ocupado = true;
    }

    public void liberar() {
        this.ocupado = false;
    }

    public boolean estaLibre() {
        return !ocupado;
    }

    public void registrarRetraso() {
        this.cantidadRetrasos++;
    }

    public int obtenerCantidadRetrasos() {
        return cantidadRetrasos;
    }

    public Integer getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isOcupado() {
        return ocupado;
    }
    
    public double getValorHora() {
        return valorHora;
    }

    public abstract double calcularPago(double diasTrabajados);

    @Override
    public String toString() {
        return legajo + " - " + nombre;
    }
}
