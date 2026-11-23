package instituto.modelo;

import java.time.LocalDate;

public class Inscripcion {
    private final Alumno alumno;
    private final Curso curso;
    private final LocalDate fechaInscripcion;
    private boolean estadoCuota;

    public Inscripcion(Alumno alumno, Curso curso, boolean estadoCuota){
        if (alumno == null){
            throw new IllegalArgumentException("El alumno no puede estar vacio");
        }
        if (curso == null){
            throw new IllegalArgumentException("el curso no puede estar vacio");
        }
        this.alumno = alumno;
        this.curso = curso;
        this.fechaInscripcion = LocalDate.now();
        this.estadoCuota = estadoCuota;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean isEstadoCuota() {
        return estadoCuota;
    }

    public void setEstadoCuota(boolean estadoCuota) {
        this.estadoCuota = estadoCuota;
    }
}
