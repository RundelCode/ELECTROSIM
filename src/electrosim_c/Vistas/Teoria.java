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

        root
                = new BorderPane();

        root.getStyleClass().add(
                "contenedor"
        );

        Header header
                = new Header(
                        "Fundamentos teóricos",
                        goBack
                );

        TheoryParser parser
                = new TheoryParser();

        VBox contenido
                = parser.parse();

        contenido.setSpacing(
                20
        );

        contenido.setPadding(
                new Insets(
                        30,
                        20,
                        30,
                        20
                )
        );

        contenido.setFillWidth(
                true
        );

        root.setTop(
                header.getView()
        );

        root.setCenter(
                contenido
        );
    }

    public BorderPane getView() {

        return root;
    }
}
