import java.time.LocalDate;
import java.util.Objects;

public class Inscripcion {
    private final Alumno alumno;
    private final Curso curso;
    private final LocalDate fechaInscripcion;

    public Inscripcion(Alumno alumno, Curso curso){
        if (alumno == null){
            throw new IllegalArgumentException("El alumno no puede estar vacio")
        }
        if (curso == null){
            throw new IllegalArgumentException("el curso no puede estar vacio")
        }
        this.alumno = alumno;
        this.curso = curso;
        this.fechaInscripcion = LocalDate.now();
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
    
}
