package modelo;

public class MateriasTareas {
    private String nombreMateria;
    private String nivelDificultad;
    private double calificacionActual;
    private double notaMinimaPersonal;
    private double horasRecomendadas;
    private String nombreTarea;
    private String fechaEntrega;
    private String prioridad;

    //Constructores
    public MateriasTareas(String nombreMateria, String nivelDificultad, double calificacionActual,
                          double notaMinimaPersonal, String nombreTarea, String fechaEntrega) {
        this.nombreMateria = nombreMateria;
        setNivelDificultad(nivelDificultad);
        setCalificacionActual(calificacionActual);
        setNotaMinimaPersonal(notaMinimaPersonal);
        this.nombreTarea = nombreTarea;
        this.fechaEntrega = fechaEntrega;
    }

    //Getter and Setter "nombreMateria"
    public String getNombreMateria(){ return nombreMateria; }
    public void setNombreMateria(String nombreMateria){ this.nombreMateria = nombreMateria;}

    //Getter and Setter "nivelDificultad"
    public String getNivelDificultad(){ return nivelDificultad; }

    public void setNivelDificultad(String nivelDificultad){
        if (nivelDificultad.equalsIgnoreCase("alto") ||
                nivelDificultad.equalsIgnoreCase("medio") ||
                nivelDificultad.equalsIgnoreCase("bajo")) {
            this.nivelDificultad = nivelDificultad;
        } else {
            this.nivelDificultad = "medio";
        }
    }

    //Getter and Setter "calificacionActual"
    public double getCalificacionActual(){
        return calificacionActual;
    }
    public void setCalificacionActual(double calificacionActual) {
        if (calificacionActual >= 0 && calificacionActual <= 10)
            this.calificacionActual = calificacionActual;
    }

    //Getter and Setter "notaMinimaPersonal"
    public double getNotaMinimaPersonal(){
        return notaMinimaPersonal;
    }
    public void setNotaMinimaPersonal(double notaMinimaPersonal) {
        if (notaMinimaPersonal > 0 && notaMinimaPersonal <= 10)
            this.notaMinimaPersonal = notaMinimaPersonal;
    }

    //Getter and Setter "horasRecomendadas"
    public double getHorasRecomendadas() { return horasRecomendadas; }
    public void setHorasRecomendadas(double horasRecomendadas) { this.horasRecomendadas = horasRecomendadas; }

    //Getter and Setter "nombreTarea"
    public String getNombreTarea() { return nombreTarea; }
    public void setNombreTarea(String nombreTarea) { this.nombreTarea = nombreTarea; }

    //Getter and Setter "fechaEntrega"
    public String getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(String fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    //Getter and Setter "prioridad"
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

}
