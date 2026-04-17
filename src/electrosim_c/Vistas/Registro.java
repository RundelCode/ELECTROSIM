package electrosim_c.Vistas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Registro {

    private VBox root;

    public Registro(Runnable goBack) {
        root = new VBox(15);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("contenedor");

        Label titulo = new Label("ELECTROSIM");
        titulo.getStyleClass().add("titulo");
        titulo.setStyle("-fx-font-size: 40px;");

        Label info = new Label("Registro en especifico");
        info.getStyleClass().add("texto");

        Button back = new Button("Volver");

        back.setOnAction(e -> goBack.run());

        root.getChildren().addAll(titulo, info, back);
    }

    public VBox getView() {
        return root;
    }
}
