package entidades;


public class EmpleadoPlanta extends Empleado {
    private double valorDia;
    private String categoria;

    public EmpleadoPlanta(String nombre, int legajo, double valorDia, String categoria) {
        super(nombre, legajo);
        if (valorDia <= 0)
            throw new IllegalArgumentException("Valor día inválido.");
        if (!categoria.equals("INICIAL") && !categoria.equals("TÉCNICO") && !categoria.equals("EXPERTO"))
            throw new IllegalArgumentException("Categoría inválida.");
        this.valorDia = valorDia;
        this.categoria = categoria;
    }

    @Override
    public double calcularPago(double diasTrabajados) {
        double pago = valorDia * diasTrabajados;
       
        if (cantidadRetrasos == 0) {
        	pago *= 1.02;
        }
        return pago;
    }

    public double getValorDia() {
        return valorDia;
    }

    public void setValorDia(double valorDia) {
        if (valorDia <= 0)
            throw new IllegalArgumentException("Valor día inválido.");
        this.valorDia = valorDia;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return super.toString() + " - Planta (" + categoria + ", $" + valorDia + "/día)";
    }


}