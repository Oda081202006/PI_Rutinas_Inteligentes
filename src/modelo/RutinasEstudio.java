package modelo;

public class RutinasEstudio {

    public static class Materia {

        private String nombre;
        private String nivelDificultad; // "alto", "medio", "bajo"

        public Materia(String nombre, String nivelDificultad) {
            this.nombre = nombre;
            setNivelDificultad(nivelDificultad);
        }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getNivelDificultad() { return nivelDificultad; }
        public void setNivelDificultad(String nivelDificultad) {
            if (nivelDificultad.equalsIgnoreCase("alto")
                    || nivelDificultad.equalsIgnoreCase("medio")
                    || nivelDificultad.equalsIgnoreCase("bajo")) {
                this.nivelDificultad = nivelDificultad.toLowerCase();
            } else {
                this.nivelDificultad = "medio";
            }
        }

        @Override
        public String toString() {
            return nombre + " [" + nivelDificultad + "]";
        }
    }

    public static class Tarea {

        private String nombre;
        private String fechaLimite; // formato dd/MM/yyyy
        private Materia materia;

        public Tarea(String nombre, String fechaLimite, Materia materia) {
            this.nombre = nombre;
            this.fechaLimite = fechaLimite;
            this.materia = materia;
        }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getFechaLimite() { return fechaLimite; }
        public void setFechaLimite(String fechaLimite) { this.fechaLimite = fechaLimite; }

        public Materia getMateria() { return materia; }
        public void setMateria(Materia materia) { this.materia = materia; }

        @Override
        public String toString() {
            return nombre + " | Limite: " + fechaLimite + " | Materia: " + materia.getNombre();
        }
    }

    private String dia;
    private String horaInicio;
    private String horaFin;
    private int duracionMinutos; // entre 15 y 480
    private boolean esDescanso;
    private boolean cumplida;
    private Tarea tarea; // referencia al objeto Tarea (null si es descanso)

    // Constructor para sesion de estudio
    public RutinasEstudio(String dia, String horaInicio, String horaFin,
                          int duracionMinutos, Tarea tarea) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        setDuracionMinutos(duracionMinutos);
        this.tarea = tarea;
        this.esDescanso = false;
        this.cumplida = false;
    }

    // Constructor para bloque de descanso (sin tarea)
    public RutinasEstudio(String dia, String horaInicio, String horaFin, int duracionMinutos) {
        this(dia, horaInicio, horaFin, duracionMinutos, null);
        this.esDescanso = true;
    }

    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) {
        if (duracionMinutos >= 15 && duracionMinutos <= 480) {
            this.duracionMinutos = duracionMinutos;
        } else {
            this.duracionMinutos = 60;
        }
    }

    public boolean isEsDescanso() { return esDescanso; }
    public void setEsDescanso(boolean esDescanso) { this.esDescanso = esDescanso; }

    public boolean isCumplida() { return cumplida; }
    public void setCumplida(boolean cumplida) { this.cumplida = cumplida; }

    public Tarea getTarea() { return tarea; }
    public void setTarea(Tarea tarea) { this.tarea = tarea; }

    @Override
    public String toString() {
        if (esDescanso) {
            return dia + " " + horaInicio + "-" + horaFin
                    + " | DESCANSO (" + duracionMinutos + " min)";
        }
        return dia + " " + horaInicio + "-" + horaFin
                + " | " + tarea.getMateria().getNombre()
                + " | Tarea: " + tarea.getNombre()
                + " | " + duracionMinutos + " min"
                + " | Cumplida: " + cumplida;
    }
}
