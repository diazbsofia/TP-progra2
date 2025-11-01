package entidades;

public class EmpleadoPlanta extends Empleado {
    private String categoria;
    
    public static final String CATEGORIA_INICIAL = "INICIAL"; // está hecho asi porque como es final no se puede modificar, entonces evita errores
    public static final String CATEGORIA_TECNICO = "TECNICO";
    public static final String CATEGORIA_EXPERTO = "EXPERTO";
    

    public EmpleadoPlanta(String nombre, Integer legajo, double valorDia) {
        super(nombre, legajo, valorDia);
        this.categoria = CATEGORIA_INICIAL;
    }
 
    public EmpleadoPlanta(String nombre, Integer legajo, double valorDia, String categoria) {
        super(nombre, legajo, valorDia);
        
        this.categoria = categoria;
    }
    
   
    public double calcularPago(double dias) {
        double pagoBase = super.getValorHora() * dias;
        
        // si no tiene ningun retraso cobra el adicional del 2%
        if (obtenerCantidadRetrasos() == 0) {
            pagoBase += calcularAdicional(pagoBase);
        }
        
        return pagoBase;
    }
    

    private double calcularAdicional(double pagoBase) { //lo hice directamente en un metodo q calcule por lo que piden los profes
        return pagoBase * 0.02;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    
    public double getValorDia() {
        return super.getValorHora();
    }
    
    @Override
    public String toString() {
        return super.toString() + " (Planta " + categoria + " - $" + getValorDia() + "/día)";
    }
}