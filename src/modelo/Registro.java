package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Registro {

    private String idRegistro;
    private String usuario;
    private LocalDateTime fecha;
    private String descripcion;
    private ArrayList<Grafica> graficas;

    public Registro(String usuario, String descripcion, ArrayList<Grafica> graficas) {
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.graficas = graficas;
        this.fecha = LocalDateTime.now();
    }

    public void crearEntrada(String descripcion) {
    }

    public void guardar() {
    }

    public String consultarHistorial(LocalDateTime fecha) {
        return null;
    }

    public String getIdRegistro() { return idRegistro; }
    public void setIdRegistro(String idRegistro) { this.idRegistro = idRegistro; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public ArrayList<Grafica> getGraficas() { return graficas; }
    public void setGraficas(ArrayList<Grafica> graficas) { this.graficas = graficas; }
}