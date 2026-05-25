package interfaz;

import negocio.GestorUsuario;
import javax.swing.JOptionPane;

public class MainUsuarios {
    public static void main(String[] args) {
        GestorUsuario gestorUsuario = new GestorUsuario();
        int opcion;

        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(
                    "GESTIÓN DE USUARIOS\n" +
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
                            gestorUsuario.registrarUsuario(nombre, correo, carrera, semestre, contrasena));
                    break;

                case 2:
                    String correoLogin = JOptionPane.showInputDialog("Ingrese correo:");
                    String contrasenaLogin = JOptionPane.showInputDialog("Ingrese contraseña:");

                    JOptionPane.showMessageDialog(null,
                            gestorUsuario.iniciarSesion(correoLogin, contrasenaLogin));
                    break;

                case 3:
                    JOptionPane.showMessageDialog(null, gestorUsuario.listarUsuarios());
                    break;

                case 4:
                    JOptionPane.showMessageDialog(null, "Saliendo del módulo de usuarios.");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
            }

        } while (opcion != 4);
    }
}
//PARTE NICOLL, CUANDO TERMINEN TODAS TOCA LLAMAR A TODOS LOS METODOS Y HACER UN SOLO MENÚ