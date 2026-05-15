package electrosim_c.Render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import modelo.Carga;

import java.util.List;

public class CargaRenderer {

    private final double radioCarga;

    public CargaRenderer(
            double radioCarga
    ) {

        this.radioCarga = radioCarga;
    }

    public void renderCampoElectrico(
            GraphicsContext gc,
            List<Carga> cargas,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        for (Carga c : cargas) {

            double x
                    = centerX
                    + c.getPosicionX() * spacingX;

            double y
                    = centerY
                    - c.getPosicionY() * spacingY;

            float magnitud
                    = Math.abs(
                            c.getCarga()
                    );

            Color color
                    = c.getCarga() > 0
                    ? Color.web("#ff4530")
                    : Color.web("#3aa0ff");

            int capas
                    = (int) Math.max(
                            4,
                            magnitud
                    );

            double radioBase
                    = 28 + magnitud * 3;

            double alphaBase
                    = 0.012 + magnitud * 0.003;

            for (int i = 0;
                    i < capas;
                    i++) {

                double radius
                        = radioBase + i * 20;

                double alpha
                        = alphaBase - i * 0.002;

                alpha
                        = Math.max(
                                0.008,
                                alpha
                        );

                gc.setGlobalAlpha(
                        alpha
                );

                gc.setFill(
                        color
                );

                gc.fillOval(
                        x - radius,
                        y - radius,
                        radius * 2,
                        radius * 2
                );
            }
        }

        gc.setGlobalAlpha(
                1
        );
    }

    public void renderCargas(
            GraphicsContext gc,
            List<Carga> cargas,
            Carga cargaSeleccionada,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        for (int i = 0;
                i < cargas.size();
                i++) {

            Carga c
                    = cargas.get(i);

            double x
                    = centerX
                    + c.getPosicionX() * spacingX;

            double y
                    = centerY
                    - c.getPosicionY() * spacingY;

            Color glow
                    = c.getCarga() > 0
                    ? Color.web("#ff7849")
                    : Color.web("#4fc3ff");

            Color core
                    = c.getCarga() > 0
                    ? Color.web("#ff3b30")
                    : Color.web("#007aff");

            if (c == cargaSeleccionada) {

                glow
                        = c.getCarga() > 0
                        ? Color.web("#ffb199")
                        : Color.web("#8ed8ff");
            }

            gc.setGlobalAlpha(
                    0.18
            );

            gc.setFill(
                    glow
            );

            gc.fillOval(
                    x - radioCarga - 10,
                    y - radioCarga - 10,
                    (radioCarga + 10) * 2,
                    (radioCarga + 10) * 2
            );

            gc.setGlobalAlpha(
                    1
            );

            gc.setFill(
                    core
            );

            gc.fillOval(
                    x - radioCarga,
                    y - radioCarga,
                    radioCarga * 2,
                    radioCarga * 2
            );

            gc.setFill(
                    Color.WHITE
            );

            gc.setTextAlign(
                    javafx.scene.text.TextAlignment.CENTER
            );

            gc.setFont(
                    javafx.scene.text.Font.font(
                            "System",
                            javafx.scene.text.FontWeight.BOLD,
                            11
                    )
            );

            gc.fillText(
                    "Q" + (i + 1),
                    x,
                    y + 4
            );
        }
    }
}
