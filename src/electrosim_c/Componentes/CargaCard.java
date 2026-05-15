package electrosim_c.Componentes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Carga;

public class CargaCard {

    // Conservamos el contenedor principal que representa visualmente la tarjeta.
    private VBox root;

    // Construimos la tarjeta interactiva que permite editar una carga del sistema.
    public CargaCard(int index, Carga carga, Runnable onDelete, Runnable onUpdate) {

        // Mostramos el identificador visual de la carga dentro del simulador.
        Label titulo = new Label("CARGA Q" + (index + 1));

        // Aplicamos el estilo principal del encabezado de la tarjeta.
        titulo.getStyleClass().add("card-title");

        // Indicamos al usuario qué valor físico está editando.
        Label magnitudLabel = new Label("MAGNITUD (COULOMBS)");

        // Aplicamos el estilo descriptivo de los campos del formulario.
        magnitudLabel.getStyleClass().add("card-text");

        // Inicializamos el campo con el valor actual de la carga.
        TextField magnitudInput = new TextField(String.valueOf(carga.getCarga()));

        // Aplicamos el estilo visual estándar de los inputs.
        magnitudInput.getStyleClass().add("input-line");

        // Mostramos la sección donde se editarán las coordenadas.
        Label posicionLabel = new Label("POSICIÓN");

        // Aplicamos el estilo descriptivo para mantener consistencia visual.
        posicionLabel.getStyleClass().add("card-text");

        // Identificamos visualmente el campo de coordenada horizontal.
        Label labelX = new Label("X");

        // Identificamos visualmente el campo de coordenada vertical.
        Label labelY = new Label("Y");

        // Inicializamos el campo con la posición horizontal actual.
        TextField posX = new TextField(String.valueOf(carga.getPosicionX()));

        // Inicializamos el campo con la posición vertical actual.
        TextField posY = new TextField(String.valueOf(carga.getPosicionY()));

        // Aplicamos el estilo visual del formulario al eje X.
        posX.getStyleClass().add("input-line");

        // Aplicamos el estilo visual del formulario al eje Y.
        posY.getStyleClass().add("input-line");

        // Agrupamos la etiqueta y el campo del eje X.
        VBox campoX = new VBox(1, labelX, posX);

        // Agrupamos la etiqueta y el campo del eje Y.
        VBox campoY = new VBox(1, labelY, posY);

        // Organizamos ambos ejes en una misma fila para facilitar edición.
        HBox filaInputs = new HBox(6, campoX, campoY);

        // Alineamos los campos hacia la izquierda siguiendo el diseño general.
        filaInputs.setAlignment(Pos.CENTER_LEFT);

        // Creamos el botón que permite eliminar la carga del sistema.
        Button eliminar = new Button("ELIMINAR");

        // Aplicamos el estilo visual de los botones principales.
        eliminar.getStyleClass().add("btn-primary");

        // Permitimos que el botón ocupe todo el ancho disponible de la tarjeta.
        eliminar.setMaxWidth(
                Double.MAX_VALUE
        );

        // Delegamos la eliminación para mantener separada la lógica visual.
        eliminar.setOnAction(
                e -> onDelete.run()
        );

        // Escuchamos cambios en la magnitud para actualizar la simulación.
        magnitudInput.setOnAction(e -> {

            try {

                // Convertimos el texto ingresado a un valor numérico.
                float valor = Float.parseFloat(magnitudInput.getText());

                // Limitamos el rango permitido para mantener estabilidad física.
                valor = Math.max(-20, Math.min(20, valor));

                // Persistimos la nueva magnitud dentro del modelo.
                carga.setCarga(valor);

                // Reflejamos el valor final dentro del campo visual.
                magnitudInput.setText(String.valueOf(valor));

                // Notificamos que el sistema debe recalcular resultados.
                onUpdate.run();

            } catch (Exception ignored) {

                // Ignoramos entradas inválidas para no interrumpir la experiencia.
            }
        });

        // Escuchamos cambios en la coordenada horizontal de la carga.
        posX.setOnAction(e -> {

            try {

                // Convertimos el valor ingresado a una coordenada numérica.
                float valor = Float.parseFloat(posX.getText());

                // Limitamos la posición al área útil del simulador.
                valor = Math.max(-20, Math.min(20, valor));

                // Actualizamos la posición horizontal dentro del modelo.
                carga.setPosicionX(valor);

                // Reflejamos el valor corregido dentro del campo.
                posX.setText(String.valueOf(valor));

                // Disparamos la actualización física y visual del sistema.
                onUpdate.run();

            } catch (Exception ignored) {

                // Ignoramos entradas inválidas para mantener fluidez.
            }
        });

        // Escuchamos cambios en la coordenada vertical de la carga.
        posY.setOnAction(e -> {

            try {

                // Convertimos el valor ingresado a una coordenada numérica.
                float valor = Float.parseFloat(posY.getText());

                // Limitamos la posición al área útil del simulador.
                valor = Math.max(-20, Math.min(20, valor));

                // Actualizamos la posición vertical dentro del modelo.
                carga.setPosicionY(valor);

                // Reflejamos el valor corregido dentro del campo.
                posY.setText(String.valueOf(valor));

                // Disparamos la actualización física y visual del sistema.
                onUpdate.run();

            } catch (Exception ignored) {

                // Ignoramos entradas inválidas para mantener la interacción estable.
            }
        });

        // Construimos la composición final de la tarjeta editable.
        root = new VBox(6,
                titulo,
                magnitudLabel,
                magnitudInput,
                posicionLabel,
                filaInputs,
                eliminar
        );

        // Aplicamos el estilo visual general de las tarjetas de carga.
        root.getStyleClass().add("card-carga");

        // Permitimos que la tarjeta se adapte al ancho del panel lateral.
        root.setMaxWidth(Double.MAX_VALUE);
    }

    // Retornamos la vista construida para integrarla en la interfaz.
    public VBox getView() {
        return root;
    }
}
