package electrosim_c.Vistas;

import electrosim_c.Componentes.Header;
import electrosim_c.Componentes.TheoryParser;

import javafx.geometry.Insets;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Teoria {

    private BorderPane root;

    public Teoria(
            Runnable goBack
    ) {

        // Construimos el contenedor principal de la vista teórica.
        root = new BorderPane();

        root.getStyleClass().add(
                "contenedor"
        );

        // Agregamos navegación para regresar al flujo principal.
        Header header = new Header(
                "Fundamentos teóricos",
                goBack
        );

        // Delegamos la interpretación del contenido a un parser especializado.
        TheoryParser parser = new TheoryParser();

        // Generamos dinámicamente la interfaz a partir del archivo teórico.
        VBox contenido = parser.parse();

        // Definimos separación visual entre bloques de contenido.
        contenido.setSpacing(
                20
        );

        // Agregamos márgenes para mejorar la lectura del material.
        contenido.setPadding(
                new Insets(
                        30,
                        20,
                        30,
                        20
                )
        );

        // Permitimos que cada bloque aproveche el ancho disponible.
        contenido.setFillWidth(
                true
        );

        // Ubicamos el encabezado en la parte superior de la vista.
        root.setTop(
                header.getView()
        );

        // Mostramos el contenido teórico en la zona principal.
        root.setCenter(
                contenido
        );
    }

    // Exponemos la vista para integrarla dentro de la aplicación.
    public BorderPane getView() {

        return root;
    }
}
