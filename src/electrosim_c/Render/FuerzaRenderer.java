package electrosim_c.Render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import modelo.Carga;
import modelo.Fuerza;

import java.util.List;

public class FuerzaRenderer {

    public void renderInteracciones(
            GraphicsContext gc,
            List<Fuerza> fuerzas,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        for (Fuerza fuerza : fuerzas) {

            Carga origen
                    = fuerza.getCargaOrigen();

            Carga destino
                    = fuerza.getCargaDestino();

            double x1
                    = centerX
                    + origen.getPosicionX() * spacingX;

            double y1
                    = centerY
                    - origen.getPosicionY() * spacingY;

            double x2
                    = centerX
                    + destino.getPosicionX() * spacingX;

            double y2
                    = centerY
                    - destino.getPosicionY() * spacingY;

            Color color
                    = fuerza.esRepulsion()
                    ? Color.web("#ff5e57")
                    : Color.web("#4fc3ff");

            gc.setStroke(
                    color
            );

            gc.setGlobalAlpha(
                    0.75
            );

            gc.setLineWidth(
                    2
            );

            gc.strokeLine(
                    x1,
                    y1,
                    x2,
                    y2
            );

            gc.setGlobalAlpha(
                    1
            );

            dibujarFlechasInteraccion(
                    gc,
                    x1,
                    y1,
                    x2,
                    y2,
                    fuerza.esRepulsion()
            );
        }
    }

    public void renderEtiquetas(
            GraphicsContext gc,
            List<Fuerza> fuerzas,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        for (Fuerza fuerza : fuerzas) {

            Carga origen
                    = fuerza.getCargaOrigen();

            Carga destino
                    = fuerza.getCargaDestino();

            double x1
                    = centerX
                    + origen.getPosicionX() * spacingX;

            double y1
                    = centerY
                    - origen.getPosicionY() * spacingY;

            double x2
                    = centerX
                    + destino.getPosicionX() * spacingX;

            double y2
                    = centerY
                    - destino.getPosicionY() * spacingY;

            double mx
                    = (x1 + x2) / 2;

            double my
                    = (y1 + y2) / 2;

            double ancho = 78;
            double alto = 48;

            gc.setGlobalAlpha(
                    0.38
            );

            gc.setFill(
                    Color.web("#0d111c")
            );

            gc.fillRoundRect(
                    mx - ancho / 2,
                    my - alto / 2,
                    ancho,
                    alto,
                    12,
                    12
            );

            gc.setGlobalAlpha(
                    0.55
            );

            gc.setStroke(
                    Color.web("#7380a8")
            );

            gc.setLineWidth(
                    1
            );

            gc.strokeRoundRect(
                    mx - ancho / 2,
                    my - alto / 2,
                    ancho,
                    alto,
                    12,
                    12
            );

            gc.setGlobalAlpha(
                    1
            );

            gc.setTextAlign(
                    javafx.scene.text.TextAlignment.CENTER
            );

            gc.setFill(
                    Color.WHITE
            );

            gc.setFont(
                    javafx.scene.text.Font.font(
                            "System",
                            javafx.scene.text.FontWeight.BOLD,
                            9
                    )
            );

            String tipo
                    = fuerza.esRepulsion()
                    ? "Repulsión"
                    : "Atracción";

            gc.fillText(
                    tipo,
                    mx,
                    my - 3
            );

            gc.setFill(
                    Color.web("#c4cee8")
            );

            gc.setFont(
                    javafx.scene.text.Font.font(
                            "System",
                            javafx.scene.text.FontWeight.NORMAL,
                            8
                    )
            );

            gc.fillText(
                    "F = "
                    + String.format(
                            "%.2f",
                            fuerza.getMagnitud()
                    ) + " N",
                    mx,
                    my + 8
            );

            gc.fillText(
                    "r = "
                    + String.format(
                            "%.2f",
                            fuerza.getDistancia()
                    ) + " m",
                    mx,
                    my + 18
            );
        }
    }

    private void dibujarFlechasInteraccion(
            GraphicsContext gc,
            double x1,
            double y1,
            double x2,
            double y2,
            boolean repulsion
    ) {

        double dx
                = x2 - x1;

        double dy
                = y2 - y1;

        double angle
                = Math.atan2(
                        dy,
                        dx
                );

        double mx
                = (x1 + x2) / 2;

        double my
                = (y1 + y2) / 2;

        double offset = 32;

        double ax1
                = mx - Math.cos(angle) * offset;

        double ay1
                = my - Math.sin(angle) * offset;

        double ax2
                = mx + Math.cos(angle) * offset;

        double ay2
                = my + Math.sin(angle) * offset;

        if (repulsion) {

            drawArrow(
                    gc,
                    mx,
                    my,
                    ax1,
                    ay1
            );

            drawArrow(
                    gc,
                    mx,
                    my,
                    ax2,
                    ay2
            );

        } else {

            drawArrow(
                    gc,
                    ax1,
                    ay1,
                    mx,
                    my
            );

            drawArrow(
                    gc,
                    ax2,
                    ay2,
                    mx,
                    my
            );
        }
    }

    private void drawArrow(
            GraphicsContext gc,
            double x1,
            double y1,
            double x2,
            double y2
    ) {

        gc.strokeLine(
                x1,
                y1,
                x2,
                y2
        );

        double angle
                = Math.atan2(
                        y2 - y1,
                        x2 - x1
                );

        double size = 8;

        double xA
                = x2
                - size
                * Math.cos(
                        angle - Math.PI / 6
                );

        double yA
                = y2
                - size
                * Math.sin(
                        angle - Math.PI / 6
                );

        double xB
                = x2
                - size
                * Math.cos(
                        angle + Math.PI / 6
                );

        double yB
                = y2
                - size
                * Math.sin(
                        angle + Math.PI / 6
                );

        gc.strokeLine(
                x2,
                y2,
                xA,
                yA
        );

        gc.strokeLine(
                x2,
                y2,
                xB,
                yB
        );
    }
}
