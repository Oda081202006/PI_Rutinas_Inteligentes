package interfaz;

import modelo.Materia;
import modelo.Tarea;
import modelo.RutinasEstudio;
import modelo.Usuario;
import negocio.GestorMateriasTareas;
import negocio.GestorRutinas;
import negocio.GestorUsuario;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainUsuarios {

    static GestorUsuario gestorUsuario = new GestorUsuario();
    static GestorMateriasTareas gestorMaterias = new GestorMateriasTareas();
    static GestorRutinas gestorRutinas = new GestorRutinas();
    static Usuario usuarioActivo = null;

    public static void main(String[] args) {
        int opcion;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "════════════════════════════\n" +
                            "   SISTEMA DE RUTINAS DE ESTUDIO\n" +
                            "════════════════════════════\n" +
                            "1. Registrarse\n" +
                            "2. Iniciar sesión\n" +
                            "0. Salir\n" +
                            "════════════════════════════\n" +
                            "Seleccione una opción:"));

            switch (opcion) {
                case 1 -> registrarUsuario();
                case 2 -> iniciarSesion();
                case 0 -> JOptionPane.showMessageDialog(null, "¡Hasta luego!");
                default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        } while (opcion != 0);
    }

    static void registrarUsuario() {
        String nombre = JOptionPane.showInputDialog("Nombre completo:");
        String correo = JOptionPane.showInputDialog("Correo electrónico:");
        String carrera = JOptionPane.showInputDialog("Carrera:");
        int semestre = Integer.parseInt(JOptionPane.showInputDialog("Semestre:"));
        int materias = Integer.parseInt(JOptionPane.showInputDialog("Cantidad de materias:"));
        int horas = Integer.parseInt(JOptionPane.showInputDialog("Horas disponibles por semana:"));
        String contrasena = JOptionPane.showInputDialog("Contraseña (mínimo 8 caracteres):");

        Usuario usuario = new Usuario(nombre, correo, carrera, semestre, materias, horas, contrasena, "");
        String resultado = gestorUsuario.registrarUsuario(usuario);
        JOptionPane.showMessageDialog(null, resultado);
    }

    static void iniciarSesion() {
        String correo = JOptionPane.showInputDialog("Correo electrónico:");
        String contrasena = JOptionPane.showInputDialog("Contraseña:");

        usuarioActivo = gestorUsuario.iniciarSesion(correo, contrasena);

        if (usuarioActivo == null) {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos, o cuenta bloqueada.");
        } else {
            JOptionPane.showMessageDialog(null, "¡Bienvenido, " + usuarioActivo.getNombreCompleto() + "!");
            menuPrincipal();
        }
    }

    static void menuPrincipal() {
        int opcion;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "════════════════════════════\n" +
                            "   MENÚ PRINCIPAL\n" +
                            "════════════════════════════\n" +
                            "1. Registrar materia\n" +
                            "2. Registrar tarea\n" +
                            "3. Ver materias\n" +
                            "4. Ver tareas\n" +
                            "5. Eliminar materia\n" +
                            "6. Eliminar tarea\n" +
                            "7. Marcar tarea como completada\n" +
                            "8. Agregar sesión a rutina\n" +
                            "9. Ver rutina semanal\n" +
                            "10. Reprogramar sesión\n" +
                            "11. Marcar sesión como cumplida\n" +
                            "0. Cerrar sesión\n" +
                            "════════════════════════════\n" +
                            "Seleccione una opción:"));

            switch (opcion) {
                case 1 -> registrarMateria();
                case 2 -> registrarTarea();
                case 3 -> JOptionPane.showMessageDialog(null, gestorMaterias.listarMaterias());
                case 4 -> JOptionPane.showMessageDialog(null, gestorMaterias.listarTareas());
                case 5 -> eliminarMateria();
                case 6 -> eliminarTarea();
                case 7 -> marcarTareaCompletada();
                case 8 -> agregarSesion();
                case 9 -> verRutina();
                case 10 -> reprogramarSesion();
                case 11 -> marcarCumplida();
                case 0 -> JOptionPane.showMessageDialog(null, "Sesión cerrada.");
                default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        } while (opcion != 0);
    }

    static void registrarMateria() {
        if (gestorMaterias.getListaMaterias().size() >= usuarioActivo.getCantidadMaterias()) {
            JOptionPane.showMessageDialog(null, "Ya registraste todas tus materias. " +
                    "Tienes un máximo de " + usuarioActivo.getCantidadMaterias() + " materias.");
            return;
        }

        String nombre = JOptionPane.showInputDialog("Nombre de la materia:");
        String dificultad = JOptionPane.showInputDialog("Nivel de dificultad (alto/medio/bajo):");
        double calificacion = Double.parseDouble(JOptionPane.showInputDialog("Calificación actual (0-10):"));
        double notaMinima = Double.parseDouble(JOptionPane.showInputDialog("Nota mínima personal (0-10):"));

        String resultado = gestorMaterias.registrarMateria(nombre, dificultad, calificacion, notaMinima);
        JOptionPane.showMessageDialog(null, resultado);
    }

    static void registrarTarea() {
        if (gestorMaterias.getListaMaterias().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero debes registrar al menos una materia.");
            return;
        }

        String nombreTarea = JOptionPane.showInputDialog("Nombre de la tarea:");

        // Mostrar lista de materias para elegir
        String listaMaterias = "Seleccione la materia asociada:\n\n";
        for (int i = 0; i < gestorMaterias.getListaMaterias().size(); i++) {
            Materia m = gestorMaterias.getListaMaterias().get(i);
            listaMaterias += (i + 1) + ". " + m.getNombreMateria() + "\n";
        }

        int indiceMateria = Integer.parseInt(JOptionPane.showInputDialog(listaMaterias)) - 1;

        if (indiceMateria < 0 || indiceMateria >= gestorMaterias.getListaMaterias().size()) {
            JOptionPane.showMessageDialog(null, "Número de materia no válido.");
            return;
        }

        String nombreMateria = gestorMaterias.getListaMaterias().get(indiceMateria).getNombreMateria();

        String fechaEntrega = "";
        while (true) {
            fechaEntrega = JOptionPane.showInputDialog("Fecha de entrega (dd/MM/yyyy):");
            String[] partes = fechaEntrega.split("/");

            if (partes.length == 3) {
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int anio = Integer.parseInt(partes[2]);

                if (dia >= 1 && dia <= 31 && mes >= 1 && mes <= 12 && anio >= 2026) {
                    LocalDate fechaIngresada = LocalDate.of(anio, mes, dia);
                    LocalDate hoy = LocalDate.now();

                    if (fechaIngresada.isBefore(hoy) || fechaIngresada.isEqual(hoy)) {
                        JOptionPane.showMessageDialog(null, "La fecha ya pasó, ingrese una fecha futura.");
                    } else {
                        break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fecha inválida, inténtelo de nuevo.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Fecha inválida, inténtelo de nuevo.");
            }
        }

        String resultado = gestorMaterias.registrarTarea(nombreTarea, fechaEntrega, nombreMateria);
        JOptionPane.showMessageDialog(null, resultado);
    }

    static void eliminarMateria() {
        if (gestorMaterias.getListaMaterias().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay materias registradas.");
            return;
        }

        String lista = "Seleccione la materia a eliminar:\n\n";
        for (int i = 0; i < gestorMaterias.getListaMaterias().size(); i++) {
            Materia m = gestorMaterias.getListaMaterias().get(i);
            lista += (i + 1) + ". " + m.getNombreMateria() + "\n";
        }

        int indice = Integer.parseInt(JOptionPane.showInputDialog(lista)) - 1;
        String resultado = gestorMaterias.eliminarMateria(indice);
        JOptionPane.showMessageDialog(null, resultado);
    }

    static void eliminarTarea() {
        if (gestorMaterias.getListaTareas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas registradas.");
            return;
        }

        String lista = "Seleccione la tarea a eliminar:\n\n";
        for (int i = 0; i < gestorMaterias.getListaTareas().size(); i++) {
            Tarea t = gestorMaterias.getListaTareas().get(i);
            lista += (i + 1) + ". " + t.getNombreTarea() +
                    " - Materia: " + t.getMateria().getNombreMateria() + "\n";
        }

        int indice = Integer.parseInt(JOptionPane.showInputDialog(lista)) - 1;
        String resultado = gestorMaterias.eliminarTarea(indice);
        JOptionPane.showMessageDialog(null, resultado);
    }

    static void marcarTareaCompletada() {
        if (gestorMaterias.getListaTareas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas registradas.");
            return;
        }

        String lista = "Seleccione la tarea a marcar como completada:\n\n";
        for (int i = 0; i < gestorMaterias.getListaTareas().size(); i++) {
            Tarea t = gestorMaterias.getListaTareas().get(i);
            lista += (i + 1) + ". " + t.getNombreTarea() +
                    " - Materia: " + t.getMateria().getNombreMateria() +
                    " - Estado: " + (t.isCompletada() ? "COMPLETADA" : "PENDIENTE") + "\n";
        }

        int indice = Integer.parseInt(JOptionPane.showInputDialog(lista)) - 1;
        String resultado = gestorMaterias.marcarTareaCompletada(indice);
        JOptionPane.showMessageDialog(null, resultado);
    }

    static void agregarSesion() {
        String opcionTarea = JOptionPane.showInputDialog(
                "¿Deseas crear una sesión con tarea o sin tarea?\n" +
                        "1. Con tarea\n" +
                        "2. Sin tarea");

        if ("1".equals(opcionTarea)) {
            agregarSesionConTarea();
        } else if ("2".equals(opcionTarea)) {
            agregarSesionSinTarea();
        } else {
            JOptionPane.showMessageDialog(null, "Opción no válida.");
        }
    }

    static void agregarSesionConTarea() {
        // Filtrar solo tareas pendientes
        List<Tarea> tareasPendientes = new ArrayList<>();
        for (Tarea t : gestorMaterias.getListaTareas()) {
            if (!t.isCompletada()) {
                tareasPendientes.add(t);
            }
        }

        if (tareasPendientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas pendientes para agregar a la rutina.");
            return;
        }

        String listaTareas = "Seleccione la tarea para la sesión:\n\n";
        for (int i = 0; i < tareasPendientes.size(); i++) {
            Tarea t = tareasPendientes.get(i);
            listaTareas += (i + 1) + ". " + t.getNombreTarea() +
                    " - Materia: " + t.getMateria().getNombreMateria() +
                    " - Fecha entrega: " + t.getFechaEntrega() +
                    " - Prioridad: " + t.getPrioridad() + "\n";
        }

        int indice = Integer.parseInt(JOptionPane.showInputDialog(listaTareas)) - 1;

        if (indice < 0 || indice >= tareasPendientes.size()) {
            JOptionPane.showMessageDialog(null, "Número de tarea no válido.");
            return;
        }

        Tarea tareaSeleccionada = tareasPendientes.get(indice);

        String fechaSesion = "";
        while (true) {
            fechaSesion = JOptionPane.showInputDialog("Fecha de la sesión (dd/MM/yyyy):");
            String[] partes = fechaSesion.split("/");

            if (partes.length == 3) {
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int anio = Integer.parseInt(partes[2]);

                if (dia >= 1 && dia <= 31 && mes >= 1 && mes <= 12 && anio >= 2026) {
                    LocalDate fechaSesionDate = LocalDate.of(anio, mes, dia);
                    LocalDate hoy = LocalDate.now();
                    LocalDate fechaEntrega = LocalDate.parse(tareaSeleccionada.getFechaEntrega(),
                            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    if (fechaSesionDate.isBefore(hoy)) {
                        JOptionPane.showMessageDialog(null, "La fecha ya pasó, ingrese una fecha futura.");
                    } else if (fechaSesionDate.isAfter(fechaEntrega)) {
                        JOptionPane.showMessageDialog(null, "La sesión no puede ser después de la fecha de entrega ("
                                + tareaSeleccionada.getFechaEntrega() + ").");
                    } else {
                        break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fecha inválida, inténtelo de nuevo.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Fecha inválida, inténtelo de nuevo.");
            }
        }

        String horaInicio = JOptionPane.showInputDialog("Hora inicio (HH:mm):");
        String horaFin = JOptionPane.showInputDialog("Hora fin (HH:mm):");

        for (RutinasEstudio s : gestorRutinas.getRutinaSemanal()) {
            if (s.getDia().equals(fechaSesion) && s.getHoraInicio().equals(horaInicio)) {
                JOptionPane.showMessageDialog(null, "Ya tienes una sesión en ese horario, elige otro.");
                return;
            }
        }

        RutinasEstudio sesion = new RutinasEstudio(fechaSesion, horaInicio, horaFin, 0, tareaSeleccionada);
        gestorRutinas.agregarSesion(sesion);
        JOptionPane.showMessageDialog(null, "Sesión agregada correctamente a la rutina.");
    }

    static void agregarSesionSinTarea() {
        String nombreSesion = JOptionPane.showInputDialog("Nombre de la sesión (ej: Repasar apuntes, Leer capítulo...):");

        String fechaSesion = "";
        while (true) {
            fechaSesion = JOptionPane.showInputDialog("Fecha de la sesión (dd/MM/yyyy):");
            String[] partes = fechaSesion.split("/");

            if (partes.length == 3) {
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int anio = Integer.parseInt(partes[2]);

                if (dia >= 1 && dia <= 31 && mes >= 1 && mes <= 12 && anio >= 2026) {
                    LocalDate fechaSesionDate = LocalDate.of(anio, mes, dia);
                    LocalDate hoy = LocalDate.now();

                    if (fechaSesionDate.isBefore(hoy)) {
                        JOptionPane.showMessageDialog(null, "La fecha ya pasó, ingrese una fecha futura.");
                    } else {
                        break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fecha inválida, inténtelo de nuevo.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Fecha inválida, inténtelo de nuevo.");
            }
        }

        String horaInicio = JOptionPane.showInputDialog("Hora inicio (HH:mm):");
        String horaFin = JOptionPane.showInputDialog("Hora fin (HH:mm):");

        for (RutinasEstudio s : gestorRutinas.getRutinaSemanal()) {
            if (s.getDia().equals(fechaSesion) && s.getHoraInicio().equals(horaInicio)) {
                JOptionPane.showMessageDialog(null, "Ya tienes una sesión en ese horario, elige otro.");
                return;
            }
        }

        RutinasEstudio sesion = new RutinasEstudio(fechaSesion, horaInicio, horaFin, 0, null);
        sesion.setNombreSesion(nombreSesion);
        gestorRutinas.agregarSesion(sesion);
        JOptionPane.showMessageDialog(null, "Sesión agregada correctamente a la rutina.");
    }

    static void verRutina() {
        if (gestorRutinas.getRutinaSemanal().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay sesiones en la rutina aún.");
            return;
        }
        String texto = "════════════════════════════\n" +
                "       RUTINA SEMANAL\n" +
                "════════════════════════════\n";
        for (RutinasEstudio s : gestorRutinas.getRutinaSemanal()) {
            String prioridad = (s.isEsDescanso() || s.getTarea() == null) ? "" : gestorRutinas.calcularPrioridad(s);
            texto += s + (prioridad.isEmpty() ? "" : " | Prioridad: " + prioridad) + "\n";
        }
        JOptionPane.showMessageDialog(null, texto);
    }

    static void reprogramarSesion() {
        if (gestorRutinas.getRutinaSemanal().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay sesiones en la rutina.");
            return;
        }

        String lista = "Seleccione la sesión a reprogramar:\n\n";
        for (int i = 0; i < gestorRutinas.getRutinaSemanal().size(); i++) {
            RutinasEstudio s = gestorRutinas.getRutinaSemanal().get(i);
            String descripcion;
            if (s.getTarea() == null) {
                descripcion = s.getNombreSesion();
            } else {
                descripcion = s.getTarea().getNombreTarea() +
                        " - Materia: " + s.getTarea().getMateria().getNombreMateria();
            }
            lista += (i + 1) + ". " + s.getDia() + " " + s.getHoraInicio() + "-" + s.getHoraFin() +
                    " | " + descripcion + "\n";
        }

        int indice = Integer.parseInt(JOptionPane.showInputDialog(lista)) - 1;

        if (indice < 0 || indice >= gestorRutinas.getRutinaSemanal().size()) {
            JOptionPane.showMessageDialog(null, "Número no válido.");
            return;
        }

        String nuevaFecha = "";
        while (true) {
            nuevaFecha = JOptionPane.showInputDialog("Nueva fecha (dd/MM/yyyy):");
            String[] partes = nuevaFecha.split("/");

            if (partes.length == 3) {
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int anio = Integer.parseInt(partes[2]);

                if (dia >= 1 && dia <= 31 && mes >= 1 && mes <= 12 && anio >= 2026) {
                    LocalDate fechaNueva = LocalDate.of(anio, mes, dia);
                    LocalDate hoy = LocalDate.now();

                    if (fechaNueva.isBefore(hoy)) {
                        JOptionPane.showMessageDialog(null, "La fecha ya pasó, ingrese una fecha futura.");
                    } else {
                        break;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Fecha inválida, inténtelo de nuevo.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Fecha inválida, inténtelo de nuevo.");
            }
        }

        String nuevaHoraInicio = JOptionPane.showInputDialog("Nueva hora inicio (HH:mm):");
        String nuevaHoraFin = JOptionPane.showInputDialog("Nueva hora fin (HH:mm):");

        for (int i = 0; i < gestorRutinas.getRutinaSemanal().size(); i++) {
            RutinasEstudio s = gestorRutinas.getRutinaSemanal().get(i);
            if (i != indice && s.getDia().equals(nuevaFecha) && s.getHoraInicio().equals(nuevaHoraInicio)) {
                JOptionPane.showMessageDialog(null, "Ya tienes una sesión en ese horario, elige otro.");
                return;
            }
        }

        RutinasEstudio sesion = gestorRutinas.getRutinaSemanal().get(indice);
        sesion.setDia(nuevaFecha);
        sesion.setHoraInicio(nuevaHoraInicio);
        sesion.setHoraFin(nuevaHoraFin);
        JOptionPane.showMessageDialog(null, "Sesión reprogramada correctamente.");
    }

    static void marcarCumplida() {
        if (gestorRutinas.getRutinaSemanal().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay sesiones en la rutina.");
            return;
        }

        String lista = "Seleccione la sesión a marcar como cumplida:\n\n";
        for (int i = 0; i < gestorRutinas.getRutinaSemanal().size(); i++) {
            RutinasEstudio s = gestorRutinas.getRutinaSemanal().get(i);
            String descripcion;
            if (s.getTarea() == null) {
                descripcion = "Sesión de estudio";
            } else {
                descripcion = s.getTarea().getMateria().getNombreMateria() +
                        " - Tarea: " + s.getTarea().getNombreTarea();
            }
            lista += (i + 1) + ". " + s.getDia() + " " + s.getHoraInicio() + "-" + s.getHoraFin() +
                    " | " + descripcion +
                    " | " + (s.isCumplida() ? "CUMPLIDA" : "PENDIENTE") + "\n";
        }

        int indice = Integer.parseInt(JOptionPane.showInputDialog(lista)) - 1;

        if (indice < 0 || indice >= gestorRutinas.getRutinaSemanal().size()) {
            JOptionPane.showMessageDialog(null, "Número no válido.");
            return;
        }

        RutinasEstudio sesion = gestorRutinas.getRutinaSemanal().get(indice);
        if (sesion.isCumplida()) {
            JOptionPane.showMessageDialog(null, "Esta sesión ya estaba marcada como cumplida.");
        } else {
            sesion.setCumplida(true);
            JOptionPane.showMessageDialog(null, "Sesión marcada como cumplida.");
        }
    }
}