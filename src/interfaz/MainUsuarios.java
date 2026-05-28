package interfaz;

import modelo.Materia;
import modelo.Tarea;
import modelo.RutinasEstudio;
import modelo.Usuario;
import negocio.GestorMateriasTareas;
import negocio.GestorRutinas;
import negocio.GestorUsuario;
import javax.swing.JOptionPane;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    // ─── REGISTRO ───────────────────────────────────────
    static void registrarUsuario() {
        String
                nombre = JOptionPane.showInputDialog("Nombre completo:");
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

    // ─── INICIO DE SESIÓN ────────────────────────────────
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

    // ─── MENÚ PRINCIPAL ──────────────────────────────────
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
                            "5. Agregar sesión a rutina\n" +
                            "6. Ver rutina semanal\n" +
                            "7. Reprogramar sesión\n" +
                            "8. Marcar sesión como cumplida\n" +
                            "0. Cerrar sesión\n" +
                            "════════════════════════════\n" +
                            "Seleccione una opción:"));

            switch (opcion) {
                case 1 -> registrarMateria();
                case 2 -> registrarTarea();
                case 3 -> JOptionPane.showMessageDialog(null, gestorMaterias.listarMaterias());
                case 4 -> JOptionPane.showMessageDialog(null, gestorMaterias.listarTareas());
                case 5 -> agregarSesion();
                case 6 -> verRutina();
                case 7 -> reprogramarSesion();
                case 8 -> marcarCumplida();
                case 0 -> JOptionPane.showMessageDialog(null, "Sesión cerrada.");
                default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        } while (opcion != 0);
    }

    // ─── REGISTRAR MATERIA ───────────────────────────────
    static void registrarMateria() {
        String nombre = JOptionPane.showInputDialog("Nombre de la materia:");
        String dificultad = JOptionPane.showInputDialog("Nivel de dificultad (alto/medio/bajo):");
        double calificacion = Double.parseDouble(JOptionPane.showInputDialog("Calificación actual (0-10):"));
        double notaMinima = Double.parseDouble(JOptionPane.showInputDialog("Nota mínima personal (0-10):"));

        String resultado = gestorMaterias.registrarMateria(nombre, dificultad, calificacion, notaMinima);
        JOptionPane.showMessageDialog(null, resultado);
    }

    // ─── REGISTRAR TAREA ─────────────────────────────────
    static void registrarTarea() {
        String nombreTarea = JOptionPane.showInputDialog("Nombre de la tarea:");
        String nombreMateria = JOptionPane.showInputDialog("Materia asociada:");
        String fechaEntrega = JOptionPane.showInputDialog("Fecha de entrega (dd/MM/yyyy):");

        String resultado = gestorMaterias.registrarTarea(nombreTarea, fechaEntrega, nombreMateria);
        JOptionPane.showMessageDialog(null, resultado);
    }

    // ─── AGREGAR SESIÓN ──────────────────────────────────
    static void agregarSesion() {
        if (gestorMaterias.getListaTareas().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero debes registrar al menos una tarea.");
            return;
        }

        String nombreMateria = JOptionPane.showInputDialog("Nombre de la materia para la sesión:");
        Tarea tareaEncontrada = null;

        for (Tarea t : gestorMaterias.getListaTareas()) {
            if (t.getMateria().getNombreMateria().equalsIgnoreCase(nombreMateria)) {
                tareaEncontrada = t;
                break;
            }
        }

        if (tareaEncontrada == null) {
            JOptionPane.showMessageDialog(null, "No se encontró una tarea para esa materia.");
            return;
        }

        String dia = JOptionPane.showInputDialog("Día (Lunes/Martes/...):");
        String horaInicio = JOptionPane.showInputDialog("Hora inicio (HH:mm):");
        String horaFin = JOptionPane.showInputDialog("Hora fin (HH:mm):");

        for (RutinasEstudio rutina : gestorRutinas.getRutinaSemanal()) {
            if(rutina.getDia().equals(dia) && rutina.getHoraFin().equals(horaInicio) && tareaEncontrada.getPrioridad().equals("alta")){
                LocalTime inicio = LocalTime.parse(horaInicio);
                LocalTime fin = LocalTime.parse(horaFin);
                LocalTime inicioMas15 = inicio.plusMinutes(15);
                LocalTime finMas15 = fin.plusMinutes(15);
                String inicioString = inicioMas15.format(DateTimeFormatter.ofPattern("HH:mm"));
                String finString = finMas15.format(DateTimeFormatter.ofPattern("HH:mm"));

                horaInicio = inicioString;
                horaFin = finString;
            }

        }


        RutinasEstudio sesion = new RutinasEstudio(dia, horaInicio, horaFin, 1, tareaEncontrada);
        gestorRutinas.agregarSesion(sesion);
        JOptionPane.showMessageDialog(null, "Sesión agregada correctamente a la rutina.");
    }

    // ─── VER RUTINA ──────────────────────────────────────
    static void verRutina() {
        if (gestorRutinas.getRutinaSemanal().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay sesiones en la rutina aún.");
            return;
        }
        String texto = "════════════════════════════\n" +
                "       RUTINA SEMANAL\n" +
                "════════════════════════════\n";
        for (RutinasEstudio s : gestorRutinas.getRutinaSemanal()) {
            String prioridad = gestorRutinas.calcularPrioridad(s);
            texto += s + (s.isEsDescanso() ? "" : " | Prioridad: " + prioridad) + "\n";
        }
        JOptionPane.showMessageDialog(null, texto);
    }

    // ─── REPROGRAMAR SESIÓN ──────────────────────────────
    static void reprogramarSesion() {
        String nombreMateria = JOptionPane.showInputDialog("Materia a reprogramar:");
        String nuevoDia = JOptionPane.showInputDialog("Nuevo día:");
        String nuevaHoraInicio = JOptionPane.showInputDialog("Nueva hora inicio (HH:mm):");
        String nuevaHoraFin = JOptionPane.showInputDialog("Nueva hora fin (HH:mm):");

        String resultado = gestorRutinas.reprogramarSesion(nombreMateria, nuevoDia, nuevaHoraInicio, nuevaHoraFin);
        JOptionPane.showMessageDialog(null, resultado);
    }

    // ─── MARCAR CUMPLIDA ─────────────────────────────────
    static void marcarCumplida() {
        String nombreMateria = JOptionPane.showInputDialog("Materia de la sesión cumplida:");
        String resultado = gestorRutinas.marcarCumplida(nombreMateria);
        JOptionPane.showMessageDialog(null, resultado);
    }
}