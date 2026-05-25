package interfaz;

import negocio.GestorMateriasTareas;
import javax.swing.JOptionPane;

public class MainUsuarios {
    public static void main(String[] args) {
        GestorMateriasTareas sistema = new GestorMateriasTareas();
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "SISTEMA DE RUTINAS INTELIGENTES\n" +
                            "1. Registrar usuario\n" +
                            "2. Iniciar sesión\n" +
                            "3. Listar usuarios\n" +
                            "4. Salir\n" +
                            "Seleccione una opción:"
            ));

            switch (opcion) {
                case 1:
                    String nombre = JOptionPane.showInputDialog("Ingrese nombre completo:");
                    String correo = JOptionPane.showInputDialog("Ingrese correo:");
                    String carrera = JOptionPane.showInputDialog("Ingrese carrera:");
                    int semestre = Integer.parseInt(JOptionPane.showInputDialog("Ingrese semestre:"));
                    String contrasena = JOptionPane.showInputDialog("Ingrese contraseña:");

                    JOptionPane.showMessageDialog(null,
                            sistema.registrarUsuario(nombre, correo, carrera, semestre, contrasena));
                    break;

                case 2:
                    String correoLogin = JOptionPane.showInputDialog("Ingrese correo:");
                    String contrasenaLogin = JOptionPane.showInputDialog("Ingrese contraseña:");

                    JOptionPane.showMessageDialog(null,
                            sistema.iniciarSesion(correoLogin, contrasenaLogin));
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, sistema.listarUsuarios());
                    break;

                case 4:
                    JOptionPane.showMessageDialog(null, "Sistema finalizado.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }

        } while (opcion != 4);
    }
}