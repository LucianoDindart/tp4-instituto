package instituto.modelo;

import java.util.HashSet;

public class Curso {
    private String codigo;
    private String idioma; 
    private String nivel;
    private String turno; 
    private int cupo; 
    private double cuota; 
    private static HashSet<String> codigosUnicos = new HashSet<>();


    public Curso (String codigo, String idioma, String nivel, String turno, int cupo, double cuota){
        this.codigo = codigo;
        this.idioma = idioma;
        this.nivel = nivel;
        this.turno = turno; 
        this.cupo = cupo; 
        this.cuota = cuota; 
    
    }

    public String getCodigo () {
        return codigo; 
    }
    
    private  void setCodigo (String codigo) {
        if (codigo == null) {
            throw new IllegalArgumentException("El curso tiene que tener codigo.");
        }
        String codigoLimpio = codigo.trim();
        if (codigosUnicos.contains(codigoLimpio)) {
            throw new IllegalArgumentException("Ese codigo ya esta siendo usado por curso");

        }
        this.codigo = codigoLimpio;
        codigosUnicos.add(codigoLimpio);



        

    }
    
    public String getIdioma () {
        return idioma; 
    }

  

    public String getNivel () {
        return nivel;
    }

 

    public String getTurno () {
        return turno;

    }

    
    public int getCupo () {
        return cupo; 
    }
   
    public double getCuota () {
        return cuota;
    }
  



    
}
