package electrosim_c.Componentes;

import javafx.geometry.Insets;

import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

import modelo.Registro;

public class RegistroCard extends VBox {

    // Conservamos el registro asociado para poder acceder a sus datos posteriormente.
    private final Registro registro;

    // Construimos la tarjeta visual que representa un experimento guardado.
    public RegistroCard(
            Registro registro
    ) {

        // Guardamos el registro recibido como fuente principal de información.
        this.registro = registro;

        // Aplicamos el estilo visual base de las tarjetas del historial.
        getStyleClass().add(
                "registro-card"
        );

        // Definimos una separación cómoda entre los bloques de información.
        setSpacing(
                8
        );

        // Añadimos espacio interno para mejorar la lectura del contenido.
        setPadding(
                new Insets(
                        15
                )
        );

        // Mostramos el nombre del usuario que generó este registro.
        Label usuario
                = new Label(
                        registro.getUsuario()
                );

        // Aplicamos el estilo visual principal del encabezado de la tarjeta.
        usuario.getStyleClass().add(
                "registro-title"
        );

        // Mostramos la fecha en la que fue almacenado el experimento.
        Label fecha
                = new Label(
                        registro.getFechaTexto()
                );

        // Aplicamos un estilo secundario para diferenciar la información temporal.
        fecha.getStyleClass().add(
                "registro-date"
        );

        // Mostramos la descripción que resume el contenido del experimento.
        Label titulo
                = new Label(
                        registro.getDescripcion()
                );

        // Aplicamos el estilo del contenido descriptivo dentro del historial.
        titulo.getStyleClass().add(
                "registro-desc"
        );

        // Incorporamos toda la información visual a la tarjeta final.
        getChildren().addAll(
                usuario,
                fecha,
                titulo
        );
    }

    // Retornamos el registro asociado a esta tarjeta para futuras interacciones.
    public Registro getRegistro() {

        return registro;
    }
}
