package negocio;

import modelo.Usuario;

public class GestorUsuario {

    private Usuario[] usuarios;
    private int indice;

    public GestorUsuario() {

        usuarios = new Usuario[100];
        indice = 0;

    }

    public boolean correoExiste(String correo) {

        for (int i = 0; i < indice; i++) {

            if (usuarios[i].getCorreoElectronico().equalsIgnoreCase(correo)) {

                return true;

            }

        }

        return false;

    }

    public String calcularPerfil(int materias, int horas) {

        int promedio = horas / materias;

        if (promedio >= 5) {

            return "EFICIENTE";

        } else if (promedio >= 3) {

            return "RESTRINGIDO";

        } else {

            return "SATURADO";

        }

    }

    public String registrarUsuario(Usuario usuario) {

        // Validar correo repetido
        if (correoExiste(usuario.getCorreoElectronico())) {

            return "Error: el correo ya está registrado.";

        }

        // Validar contraseña
        if (usuario.getContrasena().length() < 8) {

            return "Error: la contraseña debe tener mínimo 8 caracteres.";

        }

        // Validar cantidad de materias
        if (usuario.getCantidadMaterias() <= 0) {

            return "Error: la cantidad de materias debe ser mayor a cero.";

        }

        // Validar horas
        if (usuario.getHorasDisponibles() < 0) {

            return "Error: las horas disponibles no pueden ser negativas.";

        }

        // Calcular perfil académico
        String perfil = calcularPerfil(
                usuario.getCantidadMaterias(),
                usuario.getHorasDisponibles()
        );

        // Guardar perfil
        usuario.setPerfilViabilidad(perfil);

        // Guardar usuario en el arreglo
        usuarios[indice] = usuario;

        indice++;

        return "Usuario registrado correctamente. Perfil: " + perfil;

    }

    public Usuario iniciarSesion(String correo, String contrasena) {

        for (int i = 0; i < indice; i++) {

            if (usuarios[i].getCorreoElectronico().equalsIgnoreCase(correo)) {

                // Verificar si la cuenta está bloqueada
                if (usuarios[i].isCuentaBloqueada()) {

                    System.out.println("Cuenta bloqueada temporalmente.");

                    return null;

                }

                // Verificar contraseña correcta
                if (usuarios[i].getContrasena().equals(contrasena)) {

                    usuarios[i].setIntentosFallidos(0);

                    return usuarios[i];

                } else {

                    // Aumentar intentos fallidos
                    usuarios[i].setIntentosFallidos(
                            usuarios[i].getIntentosFallidos() + 1
                    );

                    // Bloquear cuenta
                    if (usuarios[i].getIntentosFallidos() >= 3) {

                        usuarios[i].setCuentaBloqueada(true);

                        System.out.println("Cuenta bloqueada temporalmente.");

                    }

                }

            }

        }

        return null;

    }

}