package modelo;

public class Usuario {
    private String nombre;
    private String correo;
    private String carrera;
    private int semestre;
    private String contrasena;
    private int intentosFallidos;
    private boolean bloqueado;

    public Usuario(String nombre, String correo, String carrera, int semestre, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.carrera = carrera;
        this.semestre = semestre;
        this.contrasena = contrasena;
        this.intentosFallidos = 0;
        this.bloqueado = false;
    }

    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getCarrera() { return carrera; }
    public int getSemestre() { return semestre; }
    public String getContrasena() { return contrasena; }
    public int getIntentosFallidos() { return intentosFallidos; }
    public boolean isBloqueado() { return bloqueado; }

    public void aumentarIntentos() {
        intentosFallidos++;
        if (intentosFallidos >= 3) {
            bloqueado = true;
        }
    }

    public void reiniciarIntentos() {
        intentosFallidos = 0;
    }

    public String mostrarDatos() {
        return "Nombre: " + nombre +
                "\nCorreo: " + correo +
                "\nCarrera: " + carrera +
                "\nSemestre: " + semestre +
                "\nEstado: " + (bloqueado ? "Bloqueado" : "Activo");
    }
}