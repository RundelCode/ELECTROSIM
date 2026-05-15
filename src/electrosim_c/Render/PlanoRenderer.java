package electrosim_c.Render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlanoRenderer {

    // Renderizamos la base visual del plano cartesiano donde ocurre toda la simulación.
    public void render(
            GraphicsContext gc,
            double width,
            double height,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        // Aplicamos el color base del fondo para mantener la estética general de ELECTROSIM.
        gc.setFill(
                Color.web("#07090f")
        );

        // Cubrimos completamente el canvas para limpiar cualquier render anterior.
        gc.fillRect(
                0,
                0,
                width,
                height
        );

        // Delegamos el dibujo de la cuadrícula y los ejes a un método especializado.
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

    // Construimos la cuadrícula visual que sirve como referencia espacial en la simulación.
    private void renderGrid(
            GraphicsContext gc,
            double width,
            double height,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        // Configuramos un color sutil para las líneas secundarias del plano.
        gc.setStroke(
                Color.web("#161925")
        );

        // Usamos un grosor delgado para no competir visualmente con las cargas.
        gc.setLineWidth(
                1
        );

        // Recorremos ambos ejes para generar toda la cuadrícula alrededor del origen.
        for (int i = -10;
                i <= 10;
                i++) {

            // Calculamos la posición horizontal de cada división del plano.
            double x
                    = centerX
                    + i * spacingX;

            // Calculamos la posición vertical de cada división del plano.
            double y
                    = centerY
                    + i * spacingY;

            // Dibujamos una línea vertical como guía de posicionamiento.
            gc.strokeLine(
                    x,
                    0,
                    x,
                    height
            );

            // Dibujamos una línea horizontal para completar la malla cartesiana.
            gc.strokeLine(
                    0,
                    y,
                    width,
                    y
            );
        }

        // Cambiamos a un color más visible para destacar los ejes principales.
        gc.setStroke(
                Color.web("#2f3445")
        );

        // Incrementamos el grosor para que el origen sea fácil de identificar.
        gc.setLineWidth(
                2
        );

        // Dibujamos el eje vertical principal del plano cartesiano.
        gc.strokeLine(
                centerX,
                0,
                centerX,
                height
        );

        // Dibujamos el eje horizontal principal del plano cartesiano.
        gc.strokeLine(
                0,
                centerY,
                width,
                centerY
        );
    }
}
