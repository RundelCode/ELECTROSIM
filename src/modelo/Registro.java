package modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Registro {

    private String idRegistro;
    private String usuario;
    private String titulo;
    private LocalDateTime fecha;
    private String descripcion;
    private ArrayList<Grafica> graficas;

    public Registro(
            String usuario,
            String titulo,
            String descripcion,
            ArrayList<Grafica> graficas
    ) {

        this.usuario
                = usuario;

        this.titulo
                = titulo;

        this.descripcion
                = descripcion;

        this.graficas
                = graficas;

        this.fecha
                = LocalDateTime.now();
    }

    public String getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(
            String idRegistro
    ) {
        this.idRegistro = idRegistro;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<Grafica> getGraficas() {
        return graficas;
    }
}
