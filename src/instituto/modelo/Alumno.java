public class Alumno {
    private String nombre;
    private String apellido;
    private String  dni; 
    private String correo; 


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

public void setDni (String dni){
    this.dni = dni;
}

public String getCorreo () {
    return correo;
}
public void setCorreo (String correo) {
    this.correo = correo;
}

    
}
