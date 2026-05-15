package electrosim_c.Render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlanoRenderer {

    public void render(
            GraphicsContext gc,
            double width,
            double height,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        gc.setFill(
                Color.web("#07090f")
        );

        gc.fillRect(
                0,
                0,
                width,
                height
        );

        renderGrid(
                gc,
                width,
                height,
                centerX,
                centerY,
                spacingX,
                spacingY
        );
    }

    private void renderGrid(
            GraphicsContext gc,
            double width,
            double height,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        gc.setStroke(
                Color.web("#161925")
        );

        gc.setLineWidth(
                1
        );

        for (int i = -10;
                i <= 10;
                i++) {

            double x
                    = centerX
                    + i * spacingX;

            double y
                    = centerY
                    + i * spacingY;

            gc.strokeLine(
                    x,
                    0,
                    x,
                    height
            );

            gc.strokeLine(
                    0,
                    y,
                    width,
                    y
            );
        }

        gc.setStroke(
                Color.web("#2f3445")
        );

        gc.setLineWidth(
                2
        );

        gc.strokeLine(
                centerX,
                0,
                centerX,
                height
        );

        gc.strokeLine(
                0,
                centerY,
                width,
                centerY
        );
    }
}
