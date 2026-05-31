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
        String nombreTarea = JOptionPane.showInputDialog("Nombre de la tarea:");
        String nombreMateria = JOptionPane.showInputDialog("Materia asociada:");

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
                    " - Estado: " + (t.isCompletada() ? "COMPLETADA ✅" : "PENDIENTE ⏳") + "\n";
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
        if (gestorMaterias.getListaTareas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay tareas registradas.");
            return;
        }

        String listaTareas = "Seleccione la tarea para la sesión:\n\n";
        for (int i = 0; i < gestorMaterias.getListaTareas().size(); i++) {
            Tarea t = gestorMaterias.getListaTareas().get(i);
            listaTareas += (i + 1) + ". " + t.getNombreTarea() +
                    " - Materia: " + t.getMateria().getNombreMateria() +
                    " - Prioridad: " + t.getPrioridad() + "\n";
        }

        int indice = Integer.parseInt(JOptionPane.showInputDialog(listaTareas)) - 1;

        if (indice < 0 || indice >= gestorMaterias.getListaTareas().size()) {
            JOptionPane.showMessageDialog(null, "Número de tarea no válido.");
            return;
        }

        Tarea tareaSeleccionada = gestorMaterias.getListaTareas().get(indice);

        String dia = JOptionPane.showInputDialog("Día (Lunes/Martes/...):");
        String horaInicio = JOptionPane.showInputDialog("Hora inicio (HH:mm):");
        String horaFin = JOptionPane.showInputDialog("Hora fin (HH:mm):");

        RutinasEstudio sesion = new RutinasEstudio(dia, horaInicio, horaFin, 0, tareaSeleccionada);
        gestorRutinas.agregarSesion(sesion);
        JOptionPane.showMessageDialog(null, "Sesión agregada correctamente a la rutina.");
    }

    static void agregarSesionSinTarea() {
        String dia = JOptionPane.showInputDialog("Día (Lunes/Martes/...):");
        String horaInicio = JOptionPane.showInputDialog("Hora inicio (HH:mm):");
        String horaFin = JOptionPane.showInputDialog("Hora fin (HH:mm):");

        RutinasEstudio sesion = new RutinasEstudio(dia, horaInicio, horaFin, 0, null);
        gestorRutinas.agregarSesion(sesion);
        JOptionPane.showMessageDialog(null, "Sesión de estudio agregada correctamente a la rutina.");
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
        String nombreMateria = JOptionPane.showInputDialog("Materia a reprogramar:");
        String nuevoDia = JOptionPane.showInputDialog("Nuevo día:");
        String nuevaHoraInicio = JOptionPane.showInputDialog("Nueva hora inicio (HH:mm):");
        String nuevaHoraFin = JOptionPane.showInputDialog("Nueva hora fin (HH:mm):");

        String resultado = gestorRutinas.reprogramarSesion(nombreMateria, nuevoDia, nuevaHoraInicio, nuevaHoraFin);
        JOptionPane.showMessageDialog(null, resultado);
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