import java.util.HashSet;
import java.util.Objects;

public class Alumno {
    private String nombre;
    private String apellido;
    private String  dni; 
    private String correo;
    private static HashSet<String> dnisUnicos = new HashSet<>();
    
   

    public Alumno (String nombre, String apellido, String dni, String correo){
    this.nombre = nombre;
    this.apellido = apellido;
    this.dni = dni;
    this.correo = correo;

 }

public String getNombre() { 
    return nombre; 
}
    

public void setNombre(String nombre) {
         this.nombre = nombre; 
    }

public String getApellido() { 
    return apellido; 
}
public void setApellido(String apellido) {
     this.apellido = apellido; 

}

public String getDni(){
    return dni;

}
    private void setDni(String dni) {
        if(dni == null) {
            throw new IllegalArgumentException("El alumno tiene que tener dni.");
        }
        String dniLimpio = dni.trim();
        if (dnisUnicos.contains (dniLimpio)) {
            throw new IllegalArgumentException ("Ese dni ya esta siendo usado por otro alumno");

        }
        this.dni = dniLimpio;
        dnisUnicos.add(dniLimpio);
    }
    
    
      
    public String getCorreo () {
        return correo;
    }
    private void setCorreo (String correo) {
            if (correo == null || correo.isEmpty()) {
                this.correo = null; 
            } else {
                this.correo = correo.trim();
            }
    }

   
 


}
