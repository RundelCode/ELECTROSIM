package electrosim_c.Componentes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

public class Header {

    private VBox root;

    public Header(String texto, Runnable goBack) {

        Label titulo = new Label("ELECTROSIM");
        titulo.getStyleClass().add("titulo");

        Button back = new Button("Volver");
        back.setOnAction(e -> goBack.run());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topBar = new HBox(20, titulo, spacer, back);
        topBar.setMaxWidth(Double.MAX_VALUE);
        topBar.setAlignment(Pos.CENTER_LEFT);

        Label subtitulo = new Label(texto);
        subtitulo.getStyleClass().add("subtitulo");
        subtitulo.setWrapText(true);
        subtitulo.setMaxWidth(Double.MAX_VALUE);

        root = new VBox(10, topBar, subtitulo);

        subtitulo.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
    }

    public VBox getView() {
        return root;
    }
}