package entidades;

import java.util.*;

public class HomeSolution implements IHomeSolution {

    private Map<Integer, Empleado> empleados;
    private Map<Integer, Proyecto> proyectos;
    private List<Empleado> empleadosLibres;
    private int contadorProyectos;
    private int contadorLegajos;

    public HomeSolution() {
        this.empleados = new HashMap<>();
        this.proyectos = new HashMap<>();
        this.empleadosLibres = new ArrayList<>();
        this.contadorProyectos = 0;
        this.contadorLegajos = 0;
    }

    // ============================================================
    // REGISTRO DE EMPLEADOS
    // ============================================================

    @Override
    public void registrarEmpleado(String nombre, double valor) throws IllegalArgumentException {
        if (nombre == null || nombre.isEmpty() || valor <= 0)
            throw new IllegalArgumentException("Datos inválidos para empleado contratado.");

        contadorLegajos++;
        EmpleadoContratado emp = new EmpleadoContratado(nombre, contadorLegajos, valor);
        empleados.put(emp.getLegajo(), emp);
        empleadosLibres.add(emp);
    }

    @Override
    public void registrarEmpleado(String nombre, double valor, String categoria) throws IllegalArgumentException {
        if (nombre == null || nombre.isEmpty() || valor <= 0)
            throw new IllegalArgumentException("Datos inválidos para empleado de planta.");
        if (!categoria.equals("INICIAL") && !categoria.equals("TECNICO") && !categoria.equals("EXPERTO"))
            throw new IllegalArgumentException("Categoría inválida.");

        contadorLegajos++;
        EmpleadoPlanta emp = new EmpleadoPlanta(nombre, contadorLegajos, valor, categoria);
        empleados.put(emp.getLegajo(), emp);
        empleadosLibres.add(emp);
    }

    // ============================================================
    // REGISTRO DE PROYECTOS
    // ============================================================

    @Override
    public void registrarProyecto(String[] titulos, String[] descripcion, double[] dias,
                                  String domicilio, String[] cliente, String inicio, String fin)
            throws IllegalArgumentException {

        if (titulos == null || descripcion == null || dias == null || cliente == null)
            throw new IllegalArgumentException("Datos del proyecto incompletos.");
        if (titulos.length == 0)
            throw new IllegalArgumentException("Debe haber al menos una tarea.");

        contadorProyectos++;

        List<Tarea> listaTareas = new ArrayList<>();
        for (int i = 0; i < titulos.length; i++) {
            listaTareas.add(new Tarea(titulos[i], descripcion[i], dias[i]));
        }

        Proyecto proyecto = new Proyecto(contadorProyectos, cliente, domicilio, listaTareas, inicio, fin);
        proyectos.put(contadorProyectos, proyecto);
    }

    // ============================================================
    // CONSULTAS BÁSICAS
    // ============================================================

    @Override
    public Object[] empleadosNoAsignados() {
        List<Empleado> libres = new ArrayList<>();
        for (Empleado e : empleados.values()) {
            if (e.estaLibre())
                libres.add(e);
        }
        return libres.toArray();
    }

    @Override
    public boolean estaFinalizado(Integer numero) {
        Proyecto p = proyectos.get(numero);
        if (p == null) throw new IllegalArgumentException("Proyecto inexistente.");
        return p.estaFinalizado();
    }

    @Override
    public List<Tupla<Integer, String>> proyectosActivos() {
        List<Tupla<Integer, String>> lista = new ArrayList<>();
        for (Proyecto p : proyectos.values()) {
            if (p.estaActivo())
                lista.add(new Tupla<>(p.getNumero(), p.getDireccion()));
        }
        return lista;
    }

    @Override
    public List<Tupla<Integer, String>> proyectosPendientes() {
        List<Tupla<Integer, String>> lista = new ArrayList<>();
        for (Proyecto p : proyectos.values()) {
            if (p.estaPendiente())
                lista.add(new Tupla<>(p.getNumero(), p.getDireccion()));
        }
        return lista;
    }

    @Override
    public List<Tupla<Integer, String>> proyectosFinalizados() {
        List<Tupla<Integer, String>> lista = new ArrayList<>();
        for (Proyecto p : proyectos.values()) {
            if (p.estaFinalizado())
                lista.add(new Tupla<>(p.getNumero(), p.getDireccion()));
        }
        return lista;
    }

    // ============================================================
    // TO STRING GENERAL
    // ============================================================

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("======== HOME SOLUTION ========\n");
        for (Proyecto p : proyectos.values()) {
            sb.append(p.toString()).append("\n------------------------------\n");
        }
        return sb.toString();
    }


//============================================================
// ASIGNACIÓN Y GESTIÓN DE TAREAS
// ============================================================

@Override
public void asignarResponsableEnTarea(Integer numero, String titulo) throws Exception {
    Proyecto p = proyectos.get(numero);
    if (p == null) throw new Exception("Proyecto inexistente.");
    if (p.estaFinalizado()) throw new Exception("El proyecto ya está finalizado.");

    // buscamos un empleado libre cualquiera
    Empleado libre = null;
    for (Empleado e : empleados.values()) {
        if (e.estaLibre()) {
            libre = e;
            break;
        }
    }

    if (libre == null) {
        throw new Exception("No hay empleados disponibles.");
    }

    p.asignarEmpleadoTarea(titulo, libre);
    empleadosLibres.remove(libre);
}

@Override
public void asignarResponsableMenosRetraso(Integer numero, String titulo) throws Exception {
    Proyecto p = proyectos.get(numero);
    if (p == null) throw new Exception("Proyecto inexistente.");
    if (p.estaFinalizado()) throw new Exception("El proyecto ya está finalizado.");

    // buscamos el empleado libre con menos retrasos
    Empleado mejor = null;
    for (Empleado e : empleados.values()) {
        if (e.estaLibre()) {
            if (mejor == null || e.getCantidadRetrasos() < mejor.getCantidadRetrasos())
                mejor = e;
        }
    }

    if (mejor == null) {
        throw new Exception("No hay empleados disponibles.");
    }

    p.asignarEmpleadoTarea(titulo, mejor);
    empleadosLibres.remove(mejor);
}

@Override
public void registrarRetrasoEnTarea(Integer numero, String titulo, double cantidadDias)
        throws IllegalArgumentException {
    Proyecto p = proyectos.get(numero);
    if (p == null)
        throw new IllegalArgumentException("Proyecto inexistente.");
    p.registrarRetraso(titulo, cantidadDias);
}

@Override
public void agregarTareaEnProyecto(Integer numero, String titulo, String descripcion, double dias)
        throws IllegalArgumentException {
    Proyecto p = proyectos.get(numero);
    if (p == null)
        throw new IllegalArgumentException("Proyecto inexistente.");
    p.agregarTarea(titulo, descripcion, dias);
}

@Override
public void finalizarTarea(Integer numero, String titulo) throws Exception {
    Proyecto p = proyectos.get(numero);
    if (p == null) throw new Exception("Proyecto inexistente.");
    p.finalizarTarea(titulo);
}

@Override
public void finalizarProyecto(Integer numero, String fin) throws IllegalArgumentException {
    Proyecto p = proyectos.get(numero);
    if (p == null)
        throw new IllegalArgumentException("Proyecto inexistente.");
    p.finalizar(fin);
}

// ============================================================
// REASIGNACIÓN DE EMPLEADOS
// ============================================================

@Override
public void reasignarEmpleadoEnProyecto(Integer numero, Integer legajo, String titulo) throws Exception {
    Proyecto p = proyectos.get(numero);
    if (p == null) throw new Exception("Proyecto inexistente.");
    if (p.estaFinalizado()) throw new Exception("Proyecto finalizado.");

    Empleado nuevo = empleados.get(legajo);
    if (nuevo == null) throw new Exception("Empleado inexistente.");
    if (!nuevo.estaLibre()) throw new Exception("Empleado ocupado.");

    p.reasignarEmpleado(titulo, nuevo);
}

@Override
public void reasignarEmpleadoConMenosRetraso(Integer numero, String titulo) throws Exception {
    Proyecto p = proyectos.get(numero);
    if (p == null) throw new Exception("Proyecto inexistente.");
    if (p.estaFinalizado()) throw new Exception("Proyecto finalizado.");

    Empleado mejor = null;
    for (Empleado e : empleados.values()) {
        if (e.estaLibre()) {
            if (mejor == null || e.getCantidadRetrasos() < mejor.getCantidadRetrasos())
                mejor = e;
        }
    }

    if (mejor == null)
        throw new Exception("No hay empleados disponibles.");

    p.reasignarEmpleado(titulo, mejor);
}

// ============================================================
// CONSULTAS Y REPORTES
// ============================================================

@Override
public double costoProyecto() {
    double total = 0;
    for (Proyecto p : proyectos.values()) {
        total += p.calcularCosto();
    }
    return total;
}

@Override
public int consultarCantidadRetrasosEmpleado(Integer legajo) {
    Empleado e = empleados.get(legajo);
    if (e == null)
        throw new IllegalArgumentException("Empleado inexistente.");
    return e.getCantidadRetrasos();
}

@Override
public List<Tupla<Integer, String>> empleadosAsignadosAProyecto(Integer numero) {
    Proyecto p = proyectos.get(numero);
    if (p == null)
        throw new IllegalArgumentException("Proyecto inexistente.");

    List<Tupla<Integer, String>> lista = new ArrayList<>();
    for (Empleado e : p.obtenerEmpleadosAsignados()) {
        lista.add(new Tupla<>(e.getLegajo(), e.getNombre()));
    }
    return lista;
}

@Override
public Object[] tareasProyectoNoAsignadas(Integer numero) {
    Proyecto p = proyectos.get(numero);
    if (p == null)
        throw new IllegalArgumentException("Proyecto inexistente.");

    List<Tarea> noAsignadas = new ArrayList<>();
    for (Tarea t : p.obtenerTareas()) {
        if (!t.estaAsignada())
            noAsignadas.add(t);
    }
    return noAsignadas.toArray();
}

@Override
public Object[] tareasDeUnProyecto(Integer numero) {
    Proyecto p = proyectos.get(numero);
    if (p == null)
        throw new IllegalArgumentException("Proyecto inexistente.");
    return p.obtenerTareas().toArray();
}

@Override
public String consultarDomicilioProyecto(Integer numero) {
    Proyecto p = proyectos.get(numero);
    if (p == null)
        throw new IllegalArgumentException("Proyecto inexistente.");
    return p.getDireccion();
}

@Override
public boolean tieneRestrasos(String legajo) {
    int num = Integer.parseInt(legajo);
    Empleado e = empleados.get(num);
    if (e == null)
        throw new IllegalArgumentException("Empleado inexistente.");
    return e.getCantidadRetrasos() > 0;
}

@Override
public List<Tupla<Integer, String>> empleados() {
    List<Tupla<Integer, String>> lista = new ArrayList<>();
    for (Empleado e : empleados.values()) {
        lista.add(new Tupla<>(e.getLegajo(), e.getNombre()));
    }
    return lista;
}

@Override
public String consultarProyecto(Integer numero) {
    Proyecto p = proyectos.get(numero);
    if (p == null)
        throw new IllegalArgumentException("Proyecto inexistente.");
    return p.toString();
}
}   