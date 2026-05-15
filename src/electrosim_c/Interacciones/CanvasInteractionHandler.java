package electrosim_c.Interacciones;

import controlador.FuerzaController;
import javafx.scene.canvas.Canvas;
import modelo.Carga;

import java.util.List;

public class CanvasInteractionHandler {

    private final Canvas canvas;
    private final List<Carga> cargas;
    private final FuerzaController fuerzaController;
    private final double radioCarga;

    private Carga cargaSeleccionada;

    public CanvasInteractionHandler(
            Canvas canvas,
            List<Carga> cargas,
            FuerzaController fuerzaController,
            double radioCarga
    ) {

        this.canvas = canvas;
        this.cargas = cargas;
        this.fuerzaController = fuerzaController;
        this.radioCarga = radioCarga;
    }

    public void configurar(
            Runnable onUpdate
    ) {

        canvas.setOnMousePressed(event -> {

            double mouseX
                    = event.getX();

            double mouseY
                    = event.getY();

            double width
                    = canvas.getWidth();

            double height
                    = canvas.getHeight();

            double centerX
                    = width / 2;

            double centerY
                    = height / 2;

            double spacingX
                    = width / 20;

            double spacingY
                    = height / 20;

            for (Carga carga : cargas) {

                double x
                        = centerX
                        + carga.getPosicionX()
                        * spacingX;

                double y
                        = centerY
                        - carga.getPosicionY()
                        * spacingY;

                double dx
                        = mouseX - x;

                double dy
                        = mouseY - y;

                double distancia
                        = Math.sqrt(
                                dx * dx
                                + dy * dy
                        );

                if (distancia <= radioCarga + 6) {

                    cargaSeleccionada
                            = carga;

                    break;
                }
            }
        });

        canvas.setOnMouseDragged(event -> {

            if (cargaSeleccionada == null) {
                return;
            }

            double mouseX
                    = event.getX();

            double mouseY
                    = event.getY();

            double width
                    = canvas.getWidth();

            double height
                    = canvas.getHeight();

            double centerX
                    = width / 2;

            double centerY
                    = height / 2;

            double spacingX
                    = width / 20;

            double spacingY
                    = height / 20;

            float nuevaX
                    = (float) ((mouseX - centerX)
                    / spacingX);

            float nuevaY
                    = (float) ((centerY - mouseY)
                    / spacingY);

            nuevaX
                    = Math.max(
                            -9,
                            Math.min(
                                    9,
                                    nuevaX
                            )
                    );

            nuevaY
                    = Math.max(
                            -9,
                            Math.min(
                                    9,
                                    nuevaY
                            )
                    );

            cargaSeleccionada.setPosicionX(
                    nuevaX
            );

            cargaSeleccionada.setPosicionY(
                    nuevaY
            );

            fuerzaController.calcularFuerzas(
                    cargas
            );

            onUpdate.run();
        });

        canvas.setOnMouseReleased(event -> {

            cargaSeleccionada = null;
        });
    }

    public Carga getCargaSeleccionada() {

        return cargaSeleccionada;
    }
}
