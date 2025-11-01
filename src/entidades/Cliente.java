package entidades;

public class Cliente {
    private String nombre;
    private String email;
    private String telefono;

    public Cliente(String nombre, String email, String telefono) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío");
        }
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }
    
    public void modificarTelefono(String nuevoTelefono) {
        this.telefono = nuevoTelefono;
    }

    public void modificarEmail(String nuevoEmail) {
        this.email = nuevoEmail;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return nombre + " (" + email + " - " + telefono + ")";
    }
}