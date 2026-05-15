package modelo;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class Registro {

    private String idRegistro;

    private String nombreArchivo;

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

        this.idRegistro
                = idRegistro;
    }

    public String getUsuario() {

        return usuario;
    }

    public void setUsuario(
            String usuario
    ) {

        this.usuario
                = usuario;
    }

    public String getNombreArchivo() {

        return nombreArchivo;
    }

    public void setNombreArchivo(
            String nombreArchivo
    ) {

        this.nombreArchivo
                = nombreArchivo;
    }

    public String getTitulo() {

        return titulo;
    }

    public void setTitulo(
            String titulo
    ) {

        this.titulo
                = titulo;
    }

    public LocalDateTime getFecha() {

        return fecha;
    }

    public void setFecha(
            LocalDateTime fecha
    ) {

        this.fecha
                = fecha;
    }

    public String getFechaTexto() {

        return fecha.format(
                DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy HH:mm"
                )
        );
    }

    public String getDescripcion() {

        return descripcion;
    }

    public void setDescripcion(
            String descripcion
    ) {

        this.descripcion
                = descripcion;
    }

    public ArrayList<Grafica> getGraficas() {

        return graficas;
    }

    public void setGraficas(
            ArrayList<Grafica> graficas
    ) {

        this.graficas
                = graficas;
    }

    @Override
    public String toString() {

        return usuario
                + " - "
                + getFechaTexto();
    }
}
