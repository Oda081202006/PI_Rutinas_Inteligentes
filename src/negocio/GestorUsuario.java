package negocio;

import modelo.Usuario;
import java.util.ArrayList;

public class GestorUsuario {
    private ArrayList<Usuario> usuarios;

    public GestorUsuario() {
        usuarios = new ArrayList<>();
    }

    public String registrarUsuario(String nombre, String correo, String carrera, int semestre, String contrasena) {
        if (nombre.isEmpty() || correo.isEmpty() || carrera.isEmpty() || contrasena.isEmpty()) {
            return "Error: todos los campos son obligatorios.";
        }

        if (!correo.contains("@") || !correo.contains(".")) {
            return "Error: formato de correo inválido.";
        }

        if (contrasena.length() < 8) {
            return "Error: la contraseña debe tener mínimo 8 caracteres.";
        }

        if (semestre < 1 || semestre > 10) {
            return "Error: el semestre debe estar entre 1 y 10.";
        }

        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) {
                return "Error: el correo ya está registrado.";
            }
        }

        usuarios.add(new Usuario(nombre, correo, carrera, semestre, contrasena));
        return "Registro exitoso. Ahora puede iniciar sesión.";
    }

    public String iniciarSesion(String correo, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) {

                if (usuario.isBloqueado()) {
                    return "Usuario bloqueado por superar los 3 intentos fallidos.";
                }

                if (usuario.getContrasena().equals(contrasena)) {
                    usuario.reiniciarIntentos();
                    return "Acceso permitido. Bienvenido/a " + usuario.getNombre();
                } else {
                    usuario.aumentarIntentos();
                    return "Usuario o contraseña incorrectos. Intentos fallidos: " + usuario.getIntentosFallidos();
                }
            }
        }

        return "Usuario no encontrado.";
    }

    public String listarUsuarios() {
        if (usuarios.isEmpty()) {
            return "No hay usuarios registrados.";
        }

        String texto = "";

        for (Usuario usuario : usuarios) {
            texto += usuario.mostrarDatos() + "\n-------------------\n";
        }

        return texto;
    }
}
