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

        // Construimos el contenedor principal del menú inicial.
        root = new VBox(15);

        root.setAlignment(
                Pos.TOP_CENTER
        );

        root.getStyleClass().add(
                "contenedor"
        );

        // Cargamos los elementos visuales que representan la identidad de la aplicación.
        ImageView logo = crearLogo();

        Label titulo = new Label(
                "ELECTROSIM"
        );

        titulo.getStyleClass().add(
                "titulo"
        );

        titulo.setStyle(
                "-fx-font-size: 40px;"
        );

        // Presentamos una descripción breve para contextualizar al usuario.
        Label descripcion = new Label(
                "Simulador interactivo de la Ley de Coulomb para visualizar y analizar fuerzas eléctricas entre cargas."
        );

        descripcion.getStyleClass().add(
                "texto"
        );

        descripcion.setWrapText(
                true
        );

        // Cada opción representa uno de los módulos principales del sistema.
        Button opcion1 = new Button(
                "NUEVA SIMULACIÓN"
        );

        Button opcion2 = new Button(
                "REGISTROS"
        );

        Button opcion3 = new Button(
                "FUNDAMENTOS TEORICOS"
        );

        Button opcion4 = new Button(
                "SALIR"
        );

        // Delegamos la navegación al controlador principal de la aplicación.
        opcion1.setOnAction(
                e -> goToSimulador.run()
        );

        opcion2.setOnAction(
                e -> goToBuscador.run()
        );

        opcion3.setOnAction(
                e -> goToTeoria.run()
        );

        // Cerramos completamente la aplicación cuando el usuario lo solicita.
        opcion4.setOnAction(
                e -> System.exit(0)
        );

        // Unificamos el ancho de los botones para mantener equilibrio visual.
        for (Button btn
                : new Button[]{
                    opcion1,
                    opcion2,
                    opcion3,
                    opcion4
                }) {

            btn.prefWidthProperty().bind(
                    root.widthProperty()
                            .multiply(0.7)
            );
        }

        // Ensamblamos todos los componentes que forman la pantalla inicial.
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

        // Buscamos el recurso local para evitar rutas dependientes del entorno.
        URL logoUrl = getClass().getResource(
                "/electrosim_c/Recursos/Logo.png"
        );

        ImageView logo = new ImageView();

        // Solo cargamos la imagen si el recurso fue encontrado correctamente.
        if (logoUrl != null) {

            logo.setImage(
                    new Image(
                            logoUrl.toExternalForm()
                    )
            );
        }

        // Ajustamos el logo manteniendo sus proporciones originales.
        logo.setPreserveRatio(
                true
        );

        logo.setFitWidth(
                75
        );

        return logo;
    }

    // Expone la vista para integrarla dentro del flujo principal.
    public VBox getView() {

        return root;
    }
}
