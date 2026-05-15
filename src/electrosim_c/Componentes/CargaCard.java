package electrosim_c.Componentes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Carga;

public class CargaCard {

    private VBox root;

    public CargaCard(int index, Carga carga, Runnable onDelete, Runnable onUpdate) {

        Label titulo = new Label("CARGA Q" + (index + 1));
        titulo.getStyleClass().add("card-title");

        Label magnitudLabel = new Label("MAGNITUD (COULOMBS)");
        magnitudLabel.getStyleClass().add("card-text");

        TextField magnitudInput = new TextField(String.valueOf(carga.getCarga()));
        magnitudInput.getStyleClass().add("input-line");

        Label posicionLabel = new Label("POSICIÓN");
        posicionLabel.getStyleClass().add("card-text");

        Label labelX = new Label("X");
        Label labelY = new Label("Y");

        TextField posX = new TextField(String.valueOf(carga.getPosicionX()));
        TextField posY = new TextField(String.valueOf(carga.getPosicionY()));

        posX.getStyleClass().add("input-line");
        posY.getStyleClass().add("input-line");

        VBox campoX = new VBox(1, labelX, posX);
        VBox campoY = new VBox(1, labelY, posY);

        HBox filaInputs = new HBox(6, campoX, campoY);
        filaInputs.setAlignment(Pos.CENTER_LEFT);

        Button eliminar = new Button("ELIMINAR");

        eliminar.getStyleClass().add("btn-primary");

        eliminar.setMaxWidth(
                Double.MAX_VALUE
        );

        eliminar.setOnAction(
                e -> onDelete.run()
        );

        magnitudInput.setOnAction(e -> {
            try {
                float valor = Float.parseFloat(magnitudInput.getText());
                valor = Math.max(-20, Math.min(20, valor));
                carga.setCarga(valor);
                magnitudInput.setText(String.valueOf(valor));
                onUpdate.run();
            } catch (Exception ignored) {
            }
        });

        posX.setOnAction(e -> {
            try {
                float valor = Float.parseFloat(posX.getText());
                valor = Math.max(-20, Math.min(20, valor));
                carga.setPosicionX(valor);
                posX.setText(String.valueOf(valor));
                onUpdate.run();
            } catch (Exception ignored) {
            }
        });

        posY.setOnAction(e -> {
            try {
                float valor = Float.parseFloat(posY.getText());
                valor = Math.max(-20, Math.min(20, valor));
                carga.setPosicionY(valor);
                posY.setText(String.valueOf(valor));
                onUpdate.run();
            } catch (Exception ignored) {
            }
        });

        root = new VBox(6,
                titulo,
                magnitudLabel,
                magnitudInput,
                posicionLabel,
                filaInputs,
                eliminar
        );

        root.getStyleClass().add("card-carga");
        root.setMaxWidth(Double.MAX_VALUE);
    }

    public VBox getView() {
        return root;
    }
}
