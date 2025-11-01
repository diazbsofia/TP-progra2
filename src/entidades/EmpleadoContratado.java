package entidades;

public class EmpleadoContratado extends Empleado {
    //private double valorHora;

    public EmpleadoContratado(String nombre, Integer legajo, double valorHora) {
        super(nombre, legajo, valorHora);
    }

    @Override
    public double calcularPago(double dias) {
        return super.getValorHora() * dias * 8;
    }

    public double getValorHora() {
        return super.getValorHora();
    }

    @Override
    public String toString() {
        return super.toString() + " (Contratado - $" + super.getValorHora() + "/hora)";
    }
}