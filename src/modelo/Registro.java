package modelo;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

public class Registro {

    // Identificador único usado para rastrear este experimento.
    private String idRegistro;

    // Nombre físico del directorio donde se almacena el registro.
    private String nombreArchivo;

    // Usuario responsable de la simulación registrada.
    private String usuario;

    // Nombre descriptivo asignado al experimento.
    private String titulo;

    // Momento exacto en que se generó el registro.
    private LocalDateTime fecha;

    // Observaciones o contexto agregado por el usuario.
    private String descripcion;

    // Conjunto de gráficas asociadas a esta simulación.
    private ArrayList<Grafica> graficas;

    public Registro(
            String usuario,
            String titulo,
            String descripcion,
            ArrayList<Grafica> graficas
    ) {

        // Guardamos la información descriptiva del experimento.
        this.usuario
                = usuario;

        this.titulo
                = titulo;

        this.descripcion
                = descripcion;

        this.graficas
                = graficas;

        // Registramos automáticamente la fecha de creación.
        this.fecha
                = LocalDateTime.now();
    }

    // Retornamos el identificador interno del registro.
    public String getIdRegistro() {

        return idRegistro;
    }

    // Permitimos asociar un identificador persistente.
    public void setIdRegistro(
            String idRegistro
    ) {

        this.idRegistro
                = idRegistro;
    }

    // Retornamos el usuario que realizó la simulación.
    public String getUsuario() {

        return usuario;
    }

    // Permitimos actualizar el autor del registro.
    public void setUsuario(
            String usuario
    ) {

        this.usuario
                = usuario;
    }

    // Retornamos el nombre del directorio asociado al registro.
    public String getNombreArchivo() {

        return nombreArchivo;
    }

    // Permitimos actualizar la referencia física del almacenamiento.
    public void setNombreArchivo(
            String nombreArchivo
    ) {

        this.nombreArchivo
                = nombreArchivo;
    }

    // Retornamos el título descriptivo del experimento.
    public String getTitulo() {

        return titulo;
    }

    // Permitimos actualizar el nombre del experimento.
    public void setTitulo(
            String titulo
    ) {

        this.titulo
                = titulo;
    }

    // Retornamos la fecha real de creación del registro.
    public LocalDateTime getFecha() {

        return fecha;
    }

    // Permitimos reconstruir fechas al cargar registros antiguos.
    public void setFecha(
            LocalDateTime fecha
    ) {

        this.fecha
                = fecha;
    }

    // Formateamos la fecha para mostrarla de forma legible al usuario.
    public String getFechaTexto() {

        return fecha.format(
                DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy HH:mm"
                )
        );
    }

    // Retornamos las observaciones asociadas al experimento.
    public String getDescripcion() {

        return descripcion;
    }

    // Permitimos actualizar la descripción del registro.
    public void setDescripcion(
            String descripcion
    ) {

        this.descripcion
                = descripcion;
    }

    // Retornamos todas las gráficas generadas por la simulación.
    public ArrayList<Grafica> getGraficas() {

        return graficas;
    }

    // Permitimos reemplazar el conjunto de resultados gráficos.
    public void setGraficas(
            ArrayList<Grafica> graficas
    ) {

        this.graficas
                = graficas;
    }

    @Override
    public String toString() {

        // Generamos una representación breve para listados y búsquedas.
        return usuario
                + " - "
                + getFechaTexto();
    }
}
