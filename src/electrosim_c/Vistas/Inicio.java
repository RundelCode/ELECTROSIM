package electrosim_c.Vistas;

import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;

import java.net.URL;

public class Inicio {

    private VBox root;

    public Inicio(
            Runnable goToSimulador,
            Runnable goToBuscador,
            Runnable goToTeoria
    ) {

        root
                = new VBox(
                        15
                );

        root.setAlignment(
                Pos.TOP_CENTER
        );

        root.getStyleClass().add(
                "contenedor"
        );

        ImageView logo
                = crearLogo();

        Label titulo
                = new Label(
                        "ELECTROSIM"
                );

        titulo.getStyleClass().add(
                "titulo"
        );

        titulo.setStyle(
                "-fx-font-size: 40px;"
        );

        Label descripcion
                = new Label(
                        "Simulador interactivo de la Ley de Coulomb para visualizar y analizar fuerzas eléctricas entre cargas."
                );

        descripcion.getStyleClass().add(
                "texto"
        );

        descripcion.setWrapText(
                true
        );

        Button opcion1
                = new Button(
                        "NUEVA SIMULACIÓN"
                );

        Button opcion2
                = new Button(
                        "REGISTROS"
                );

        Button opcion3
                = new Button(
                        "FUNDAMENTOS TEORICOS"
                );

        Button opcion4
                = new Button(
                        "SALIR"
                );

        opcion1.setOnAction(
                e -> goToSimulador.run()
        );

        opcion2.setOnAction(
                e -> goToBuscador.run()
        );

        opcion3.setOnAction(
                e -> goToTeoria.run()
        );

        opcion4.setOnAction(
                e -> System.exit(
                        0
                )
        );

        for (Button btn
                : new Button[]{
                    opcion1,
                    opcion2,
                    opcion3,
                    opcion4
                }) {

            btn.prefWidthProperty().bind(
                    root.widthProperty()
                            .multiply(
                                    0.7
                            )
            );
        }

        root.getChildren().addAll(
                logo,
                titulo,
                descripcion,
                opcion1,
                opcion2,
                opcion3,
                opcion4
        );
    }

    private ImageView crearLogo() {

        URL logoUrl
                = getClass().getResource(
                        "/electrosim_c/Recursos/Logo.png"
                );

        ImageView logo
                = new ImageView();

        if (logoUrl != null) {

            logo.setImage(
                    new Image(
                            logoUrl.toExternalForm()
                    )
            );
        }

        logo.setPreserveRatio(
                true
        );

        logo.setFitWidth(
                75
        );

        return logo;
    }

    public VBox getView() {

        return root;
    }
}
