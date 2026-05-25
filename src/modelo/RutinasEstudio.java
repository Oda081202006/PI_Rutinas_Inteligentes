package modelo;

public class RutinasEstudio {
        private String dia;
        private String horaInicio;
        private String horaFin;
        private int duracionSesionMinutos;
        private int disponibilidadMaximaMinutos;
        private String nombreMateria;
        private String nivelDificultad;
        private String fechaExamen;
        private String nombreTarea;
        private String fechaLimiteTarea;
        private String prioridad;
        private boolean esDescanso;
        private boolean cumplida;

        //Constructores
        public RutinasEstudio(String dia, String horaInicio, String horaFin,
                              int duracionSesionMinutos, int disponibilidadMaximaMinutos,
                              String nombreMateria, String nivelDificultad,
                              String fechaExamen, String nombreTarea, String fechaLimiteTarea) {
            this.dia = dia;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
            setDuracionSesionMinutos(duracionSesionMinutos);
            setDisponibilidadMaximaMinutos(disponibilidadMaximaMinutos);
            this.nombreMateria = nombreMateria;
            setNivelDificultad(nivelDificultad);
            this.fechaExamen = fechaExamen;
            this.nombreTarea = nombreTarea;
            this.fechaLimiteTarea = fechaLimiteTarea;
            this.esDescanso = false;
            this.cumplida = false;
        }

        //Getter and Setter "dia"
        public String getDia() { return dia; }
        public void setDia(String dia) { this.dia = dia; }

        //Getter and Setter "horaInicio"
        public String getHoraInicio() { return horaInicio; }
        public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

        //Getter and Setter "horaFin"
        public String getHoraFin() { return horaFin; }
        public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

        //Getter and Setter "duracionSesionMinutos"
        public int getDuracionSesionMinutos() { return duracionSesionMinutos; }
        public void setDuracionSesionMinutos(int duracionSesionMinutos) {
            if (duracionSesionMinutos >= 15 && duracionSesionMinutos <= 480)
                this.duracionSesionMinutos = duracionSesionMinutos;
            else
                this.duracionSesionMinutos = 60;
        }

        //Getter and Setter "disponibilidadMaximaMinutos"
        public int getDisponibilidadMaximaMinutos() { return disponibilidadMaximaMinutos; }
        public void setDisponibilidadMaximaMinutos(int disponibilidadMaximaMinutos) {
            if (disponibilidadMaximaMinutos > 0 && disponibilidadMaximaMinutos <= 720)
                this.disponibilidadMaximaMinutos = disponibilidadMaximaMinutos;
            else
                this.disponibilidadMaximaMinutos = 240;
        }

        //Getter and Setter "nombreMateria"
        public String getNombreMateria() { return nombreMateria; }
        public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

        //Getter and Setter "nivelDificultad"
        public String getNivelDificultad() { return nivelDificultad; }
        public void setNivelDificultad(String nivelDificultad) {
            if (nivelDificultad.equalsIgnoreCase("alto") ||
                    nivelDificultad.equalsIgnoreCase("medio") ||
                    nivelDificultad.equalsIgnoreCase("bajo"))
                this.nivelDificultad = nivelDificultad;
            else
                this.nivelDificultad = "medio";
        }

        //Getter and Setter "fechaExamen"
        public String getFechaExamen() { return fechaExamen; }
        public void setFechaExamen(String fechaExamen) { this.fechaExamen = fechaExamen; }

        //Getter and Setter "nombreTarea"
        public String getNombreTarea() { return nombreTarea; }
        public void setNombreTarea(String nombreTarea) { this.nombreTarea = nombreTarea; }

        //Getter and Setter "fechaLimiteTarea"
        public String getFechaLimiteTarea() { return fechaLimiteTarea; }
        public void setFechaLimiteTarea(String fechaLimiteTarea) { this.fechaLimiteTarea = fechaLimiteTarea; }

        //Getter and Setter "prioridad"
        public String getPrioridad() { return prioridad; }
        public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

        //Getter and Setter "esDescanso"
        public boolean isEsDescanso() { return esDescanso; }
        public void setEsDescanso(boolean esDescanso) { this.esDescanso = esDescanso; }

        //Getter and Setter "cumplida"
        public boolean isCumplida() { return cumplida; }
        public void setCumplida(boolean cumplida) { this.cumplida = cumplida; }
}

