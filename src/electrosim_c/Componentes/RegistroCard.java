package electrosim_c.Componentes;

import javafx.geometry.Insets;

import javafx.scene.control.Label;

import javafx.scene.layout.VBox;

import modelo.Registro;

public class RegistroCard extends VBox {

    private final Registro registro;

    public RegistroCard(
            Registro registro
    ) {

        this.registro = registro;

        getStyleClass().add(
                "registro-card"
        );

        setSpacing(
                8
        );

        setPadding(
                new Insets(
                        15
                )
        );

        Label usuario
                = new Label(
                        registro.getUsuario()
                );

        usuario.getStyleClass().add(
                "registro-title"
        );

        Label fecha
                = new Label(
                        registro.getFechaTexto()
                );

        fecha.getStyleClass().add(
                "registro-date"
        );

        Label titulo
                = new Label(
                        registro.getDescripcion()
                );

        titulo.getStyleClass().add(
                "registro-desc"
        );

        getChildren().addAll(
                usuario,
                fecha,
                titulo
        );
    }

    public Registro getRegistro() {

        return registro;
    }
}
