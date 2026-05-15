package electrosim_c.Render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import modelo.Carga;

import java.util.List;

public class CargaRenderer {

    // Definimos el radio visual que tendrán todas las cargas dentro del canvas.
    private final double radioCarga;

    // Construimos el renderizador especializado en representar cargas eléctricas.
    public CargaRenderer(
            double radioCarga
    ) {

        // Conservamos el tamaño base para mantener una representación visual consistente.
        this.radioCarga = radioCarga;
    }

    // Dibujamos el efecto visual del campo eléctrico alrededor de cada carga activa.
    public void renderCampoElectrico(
            GraphicsContext gc,
            List<Carga> cargas,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        // Recorremos cada carga del sistema para proyectar su influencia visual.
        for (Carga c : cargas) {

            // Convertimos la posición física de la carga al eje X del canvas.
            double x
                    = centerX
                    + c.getPosicionX() * spacingX;

            // Convertimos la posición física de la carga al eje Y del canvas.
            double y
                    = centerY
                    - c.getPosicionY() * spacingY;

            // Obtenemos la magnitud absoluta para calcular la intensidad del efecto.
            float magnitud
                    = Math.abs(
                            c.getCarga()
                    );

            // Seleccionamos un color según la polaridad de la carga.
            Color color
                    = c.getCarga() > 0
                    ? Color.web("#ff4530")
                    : Color.web("#3aa0ff");

            // Ajustamos la cantidad de capas para que cargas mayores tengan más presencia.
            int capas
                    = (int) Math.max(
                            4,
                            magnitud
                    );

            // Definimos el tamaño inicial del resplandor según la intensidad de la carga.
            double radioBase
                    = 28 + magnitud * 3;

            // Calculamos la transparencia base para suavizar el efecto visual.
            double alphaBase
                    = 0.012 + magnitud * 0.003;

            // Generamos múltiples capas para simular la expansión del campo eléctrico.
            for (int i = 0;
                    i < capas;
                    i++) {

                // Expandimos progresivamente el radio de cada capa.
                double radius
                        = radioBase + i * 20;

                // Reducimos la opacidad en cada capa para lograr profundidad visual.
                double alpha
                        = alphaBase - i * 0.002;

                // Garantizamos una transparencia mínima para que el efecto siga visible.
                alpha
                        = Math.max(
                                0.008,
                                alpha
                        );

                // Aplicamos la opacidad calculada antes de dibujar la capa.
                gc.setGlobalAlpha(
                        alpha
                );

                // Configuramos el color correspondiente al campo de esta carga.
                gc.setFill(
                        color
                );

                // Dibujamos una capa circular del campo eléctrico.
                gc.fillOval(
                        x - radius,
                        y - radius,
                        radius * 2,
                        radius * 2
                );
            }
        }

        // Restauramos la opacidad global para no afectar otros renderizados.
        gc.setGlobalAlpha(
                1
        );
    }

    // Dibujamos cada carga del sistema con su estilo visual e identificación.
    public void renderCargas(
            GraphicsContext gc,
            List<Carga> cargas,
            Carga cargaSeleccionada,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        // Recorremos todas las cargas para renderizarlas individualmente.
        for (int i = 0;
                i < cargas.size();
                i++) {

            // Recuperamos la carga actual dentro del recorrido.
            Carga c
                    = cargas.get(i);

            // Transformamos la coordenada física al eje X visual.
            double x
                    = centerX
                    + c.getPosicionX() * spacingX;

            // Transformamos la coordenada física al eje Y visual.
            double y
                    = centerY
                    - c.getPosicionY() * spacingY;

            // Definimos el color del resplandor exterior según la polaridad.
            Color glow
                    = c.getCarga() > 0
                    ? Color.web("#ff7849")
                    : Color.web("#4fc3ff");

            // Definimos el color principal del núcleo de la carga.
            Color core
                    = c.getCarga() > 0
                    ? Color.web("#ff3b30")
                    : Color.web("#007aff");

            // Destacamos visualmente la carga que el usuario tenga seleccionada.
            if (c == cargaSeleccionada) {

                // Aplicamos una tonalidad más clara para resaltar cargas positivas.
                glow
                        = c.getCarga() > 0
                        ? Color.web("#ffb199")
                        : Color.web("#8ed8ff");
            }

            // Reducimos la opacidad para construir el halo exterior.
            gc.setGlobalAlpha(
                    0.18
            );

            // Aplicamos el color del resplandor antes de dibujarlo.
            gc.setFill(
                    glow
            );

            // Dibujamos el halo externo para dar sensación de energía.
            gc.fillOval(
                    x - radioCarga - 10,
                    y - radioCarga - 10,
                    (radioCarga + 10) * 2,
                    (radioCarga + 10) * 2
            );

            // Restauramos la opacidad para el núcleo principal.
            gc.setGlobalAlpha(
                    1
            );

            // Configuramos el color base de la carga.
            gc.setFill(
                    core
            );

            // Dibujamos el cuerpo principal de la carga.
            gc.fillOval(
                    x - radioCarga,
                    y - radioCarga,
                    radioCarga * 2,
                    radioCarga * 2
            );

            // Configuramos el color del texto identificador.
            gc.setFill(
                    Color.WHITE
            );

            // Centramos el texto para mantener la etiqueta dentro del círculo.
            gc.setTextAlign(
                    javafx.scene.text.TextAlignment.CENTER
            );

            // Aplicamos una fuente legible para identificar fácilmente cada carga.
            gc.setFont(
                    javafx.scene.text.Font.font(
                            "System",
                            javafx.scene.text.FontWeight.BOLD,
                            11
                    )
            );

            // Etiquetamos visualmente cada carga para facilitar la interacción.
            gc.fillText(
                    "Q" + (i + 1),
                    x,
                    y + 4
            );
        }
    }
}
