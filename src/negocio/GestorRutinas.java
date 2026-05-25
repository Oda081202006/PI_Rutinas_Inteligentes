package negocio;

import modelo.RutinasEstudio;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GestorRutinas {
        private List<RutinasEstudio> rutinaSemanal;

        public GestorRutinas() {
            this.rutinaSemanal = new ArrayList<>();
        }

        public String generarRutina(List<RutinasEstudio> sesiones) {
            if (sesiones == null || sesiones.isEmpty())
                return "Error: No se proporcionaron sesiones de estudio.";

            int totalNecesario = 0;
            int totalDisponible = 0;

            for (RutinasEstudio s : sesiones) {
                totalNecesario += s.getDuracionSesionMinutos();
                totalDisponible += s.getDisponibilidadMaximaMinutos();
            }

            if (totalDisponible < totalNecesario)
                return "No hay suficiente disponibilidad horaria para cubrir todas las materias.";

            rutinaSemanal = aplicarDescansosCognitivos(sesiones);

            for (RutinasEstudio s : rutinaSemanal) {
                if (!s.isEsDescanso()) {
                    s.setPrioridad(calcularPrioridad(s.getFechaLimiteTarea(), s.getNivelDificultad()));
                }
            }

            return "Rutina generada exitosamente";
        }

        private List<RutinasEstudio> aplicarDescansosCognitivos(List<RutinasEstudio> sesiones) {
            List<RutinasEstudio> resultado = new ArrayList<>();
            RutinasEstudio anterior = null;

            for (RutinasEstudio actual : sesiones) {
                if (anterior != null && esAltaExigencia(anterior) && esAltaExigencia(actual)) {
                    RutinasEstudio descanso = new RutinasEstudio(
                            actual.getDia(), "--", "--", 15,
                            actual.getDisponibilidadMaximaMinutos(),
                            "DESCANSO", "bajo", "--", "--", "--"
                    );
                    descanso.setEsDescanso(true);
                    resultado.add(descanso);
                }
                resultado.add(actual);
                anterior = actual;
            }
            return resultado;
        }

        private boolean esAltaExigencia(RutinasEstudio s) {
            return s.getNivelDificultad().equalsIgnoreCase("alto");
        }

        private String calcularPrioridad(String fechaLimite, String nivelDificultad) {
            LocalDate hoy = LocalDate.now();
            LocalDate fecha = LocalDate.parse(fechaLimite, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            long dias = ChronoUnit.DAYS.between(hoy, fecha);

            if (dias < 0) return "VENCIDA";
            else if (dias <= 2 && nivelDificultad.equalsIgnoreCase("alto")) return "CRÍTICA";
            else if (dias <= 5 || nivelDificultad.equalsIgnoreCase("alto")) return "ALTA";
            else if (dias <= 10 && nivelDificultad.equalsIgnoreCase("medio")) return "MEDIA";
            else return "BAJA";
        }

        public String reprogramarSesion(String nombreMateria, String nuevoDia, String nuevaHoraInicio, String nuevaHoraFin) {
            for (RutinasEstudio s : rutinaSemanal) {
                if (!s.isEsDescanso() && s.getNombreMateria().equalsIgnoreCase(nombreMateria)) {
                    s.setDia(nuevoDia);
                    s.setHoraInicio(nuevaHoraInicio);
                    s.setHoraFin(nuevaHoraFin);

                    String alerta = verificarMateriasDificilesConsecutivas(nuevoDia, s);
                    return "Cambios guardados correctamente." + (alerta.isEmpty() ? "" : "\n" + alerta);
                }
            }
            return "Error: No se encontró la materia '" + nombreMateria + "' en la rutina.";
        }

        public String marcarSesionIncumplida(String nombreMateria) {
            for (RutinasEstudio s : rutinaSemanal) {
                if (!s.isEsDescanso() && s.getNombreMateria().equalsIgnoreCase(nombreMateria)) {
                    s.setCumplida(false);
                    return "Sesión de '" + nombreMateria + "' marcada como incumplida. "
                            + "Los minutos serán redistribuidos en los días restantes de la semana.";
                }
            }
            return "Error: No se encontró la materia '" + nombreMateria + "' en la rutina.";
        }

        private String verificarMateriasDificilesConsecutivas(String dia, RutinasEstudio sesionNueva) {
            for (RutinasEstudio s : rutinaSemanal) {
                if (s.getDia().equalsIgnoreCase(dia) && !s.equals(sesionNueva) && esAltaExigencia(s) && esAltaExigencia(sesionNueva))
                    return "Has agendado dos materias muy complejas seguidas. El sistema recomienda un descanso de 15 minutos. ¿Aplicar?";
            }
            return "";
        }

        public List<RutinasEstudio> getRutinaSemanal() { return rutinaSemanal; }
}

