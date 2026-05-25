package negocio;

import modelo.MateriasTareas;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GestorMateriasTareas {
    private List<MateriasTareas> listaMaterias;

    public GestorMateriasTareas() {
        this.listaMaterias = new ArrayList<>();
    }

    public String registrarMateria(String nombreMateria, String nivelDificultad,
                                   double calificacionActual, double notaMinimaPersonal,
                                   String nombreTarea, String fechaEntrega) {

        if (notaMinimaPersonal <= 0) {
            return "Error: La nota mínima personal debe ser mayor a 0.";
        }

        MateriasTareas materia = new MateriasTareas(nombreMateria, nivelDificultad,
                calificacionActual, notaMinimaPersonal, nombreTarea, fechaEntrega);

        double horas = calcularHoras(nivelDificultad, calificacionActual, notaMinimaPersonal);
        materia.setHorasRecomendadas(horas);

        String prioridad = calcularPrioridad(fechaEntrega, nivelDificultad, calificacionActual, notaMinimaPersonal);
        materia.setPrioridad(prioridad);

        listaMaterias.add(materia);

        return generarMensajeRendimiento(calificacionActual, notaMinimaPersonal);
    }

    private double calcularHoras(String nivelDificultad, double calificacionActual, double notaMinimaPersonal) {
        double horasBase;

        if (nivelDificultad.equalsIgnoreCase("alto")) {
            horasBase = 5;
        } else if (nivelDificultad.equalsIgnoreCase("medio")) {
            horasBase = 3;
        } else {
            horasBase = 2;
        }

        if (calificacionActual < notaMinimaPersonal) {
            horasBase += 2;
        } else if (calificacionActual - notaMinimaPersonal <= 1) {
            horasBase += 1;
        }

        return horasBase;
    }

    private String generarMensajeRendimiento(double calificacionActual, double notaMinimaPersonal) {
        if (calificacionActual < notaMinimaPersonal) {
            return "Materia registrada. ⚠️ Estás por debajo de tu nota mínima, se recomienda priorizar esta materia.";
        } else if (calificacionActual - notaMinimaPersonal <= 1) {
            return "Materia registrada. Advertencia: estás cerca de tu nota mínima.";
        } else {
            return "Materia registrada. ¡Vas bien, mantén el ritmo!";
        }
    }

    private String calcularPrioridad(String fechaEntrega, String nivelDificultad,
                                     double calificacionActual, double notaMinimaPersonal) {
        LocalDate hoy = LocalDate.now();
        LocalDate fecha = LocalDate.parse(fechaEntrega, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        long diasRestantes = ChronoUnit.DAYS.between(hoy, fecha);

        if (diasRestantes < 0) {
            return "VENCIDA";
        } else if (diasRestantes <= 2 && calificacionActual < notaMinimaPersonal) {
            return "CRÍTICA";
        } else if (diasRestantes <= 5 || nivelDificultad.equalsIgnoreCase("alto")) {
            return "ALTA";
        } else if (diasRestantes <= 10 && nivelDificultad.equalsIgnoreCase("medio")) {
            return "MEDIA";
        } else {
            return "BAJA";
        }
    }

    public List<MateriasTareas> getListaMaterias() {
        return listaMaterias;
    }
}