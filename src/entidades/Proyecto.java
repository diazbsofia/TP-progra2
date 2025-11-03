


package entidades;

import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    private Integer numero;
    private Cliente cliente;
    private String direccion;
    private List<Tarea> listaDeTareas;
    private String fechaInicio;
    private String fechaEstimada;
    private String fechaReal;
    private String estado;
    private double costoFinal;
    private List<Empleado> historialEmpleados;

    public Proyecto(Integer numero, String[] datosCliente, String direccion, 
    		List<Tarea> tareas, String fechaInicio, String fechaEstimada) {
        if (numero == null || numero <= 0) {
            throw new IllegalArgumentException("El número de proyecto es inválido");
        }
        if (tareas == null || tareas.isEmpty()) {
            throw new IllegalArgumentException("Tiene que haber al menos una tarea");
        }
        
        this.numero = numero;
        if (datosCliente != null && datosCliente.length >= 3) { 
            this.cliente = new Cliente(datosCliente[0], datosCliente[1], datosCliente[2]);
        }
        this.direccion = direccion;
        this.fechaInicio = fechaInicio;
        this.fechaEstimada = fechaEstimada;
        this.fechaReal = fechaEstimada;
        this.estado = Estado.pendiente;
        this.costoFinal = 0;
        this.listaDeTareas = new ArrayList<>(tareas);
        this.historialEmpleados = new ArrayList<>();
    }

    public void asignarEmpleadoTarea(String tituloTarea, Empleado empleado) throws Exception {
        if (estado.equals(Estado.finalizado)) {
            throw new Exception("No se puede asignar empleados a un proyecto finalizado");
        }
        
        Tarea tarea = buscarTarea(tituloTarea);
        if (tarea == null) {
            throw new Exception("La tarea no existe");
        }
        
        tarea.asignarEmpleado(empleado);
        
        if (!historialEmpleados.contains(empleado)) {
            historialEmpleados.add(empleado);
        }
        
        if (estado.equals(Estado.pendiente)) {
            activar();
        }
        
        actualizarCosto();
    }

    public void reasignarEmpleado(String tituloTarea, Empleado nuevoEmpleado) throws Exception {
        if (estado.equals(Estado.finalizado)) {
            throw new Exception("No se puede reasignar empleados en un proyecto finalizado");
        }
        
        Tarea tarea = buscarTarea(tituloTarea);
        if (tarea == null) {
            throw new Exception("La tarea no existe");
        }
        
        tarea.reasignarEmpleado(nuevoEmpleado);
        
        if (!historialEmpleados.contains(nuevoEmpleado)) {
            historialEmpleados.add(nuevoEmpleado);
        }
        
        actualizarCosto();
    }

    public void registrarRetraso(String tituloTarea, double diasRetraso) {
        if (estado.equals(Estado.finalizado)) {
            throw new IllegalArgumentException("No se pueden registrar retrasos en un proyecto finalizado");
        }
        
        Tarea tarea = buscarTarea(tituloTarea);
        if (tarea == null) {
            throw new IllegalArgumentException("La tarea no existe");
        }
        
        tarea.registrarRetraso(diasRetraso);
        actualizarCosto();
    }

    public void agregarTarea(String titulo, String descripcion, double dias) {
        if (estado.equals(Estado.finalizado)) {
            throw new IllegalArgumentException("No se pueden agregar tareas a un proyecto finalizado");
        }
        
        Tarea nuevaTarea = new Tarea(titulo, descripcion, dias);
        listaDeTareas.add(nuevaTarea);
        
    }

    public void finalizarTarea(String tituloTarea) throws Exception {
        Tarea tarea = buscarTarea(tituloTarea);
        if (tarea == null) {
            throw new Exception("La tarea no existe");
        }
        
        tarea.finalizarTarea();
        actualizarCosto();
    }

    public void finalizar(String fechaFin) {
        if (estado.equals(Estado.finalizado)) {
            throw new IllegalArgumentException("El proyecto ya está finalizado");
        }
        
        this.fechaReal = fechaFin;
        this.estado = Estado.finalizado;
        
        // Liberamos empleados asignados
        for (Tarea tarea : listaDeTareas) {
            if (tarea.estaAsignada() && !tarea.estaFinalizada()) {
                Empleado emp = tarea.obtenerEmpleado();
                if (emp != null && emp.estaOcupado()) {
                    emp.liberar();
                }
            }
        }
        
        actualizarCosto();
    }

    public void activar() {
        this.estado = Estado.activo;
    }

    public double calcularCosto() {
        double costoTotal = 0;
        
        for (Tarea tarea : listaDeTareas) {
            if (tarea.obtenerEmpleado() != null) {
                double dias = tarea.obtenerDuracion();
                double pago = tarea.obtenerEmpleado().calcularPago(dias);
                costoTotal += pago;
            }
        }
        
        if (tieneRetrasos()) {
            costoTotal *= 1.25; // 25% adicional si tiene retrasos
        } else {
            costoTotal *= 1.35; // 35% adicional si NO tiene retrasos
        }
        
        return costoTotal;
    }

    public void actualizarCosto() {
        this.costoFinal = calcularCosto();
    }

    public boolean tieneRetrasos() {
        for (Tarea tarea : listaDeTareas) {
            if (tarea.obtenerDiasRetraso() > 0) {
                return true;
            }
        }
        return false;
    }

    public List<Tarea> obtenerTareas() {
        return listaDeTareas;
    }

    public List<Empleado> obtenerEmpleadosAsignados() {
        List<Empleado> empleadosActuales = new ArrayList<>();
        for (Tarea tarea : listaDeTareas) {
            if (tarea.estaAsignada() && !tarea.estaFinalizada()) {
                Empleado emp = tarea.obtenerEmpleado();
                if (!empleadosActuales.contains(emp)) {
                    empleadosActuales.add(emp);
                }
            }
        }
        return empleadosActuales;
    }

    public List<Empleado> obtenerHistorialEmpleados() {
        return historialEmpleados;
    }

    private Tarea buscarTarea(String titulo) {
        for (Tarea tarea : listaDeTareas) {
            if (tarea.getTitulo().equals(titulo)) {
                return tarea;
            }
        }
        return null;
    }


    public Integer getNumero() {
        return numero;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEstado() {
        return estado;
    }

    public List<Tarea> getTareas() {
        return listaDeTareas;
    }

    public List<Empleado> getHistorialEmpleados() {
        return historialEmpleados;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaEstimada() {
        return fechaEstimada;
    }

    public String getFechaReal() {
        return fechaReal;
    }

    public double getCostoFinal() {
        return costoFinal;
    }

    public boolean estaFinalizado() {
        return estado.equals(Estado.finalizado);
    }

    public boolean estaPendiente() {
        return estado.equals(Estado.pendiente);
    }

    public boolean estaActivo() {
        return estado.equals(Estado.activo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Proyecto #").append(numero).append("\n");
        sb.append("Dirección: ").append(direccion).append("\n");
        sb.append("Cliente: ").append(cliente).append("\n");
        sb.append("Estado: ").append(estado).append("\n");
        sb.append("Fecha inicio: ").append(fechaInicio).append("\n");
        sb.append("Fecha estimada: ").append(fechaEstimada).append("\n");
        sb.append("Fecha real: ").append(fechaReal).append("\n");
        sb.append("Costo final: $").append(String.format("%.2f", costoFinal)).append("\n"); // el %.2f es para que el numero tenga hasta 2 cifras decimales
        sb.append("Retrasos: ").append(tieneRetrasos() ? "Sí" : "No").append("\n");
        sb.append("\nTareas:\n");
        for (Tarea t : listaDeTareas) {
            sb.append("  - ").append(t.getTitulo());
            if (t.estaAsignada()) {
                sb.append(" (Asignada a: ").append(t.obtenerEmpleado().getNombre()).append(")");
            }
            if (t.estaFinalizada()) {
                sb.append(" [FINALIZADA]");
            }
            if (t.obtenerDiasRetraso() > 0) {
                sb.append(" [Retraso: ").append(t.obtenerDiasRetraso()).append(" días]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
