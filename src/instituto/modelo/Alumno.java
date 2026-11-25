package instituto.modelo;

import java.util.HashSet;
import java.util.Objects;

public class Alumno {
    private String nombre;
    private String apellido;
    private String dni;
    private String correo;

    public Alumno(String nombre, String apellido, String dni, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        setDni(dni);
        setCorreo(correo);

    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;

    }

    private void setDni(String dni) {
        if (dni == null) {
            throw new IllegalArgumentException("El alumno tiene que tener dni.");
        }
        String dniLimpio = dni.trim();
        this.dni = dniLimpio;
    }

    public String getCorreo() {
        return correo;
    }

    private void setCorreo(String correo) {
        if (correo == null || correo.isEmpty()) {
            this.correo = null;
        } else {
            this.correo = correo.trim();
        }
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + dni + ")";
    }
}
