package modelo;

public class RutinasEstudio {

    private String dia;
    private String horaInicio;
    private String horaFin;
    private int duracionMinutos;
    private boolean esDescanso;
    private boolean cumplida;
    private Tarea tarea;

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
                + " | " + tarea.getMateria().getNombreMateria()
                + " | Tarea: " + tarea.getNombreTarea()
                + " | " + duracionMinutos + " min"
                + " | Cumplida: " + cumplida;
    }
}