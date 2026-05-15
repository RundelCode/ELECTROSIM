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

    // Conservamos el campo donde el usuario identifica al autor del experimento.
    private final TextField nombreField;

    // Conservamos el campo donde el usuario puede nombrar la simulación.
    private final TextField tituloField;

    // Conservamos el área donde se documentan observaciones del experimento.
    private final TextArea notasArea;

    // Conservamos el botón que dispara el guardado de resultados.
    private final Button guardarButton;

    // Construimos el panel encargado de registrar y documentar cada simulación.
    public BitacoraPanel() {

        // Aplicamos el estilo visual principal del módulo de bitácora.
        getStyleClass().add(
                "bitacora-panel"
        );

        // Definimos una separación cómoda entre cada bloque del formulario.
        setSpacing(
                15
        );

        // Añadimos espacio interno para mejorar la lectura y edición.
        setPadding(
                new Insets(
                        15
                )
        );

        // Creamos el encabezado principal del módulo de registro.
        Label titulo
                = new Label(
                        "BITÁCORA"
                );

        // Aplicamos el estilo visual del título principal.
        titulo.getStyleClass().add(
                "bitacora-title"
        );

        // Indicamos el campo obligatorio donde se identifica el usuario.
        Label nombreLabel
                = new Label(
                        "NOMBRE COMPLETO *"
                );

        // Aplicamos el estilo descriptivo de los campos del formulario.
        nombreLabel.getStyleClass().add(
                "bitacora-label"
        );

        // Indicamos el campo opcional para nombrar el experimento.
        Label experimentoLabel
                = new Label(
                        "TÍTULO (OPCIONAL)"
                );

        // Aplicamos el mismo estilo descriptivo para mantener consistencia.
        experimentoLabel.getStyleClass().add(
                "bitacora-label"
        );

        // Creamos el campo donde se ingresará el nombre del usuario.
        nombreField
                = new TextField();

        // Creamos el campo donde se ingresará el título del experimento.
        tituloField
                = new TextField();

        // Aplicamos el estilo visual del formulario al campo de nombre.
        nombreField.getStyleClass().add(
                "bitacora-input"
        );

        // Aplicamos el estilo visual del formulario al campo de título.
        tituloField.getStyleClass().add(
                "bitacora-input"
        );

        // Organizamos ambos campos principales en una misma fila.
        HBox filaInputs
                = new HBox(
                        20
                );

        // Agrupamos la etiqueta y el campo del nombre en un bloque visual.
        VBox nombreBox
                = new VBox(
                        6,
                        nombreLabel,
                        nombreField
                );

        // Agrupamos la etiqueta y el campo del experimento en un bloque visual.
        VBox tituloBox
                = new VBox(
                        6,
                        experimentoLabel,
                        tituloField
                );

        // Permitimos que el bloque del nombre aproveche el espacio disponible.
        HBox.setHgrow(
                nombreBox,
                Priority.ALWAYS
        );

        // Permitimos que el bloque del título aproveche el espacio disponible.
        HBox.setHgrow(
                tituloBox,
                Priority.ALWAYS
        );

        // Permitimos que el campo de nombre se expanda horizontalmente.
        nombreField.setMaxWidth(
                Double.MAX_VALUE
        );

        // Permitimos que el campo de título se expanda horizontalmente.
        tituloField.setMaxWidth(
                Double.MAX_VALUE
        );

        // Incorporamos ambos bloques al formulario principal.
        filaInputs.getChildren().addAll(
                nombreBox,
                tituloBox
        );

        // Creamos el área donde se registrarán observaciones del experimento.
        notasArea
                = new TextArea();

        // Mostramos un texto guía para orientar al usuario.
        notasArea.setPromptText(
                "Observaciones..."
        );

        // Aplicamos el estilo visual del área de texto.
        notasArea.getStyleClass().add(
                "bitacora-textarea"
        );

        // Definimos una altura inicial cómoda para escritura.
        notasArea.setPrefRowCount(
                5
        );

        // Creamos el botón que permitirá persistir la simulación.
        guardarButton
                = new Button(
                        "GUARDAR RESULTADOS DE LA SIMULACIÓN"
                );

        // Aplicamos el estilo principal de acción del módulo.
        guardarButton.getStyleClass().add(
                "bitacora-save"
        );

        // Permitimos que el botón ocupe todo el ancho disponible.
        guardarButton.setMaxWidth(
                Double.MAX_VALUE
        );

        // Permitimos que el botón se adapte horizontalmente al layout.
        HBox.setHgrow(
                guardarButton,
                Priority.ALWAYS
        );

        // Evitamos que el botón crezca verticalmente y rompa la composición.
        VBox.setVgrow(
                guardarButton,
                Priority.NEVER
        );

        // Incorporamos todos los elementos para construir el formulario completo.
        getChildren().addAll(
                titulo,
                filaInputs,
                notasArea,
                guardarButton
        );
    }

    // Retornamos el campo del nombre para capturar y validar datos externos.
    public TextField getNombreField() {
        return nombreField;
    }

    // Retornamos el campo del título para integrarlo con la lógica de guardado.
    public TextField getTituloField() {
        return tituloField;
    }

    // Retornamos el área de observaciones para persistir notas del experimento.
    public TextArea getNotasArea() {
        return notasArea;
    }

    // Retornamos el botón principal para conectar eventos desde la vista.
    public Button getGuardarButton() {
        return guardarButton;
    }
}
