package entidades;

public class Fecha {

    private int dia;
    private int mes;
    private int anio;

 
    private static final int[] DIAS_POR_MES = 
        { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };


    public Fecha(int dia, int mes, int anio) {
        if (!esFechaValida(dia, mes, anio)) {
            throw new IllegalArgumentException("Fecha inválida: " + dia + "/" + mes + "/" + anio);
        }
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }



    private boolean esFechaValida(int d, int m, int a) {
        if (a < 1 || m < 1 || m > 12 || d < 1) return false;
        int diasMes = diasEnMes(m, a);
        return d <= diasMes;
    }

    private int diasEnMes(int m, int a) {
        if (m == 2 && esBisiesto(a)) return 29;
        return DIAS_POR_MES[m - 1];
    }

    private boolean esBisiesto(int a) {
        return (a % 4 == 0 && a % 100 != 0) || (a % 400 == 0);
    }



    public Fecha sumarDias(int cantidad) {
        if (cantidad < 0) return restarDias(-cantidad);

        int nuevoDia = dia;
        int nuevoMes = mes;
        int nuevoAnio = anio;

        while (cantidad > 0) {
            int diasMes = diasEnMes(nuevoMes, nuevoAnio);
            if (nuevoDia + cantidad <= diasMes) {
                nuevoDia += cantidad;
                cantidad = 0;
            } else {
                cantidad -= (diasMes - nuevoDia + 1);
                nuevoDia = 1;
                nuevoMes++;
                if (nuevoMes > 12) {
                    nuevoMes = 1;
                    nuevoAnio++;
                }
            }
        }

        return new Fecha(nuevoDia, nuevoMes, nuevoAnio);
    }

    public Fecha restarDias(int cantidad) {
        if (cantidad < 0) return sumarDias(-cantidad);

        int nuevoDia = dia;
        int nuevoMes = mes;
        int nuevoAnio = anio;

        while (cantidad > 0) {
            if (nuevoDia > cantidad) {
                nuevoDia -= cantidad;
                cantidad = 0;
            } else {
                cantidad -= nuevoDia;
                nuevoMes--;
                if (nuevoMes < 1) {
                    nuevoMes = 12;
                    nuevoAnio--;
                }
                nuevoDia = diasEnMes(nuevoMes, nuevoAnio);
            }
        }

        return new Fecha(nuevoDia, nuevoMes, nuevoAnio);
    }



    public int comparar(Fecha otra) {
        if (this.anio != otra.anio) return this.anio < otra.anio ? -1 : 1;
        if (this.mes != otra.mes) return this.mes < otra.mes ? -1 : 1;
        if (this.dia != otra.dia) return this.dia < otra.dia ? -1 : 1;
        return 0;
    }

    public boolean esIgual(Fecha otra) {
        return comparar(otra) == 0;
    }

    public boolean esAnterior(Fecha otra) {
        return comparar(otra) == -1;
    }

    public boolean esPosterior(Fecha otra) {
        return comparar(otra) == 1;
    }



    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, anio);
    }



    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAnio() {
        return anio;
    }
}