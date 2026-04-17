package electrosim_c.Vistas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Inicio {

    private VBox root;

    public Inicio(Runnable goToSimulador, Runnable goToBuscador, Runnable goToTeoria) {

        root = new VBox(15);
        root.setAlignment(Pos.TOP_CENTER);
        root.getStyleClass().add("contenedor");

        Label titulo = new Label("ELECTROSIM");
        titulo.getStyleClass().add("titulo");
        titulo.setStyle("-fx-font-size: 40px;");

        Label descripcion = new Label("Simulador interactivo de la Ley de Coulomb para visualizar y analizar fuerzas eléctricas entre cargas.");
        descripcion.getStyleClass().add("texto");
        descripcion.setWrapText(true);

        Button opcion1 = new Button("NUEVA SIMULACIÓN");
        Button opcion2 = new Button("REGISTROS");
        Button opcion3 = new Button("FUNDAMENTOS TEORICOS");
        Button opcion4 = new Button("SALIR");

        opcion1.setOnAction(e -> goToSimulador.run());
        opcion2.setOnAction(e -> goToBuscador.run());
        opcion3.setOnAction(e -> goToTeoria.run());
        opcion4.setOnAction(e -> System.exit(0));

        for (Button btn : new Button[]{opcion1, opcion2, opcion3, opcion4}) {
            btn.prefWidthProperty().bind(root.widthProperty().multiply(0.7));
        }

        root.getChildren().addAll(
                titulo,
                descripcion,
                opcion1,
                opcion2,
                opcion3,
                opcion4
        );
    }

    public VBox getView() {
        return root;
    }
}
