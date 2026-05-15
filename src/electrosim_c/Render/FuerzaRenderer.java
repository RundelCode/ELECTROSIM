package electrosim_c.Render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import modelo.Carga;
import modelo.Fuerza;

import java.util.List;

public class FuerzaRenderer {

    // Renderizamos todas las interacciones físicas entre las cargas activas del sistema.
    public void renderInteracciones(
            GraphicsContext gc,
            List<Fuerza> fuerzas,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        // Recorremos cada fuerza calculada para representarla visualmente en el canvas.
        for (Fuerza fuerza : fuerzas) {

            // Recuperamos la carga desde donde se origina la interacción.
            Carga origen
                    = fuerza.getCargaOrigen();

            // Recuperamos la carga hacia donde se dirige la interacción.
            Carga destino
                    = fuerza.getCargaDestino();

            // Convertimos la posición física del origen a coordenadas visuales.
            double x1
                    = centerX
                    + origen.getPosicionX() * spacingX;

            // Convertimos la posición vertical del origen al sistema visual del canvas.
            double y1
                    = centerY
                    - origen.getPosicionY() * spacingY;

            // Convertimos la posición física del destino a coordenadas visuales.
            double x2
                    = centerX
                    + destino.getPosicionX() * spacingX;

            // Convertimos la posición vertical del destino al sistema visual del canvas.
            double y2
                    = centerY
                    - destino.getPosicionY() * spacingY;

            // Elegimos un color distinto para identificar atracción o repulsión.
            Color color
                    = fuerza.esRepulsion()
                    ? Color.web("#ff5e57")
                    : Color.web("#4fc3ff");

            // Aplicamos el color de la interacción antes de dibujarla.
            gc.setStroke(
                    color
            );

            // Suavizamos ligeramente la línea para integrarla mejor visualmente.
            gc.setGlobalAlpha(
                    0.75
            );

            // Definimos un grosor uniforme para todas las fuerzas del simulador.
            gc.setLineWidth(
                    2
            );

            // Dibujamos la línea principal que conecta ambas cargas.
            gc.strokeLine(
                    x1,
                    y1,
                    x2,
                    y2
            );

            // Restauramos la opacidad para los siguientes elementos visuales.
            gc.setGlobalAlpha(
                    1
            );

            // Añadimos flechas para indicar claramente la dirección de la interacción.
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

    // Mostramos información numérica de cada interacción directamente sobre el canvas.
    public void renderEtiquetas(
            GraphicsContext gc,
            List<Fuerza> fuerzas,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        // Recorremos cada fuerza para generar su etiqueta informativa.
        for (Fuerza fuerza : fuerzas) {

            // Recuperamos la carga origen de la interacción.
            Carga origen
                    = fuerza.getCargaOrigen();

            // Recuperamos la carga destino de la interacción.
            Carga destino
                    = fuerza.getCargaDestino();

            // Calculamos la posición visual de la carga origen.
            double x1
                    = centerX
                    + origen.getPosicionX() * spacingX;

            // Calculamos la posición vertical visual de la carga origen.
            double y1
                    = centerY
                    - origen.getPosicionY() * spacingY;

            // Calculamos la posición visual de la carga destino.
            double x2
                    = centerX
                    + destino.getPosicionX() * spacingX;

            // Calculamos la posición vertical visual de la carga destino.
            double y2
                    = centerY
                    - destino.getPosicionY() * spacingY;

            // Ubicamos el punto medio horizontal de la interacción.
            double mx
                    = (x1 + x2) / 2;

            // Ubicamos el punto medio vertical de la interacción.
            double my
                    = (y1 + y2) / 2;

            // Definimos el ancho del panel informativo.
            double ancho = 78;

            // Definimos el alto del panel informativo.
            double alto = 48;

            // Aplicamos transparencia para integrar el fondo con la interfaz.
            gc.setGlobalAlpha(
                    0.38
            );

            // Configuramos el color del fondo de la etiqueta.
            gc.setFill(
                    Color.web("#0d111c")
            );

            // Dibujamos el contenedor visual de la información.
            gc.fillRoundRect(
                    mx - ancho / 2,
                    my - alto / 2,
                    ancho,
                    alto,
                    12,
                    12
            );

            // Ajustamos la transparencia del borde decorativo.
            gc.setGlobalAlpha(
                    0.55
            );

            // Configuramos el color del borde de la tarjeta.
            gc.setStroke(
                    Color.web("#7380a8")
            );

            // Definimos un borde delgado para no recargar la interfaz.
            gc.setLineWidth(
                    1
            );

            // Dibujamos el borde externo de la etiqueta.
            gc.strokeRoundRect(
                    mx - ancho / 2,
                    my - alto / 2,
                    ancho,
                    alto,
                    12,
                    12
            );

            // Restauramos la opacidad antes de dibujar el texto.
            gc.setGlobalAlpha(
                    1
            );

            // Centramos el contenido textual dentro de la tarjeta.
            gc.setTextAlign(
                    javafx.scene.text.TextAlignment.CENTER
            );

            // Configuramos el color principal del texto.
            gc.setFill(
                    Color.WHITE
            );

            // Aplicamos una fuente destacada para el tipo de interacción.
            gc.setFont(
                    javafx.scene.text.Font.font(
                            "System",
                            javafx.scene.text.FontWeight.BOLD,
                            9
                    )
            );

            // Traducimos el tipo físico de la interacción a una etiqueta legible.
            String tipo
                    = fuerza.esRepulsion()
                    ? "Repulsión"
                    : "Atracción";

            // Mostramos el tipo de interacción entre ambas cargas.
            gc.fillText(
                    tipo,
                    mx,
                    my - 3
            );

            // Cambiamos el color para los datos numéricos secundarios.
            gc.setFill(
                    Color.web("#c4cee8")
            );

            // Reducimos el peso visual de la fuente para los valores físicos.
            gc.setFont(
                    javafx.scene.text.Font.font(
                            "System",
                            javafx.scene.text.FontWeight.NORMAL,
                            8
                    )
            );

            // Mostramos la magnitud calculada de la fuerza.
            gc.fillText(
                    "F = "
                    + String.format(
                            "%.2f",
                            fuerza.getMagnitud()
                    ) + " N",
                    mx,
                    my + 8
            );

            // Mostramos la distancia entre ambas cargas involucradas.
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

    // Calculamos y dibujamos la orientación visual de las flechas de interacción.
    private void dibujarFlechasInteraccion(
            GraphicsContext gc,
            double x1,
            double y1,
            double x2,
            double y2,
            boolean repulsion
    ) {

        // Calculamos la diferencia horizontal entre ambos puntos.
        double dx
                = x2 - x1;

        // Calculamos la diferencia vertical entre ambos puntos.
        double dy
                = y2 - y1;

        // Obtenemos el ángulo general de la interacción.
        double angle
                = Math.atan2(
                        dy,
                        dx
                );

        // Calculamos el punto medio horizontal de referencia.
        double mx
                = (x1 + x2) / 2;

        // Calculamos el punto medio vertical de referencia.
        double my
                = (y1 + y2) / 2;

        // Separamos visualmente las flechas del centro de la interacción.
        double offset = 32;

        // Calculamos el primer extremo horizontal.
        double ax1
                = mx - Math.cos(angle) * offset;

        // Calculamos el primer extremo vertical.
        double ay1
                = my - Math.sin(angle) * offset;

        // Calculamos el segundo extremo horizontal.
        double ax2
                = mx + Math.cos(angle) * offset;

        // Calculamos el segundo extremo vertical.
        double ay2
                = my + Math.sin(angle) * offset;

        // Si la interacción es de repulsión, las flechas salen del centro.
        if (repulsion) {

            // Dibujamos la primera dirección de repulsión.
            drawArrow(
                    gc,
                    mx,
                    my,
                    ax1,
                    ay1
            );

            // Dibujamos la segunda dirección de repulsión.
            drawArrow(
                    gc,
                    mx,
                    my,
                    ax2,
                    ay2
            );

        } else {

            // En atracción, la primera flecha apunta hacia el centro.
            drawArrow(
                    gc,
                    ax1,
                    ay1,
                    mx,
                    my
            );

            // En atracción, la segunda flecha también converge al centro.
            drawArrow(
                    gc,
                    ax2,
                    ay2,
                    mx,
                    my
            );
        }
    }

    // Dibujamos una flecha individual para representar dirección y sentido.
    private void drawArrow(
            GraphicsContext gc,
            double x1,
            double y1,
            double x2,
            double y2
    ) {

        // Dibujamos el cuerpo principal de la flecha.
        gc.strokeLine(
                x1,
                y1,
                x2,
                y2
        );

        // Calculamos la orientación exacta de la flecha.
        double angle
                = Math.atan2(
                        y2 - y1,
                        x2 - x1
                );

        // Definimos el tamaño estándar de la punta.
        double size = 8;

        // Calculamos el primer lado de la punta.
        double xA
                = x2
                - size
                * Math.cos(
                        angle - Math.PI / 6
                );

        // Calculamos la posición vertical del primer lado.
        double yA
                = y2
                - size
                * Math.sin(
                        angle - Math.PI / 6
                );

        // Calculamos el segundo lado de la punta.
        double xB
                = x2
                - size
                * Math.cos(
                        angle + Math.PI / 6
                );

        // Calculamos la posición vertical del segundo lado.
        double yB
                = y2
                - size
                * Math.sin(
                        angle + Math.PI / 6
                );

        // Dibujamos el primer segmento de la punta.
        gc.strokeLine(
                x2,
                y2,
                xA,
                yA
        );

        // Dibujamos el segundo segmento de la punta.
        gc.strokeLine(
                x2,
                y2,
                xB,
                yB
        );
    }
}
