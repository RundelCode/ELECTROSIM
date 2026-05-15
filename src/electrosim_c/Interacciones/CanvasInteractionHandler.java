package electrosim_c.Interacciones;

import controlador.FuerzaController;
import javafx.scene.canvas.Canvas;
import modelo.Carga;

import java.util.List;

public class CanvasInteractionHandler {

    // Conservamos la referencia del canvas donde ocurre toda la interacción visual.
    private final Canvas canvas;

    // Compartimos la colección de cargas activas para permitir manipulación directa.
    private final List<Carga> cargas;

    // Usamos el controlador de fuerzas para recalcular la física en tiempo real.
    private final FuerzaController fuerzaController;

    // Definimos el radio visual de cada carga para detectar clics con precisión.
    private final double radioCarga;

    // Guardamos temporalmente la carga que el usuario está manipulando.
    private Carga cargaSeleccionada;

    // Construimos el manejador encargado de conectar el canvas con la simulación.
    public CanvasInteractionHandler(
            Canvas canvas,
            List<Carga> cargas,
            FuerzaController fuerzaController,
            double radioCarga
    ) {

        // Conservamos el canvas para registrar eventos del usuario.
        this.canvas = canvas;

        // Compartimos la lista de cargas para poder moverlas dentro del plano.
        this.cargas = cargas;

        // Conservamos el controlador que recalcula las interacciones físicas.
        this.fuerzaController = fuerzaController;

        // Guardamos el radio usado para detectar selección sobre cada carga.
        this.radioCarga = radioCarga;
    }

    // Configuramos todos los eventos del mouse que permiten interactuar con ELECTROSIM.
    public void configurar(
            Runnable onUpdate
    ) {

        // Escuchamos el momento exacto en el que el usuario presiona sobre el canvas.
        canvas.setOnMousePressed(event -> {

            // Capturamos la posición horizontal actual del cursor.
            double mouseX
                    = event.getX();

            // Capturamos la posición vertical actual del cursor.
            double mouseY
                    = event.getY();

            // Obtenemos el ancho actual del área de simulación.
            double width
                    = canvas.getWidth();

            // Obtenemos el alto actual del área de simulación.
            double height
                    = canvas.getHeight();

            // Calculamos el centro horizontal del plano cartesiano.
            double centerX
                    = width / 2;

            // Calculamos el centro vertical del plano cartesiano.
            double centerY
                    = height / 2;

            // Calculamos la escala horizontal entre unidades físicas y píxeles.
            double spacingX
                    = width / 20;

            // Calculamos la escala vertical entre unidades físicas y píxeles.
            double spacingY
                    = height / 20;

            // Recorremos todas las cargas para detectar cuál fue seleccionada.
            for (Carga carga : cargas) {

                // Convertimos la posición física de la carga al eje X visual.
                double x
                        = centerX
                        + carga.getPosicionX()
                        * spacingX;

                // Convertimos la posición física de la carga al eje Y visual.
                double y
                        = centerY
                        - carga.getPosicionY()
                        * spacingY;

                // Calculamos la distancia horizontal entre cursor y carga.
                double dx
                        = mouseX - x;

                // Calculamos la distancia vertical entre cursor y carga.
                double dy
                        = mouseY - y;

                // Calculamos la distancia real entre el cursor y la carga.
                double distancia
                        = Math.sqrt(
                                dx * dx
                                + dy * dy
                        );

                // Si el clic cae dentro del área de la carga, la seleccionamos.
                if (distancia <= radioCarga + 6) {

                    // Guardamos la carga seleccionada para moverla posteriormente.
                    cargaSeleccionada
                            = carga;

                    // Detenemos la búsqueda porque ya encontramos el objetivo.
                    break;
                }
            }
        });

        // Escuchamos el arrastre del mouse para mover cargas en tiempo real.
        canvas.setOnMouseDragged(event -> {

            // Si no hay ninguna carga seleccionada, no hacemos nada.
            if (cargaSeleccionada == null) {
                return;
            }

            // Capturamos la nueva posición horizontal del cursor.
            double mouseX
                    = event.getX();

            // Capturamos la nueva posición vertical del cursor.
            double mouseY
                    = event.getY();

            // Obtenemos el ancho actual del canvas.
            double width
                    = canvas.getWidth();

            // Obtenemos el alto actual del canvas.
            double height
                    = canvas.getHeight();

            // Recalculamos el centro horizontal del plano.
            double centerX
                    = width / 2;

            // Recalculamos el centro vertical del plano.
            double centerY
                    = height / 2;

            // Recalculamos la escala horizontal actual.
            double spacingX
                    = width / 20;

            // Recalculamos la escala vertical actual.
            double spacingY
                    = height / 20;

            // Convertimos la posición visual del mouse a coordenadas físicas.
            float nuevaX
                    = (float) ((mouseX - centerX)
                    / spacingX);

            // Convertimos la posición visual del mouse al eje Y físico.
            float nuevaY
                    = (float) ((centerY - mouseY)
                    / spacingY);

            // Limitamos el movimiento horizontal al área útil del simulador.
            nuevaX
                    = Math.max(
                            -9,
                            Math.min(
                                    9,
                                    nuevaX
                            )
                    );

            // Limitamos el movimiento vertical al área útil del simulador.
            nuevaY
                    = Math.max(
                            -9,
                            Math.min(
                                    9,
                                    nuevaY
                            )
                    );

            // Actualizamos la nueva posición horizontal de la carga.
            cargaSeleccionada.setPosicionX(
                    nuevaX
            );

            // Actualizamos la nueva posición vertical de la carga.
            cargaSeleccionada.setPosicionY(
                    nuevaY
            );

            // Recalculamos todas las fuerzas después del movimiento.
            fuerzaController.calcularFuerzas(
                    cargas
            );

            // Notificamos a la interfaz para refrescar el estado visual.
            onUpdate.run();
        });

        // Escuchamos cuando el usuario termina de arrastrar una carga.
        canvas.setOnMouseReleased(event -> {

            // Liberamos la selección para cerrar la interacción actual.
            cargaSeleccionada = null;
        });
    }

    // Retornamos la carga que actualmente está siendo manipulada por el usuario.
    public Carga getCargaSeleccionada() {

        return cargaSeleccionada;
    }
}
