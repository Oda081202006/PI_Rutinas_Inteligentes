package negocio;

import modelo.MateriasTareas;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GestorMateriasTareas {
    // El gestor tiene una lista que guarda todas las materias que el estudiante va registrando:
    private List<MateriasTareas> listaMaterias;

    public GestorMateriasTareas() {
        this.listaMaterias = new ArrayList<>();
    }
    //-

    // Registra materia, valida que sea mayor a 0, si no lanza error y si esta bien crea el objeto materia y calcula
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

        listaMaterias.add(materia); //Guarda todos los datos llenados anteriormente por el estudiante es como decir "la ficha está completa, archívala"
        return generarMensajeRendimiento(calificacionActual, notaMinimaPersonal); //Retorna el mensaje que le va a aparecer al estudiante, si va bien o no
    }

    private double calcularHoras(String nivelDificultad, double calificacionActual, double notaMinimaPersonal) {
        double horasBase;

        //Asigna las horas base que definimos
        if (nivelDificultad.equalsIgnoreCase("alto")) {
            horasBase = 5;
        } else if (nivelDificultad.equalsIgnoreCase("medio")) {
            horasBase = 3;
        } else {
            horasBase = 2;
        }

        //Si estás por debajo de tu nota mínima, le suma 2 horas extra a las base.
        if (calificacionActual < notaMinimaPersonal) {
            horasBase += 2;
        //Detecta si el usuario esta pasando con las justas, entonces el programa le aumenta una hora de estudio, porque está cerca de su limite
        } else if (calificacionActual - notaMinimaPersonal <= 1) {
            horasBase += 1;
        }

        return horasBase;
    }

    private String generarMensajeRendimiento(double calificacionActual, double notaMinimaPersonal) {
        if (calificacionActual < notaMinimaPersonal) {
            return "Materia registrada. Estás por debajo de tu nota mínima, se recomienda priorizar esta materia.";
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

    //Forma de acceder a todos los datos que guardamos
    public List<MateriasTareas> getListaMaterias() {
        return listaMaterias;
    }

    public String listarMaterias() {
        if (listaMaterias.isEmpty()) {
            return "No hay materias registradas aún.";
        }

        String texto = "--- LISTA DE MATERIAS Y TAREAS ---\n\n";

        for (MateriasTareas materia : listaMaterias) {
            texto += materia.mostrarDatos() + "\n\n";
        }

        return texto;
    }
}