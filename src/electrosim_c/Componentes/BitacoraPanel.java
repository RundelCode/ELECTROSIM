package electrosim_c.Componentes;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class BitacoraPanel extends VBox {

    private final TextField nombreField;
    private final TextField tituloField;
    private final TextArea notasArea;
    private final Button guardarButton;

    public BitacoraPanel() {

        getStyleClass().add(
                "bitacora-panel"
        );

        setSpacing(
                15
        );

        setPadding(
                new Insets(
                        15
                )
        );

        Label titulo
                = new Label(
                        "BITÁCORA"
                );

        titulo.getStyleClass().add(
                "bitacora-title"
        );

        Label nombreLabel
                = new Label(
                        "NOMBRE COMPLETO *"
                );

        nombreLabel.getStyleClass().add(
                "bitacora-label"
        );

        Label experimentoLabel
                = new Label(
                        "TÍTULO (OPCIONAL)"
                );

        experimentoLabel.getStyleClass().add(
                "bitacora-label"
        );

        nombreField
                = new TextField();

        tituloField
                = new TextField();

        nombreField.getStyleClass().add(
                "bitacora-input"
        );

        tituloField.getStyleClass().add(
                "bitacora-input"
        );

        HBox filaInputs
                = new HBox(
                        20
                );

        VBox nombreBox
                = new VBox(
                        6,
                        nombreLabel,
                        nombreField
                );

        VBox tituloBox
                = new VBox(
                        6,
                        experimentoLabel,
                        tituloField
                );

        HBox.setHgrow(
                nombreBox,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                tituloBox,
                Priority.ALWAYS
        );

        nombreField.setMaxWidth(
                Double.MAX_VALUE
        );

        tituloField.setMaxWidth(
                Double.MAX_VALUE
        );

        filaInputs.getChildren().addAll(
                nombreBox,
                tituloBox
        );

        notasArea
                = new TextArea();

        notasArea.setPromptText(
                "Observaciones..."
        );

        notasArea.getStyleClass().add(
                "bitacora-textarea"
        );

        notasArea.setPrefRowCount(
                5
        );

        guardarButton
                = new Button(
                        "GUARDAR RESULTADOS DE LA SIMULACIÓN"
                );

        guardarButton.getStyleClass().add(
                "bitacora-save"
        );

        guardarButton.setMaxWidth(
                Double.MAX_VALUE
        );

        HBox.setHgrow(
                guardarButton,
                Priority.ALWAYS
        );
        
        VBox.setVgrow(
                guardarButton,
                Priority.NEVER
        );

        getChildren().addAll(
                titulo,
                filaInputs,
                notasArea,
                guardarButton
        );
    }

    public TextField getNombreField() {
        return nombreField;
    }

    public TextField getTituloField() {
        return tituloField;
    }

    public TextArea getNotasArea() {
        return notasArea;
    }

    public Button getGuardarButton() {
        return guardarButton;
    }
}
