package entidades;

/**
 * Representa un empleado contratado por hora.
 *
 * IREP:
 * - valorHora > 0
 * - cantidadRetrasos >= 0
 */
public class EmpleadoContratado extends Empleado {
    private double valorHora;

    public EmpleadoContratado(String nombre, int legajo, double valorHora) {
        super(nombre, legajo);
        if (valorHora <= 0)
            throw new IllegalArgumentException("Valor hora inválido.");
        this.valorHora = valorHora;
    }

    @Override
    public double calcularPago(int diasTrabajados) {
        return valorHora * 8 * diasTrabajados;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        if (valorHora <= 0)
            throw new IllegalArgumentException("Valor hora inválido.");
        this.valorHora = valorHora;
    }

    @Override
    public String toString() {
        return super.toString() + " - Contratado ($" + valorHora + "/hora)";
    }
}