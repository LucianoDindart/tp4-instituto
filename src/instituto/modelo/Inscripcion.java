package instituto.modelo;

import java.util.Date;

public class Inscripcion {
    private final Alumno alumno;
    private final Curso curso;
    private Date fechaInscripcion;
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
        this.fechaInscripcion = new Date();
        this.estadoCuota = estadoCuota;
    }

    public Inscripcion(Alumno alumno, Curso curso, Date fecha, boolean estadoCuota){
        this(alumno, curso, estadoCuota);
        this.fechaInscripcion = fecha;
    }   

    public Alumno getAlumno() {
        return alumno;
    }

    public Curso getCurso() {
        return curso;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean isEstadoCuota() {
        return estadoCuota;
    }

    public void setEstadoCuota(boolean estadoCuota) {
        this.estadoCuota = estadoCuota;
    }
}
