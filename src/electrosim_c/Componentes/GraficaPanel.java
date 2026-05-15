package electrosim_c.Componentes;

import controlador.GraficaController;
import javafx.collections.FXCollections;
import javafx.scene.chart.*;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import modelo.Carga;

import java.util.List;

public class GraficaPanel extends VBox {

    // Centralizamos la generación de datos físicos que alimentan todas las gráficas.
    private final GraficaController graficaController;

    // Organizamos cada visualización dentro de pestañas para mejorar la navegación.
    private final TabPane tabPane;

    // Representamos la variación del campo eléctrico sobre el eje X.
    private final LineChart<Number, Number> campoChart;

    // Representamos la distribución del potencial eléctrico en el sistema.
    private final LineChart<Number, Number> potencialChart;

    // Representamos el trabajo realizado a lo largo del desplazamiento.
    private final LineChart<Number, Number> trabajoChart;

    // Representamos la fuerza neta de cada carga de forma comparativa.
    private final BarChart<String, Number> fuerzaChart;

    // Construimos el panel encargado de visualizar el comportamiento físico del sistema.
    public GraficaPanel() {

        // Inicializamos el controlador que transforma la simulación en datos graficables.
        graficaController
                = new GraficaController();

        // Creamos el eje horizontal para la gráfica de campo eléctrico.
        NumberAxis campoX
                = new NumberAxis();

        // Creamos el eje vertical para la gráfica de campo eléctrico.
        NumberAxis campoY
                = new NumberAxis();

        // Creamos el eje horizontal para la gráfica de potencial.
        NumberAxis potencialX
                = new NumberAxis();

        // Creamos el eje vertical para la gráfica de potencial.
        NumberAxis potencialY
                = new NumberAxis();

        // Creamos el eje horizontal para la gráfica de trabajo.
        NumberAxis trabajoX
                = new NumberAxis();

        // Creamos el eje vertical para la gráfica de trabajo.
        NumberAxis trabajoY
                = new NumberAxis();

        // Creamos el eje categórico para identificar cada carga.
        CategoryAxis fuerzaX
                = new CategoryAxis();

        // Creamos el eje numérico para la magnitud de la fuerza.
        NumberAxis fuerzaY
                = new NumberAxis();

        // Etiquetamos el eje horizontal de la gráfica de campo.
        campoX.setLabel(
                "Posición X"
        );

        // Etiquetamos el eje vertical de la gráfica de campo.
        campoY.setLabel(
                "Campo Eléctrico (N/C)"
        );

        // Etiquetamos el eje horizontal de la gráfica de potencial.
        potencialX.setLabel(
                "Posición X"
        );

        // Etiquetamos el eje vertical de la gráfica de potencial.
        potencialY.setLabel(
                "Potencial (V)"
        );

        // Etiquetamos el eje horizontal de la gráfica de trabajo.
        trabajoX.setLabel(
                "Posición X"
        );

        // Etiquetamos el eje vertical de la gráfica de trabajo.
        trabajoY.setLabel(
                "Trabajo (J)"
        );

        // Etiquetamos el eje categórico de las fuerzas.
        fuerzaX.setLabel(
                "Cargas"
        );

        // Etiquetamos el eje de magnitudes de fuerza.
        fuerzaY.setLabel(
                "Fuerza Neta (N)"
        );

        // Construimos la gráfica de línea para el campo eléctrico.
        campoChart
                = new LineChart<>(
                        campoX,
                        campoY
                );

        // Construimos la gráfica de línea para el potencial eléctrico.
        potencialChart
                = new LineChart<>(
                        potencialX,
                        potencialY
                );

        // Construimos la gráfica de línea para el trabajo realizado.
        trabajoChart
                = new LineChart<>(
                        trabajoX,
                        trabajoY
                );

        // Construimos la gráfica de barras para comparar fuerzas netas.
        fuerzaChart
                = new BarChart<>(
                        fuerzaX,
                        fuerzaY
                );

        // Aplicamos la configuración visual común a todas las gráficas.
        configurarCharts();

        // Creamos la pestaña dedicada al campo eléctrico.
        Tab campoTab
                = new Tab(
                        "Campo",
                        campoChart
                );

        // Creamos la pestaña dedicada al potencial eléctrico.
        Tab potencialTab
                = new Tab(
                        "Potencial",
                        potencialChart
                );

        // Creamos la pestaña dedicada a las fuerzas netas.
        Tab fuerzaTab
                = new Tab(
                        "Fuerzas",
                        fuerzaChart
                );

        // Creamos la pestaña dedicada al trabajo físico.
        Tab trabajoTab
                = new Tab(
                        "Trabajo",
                        trabajoChart
                );

        // Mantenemos fija la estructura de pestañas del simulador.
        campoTab.setClosable(false);
        potencialTab.setClosable(false);
        fuerzaTab.setClosable(false);
        trabajoTab.setClosable(false);

        // Creamos el contenedor principal de navegación entre gráficas.
        tabPane
                = new TabPane();

        // Incorporamos todas las gráficas disponibles al panel.
        tabPane.getTabs().addAll(
                campoTab,
                potencialTab,
                fuerzaTab,
                trabajoTab
        );

        // Añadimos el sistema de pestañas al contenedor principal.
        getChildren().add(
                tabPane
        );

        // Permitimos que el panel aproveche todo el espacio disponible.
        VBox.setVgrow(
                tabPane,
                Priority.ALWAYS
        );

        // Aplicamos el estilo visual del módulo de resultados.
        getStyleClass().add(
                "result-panel"
        );
    }

    // Aplicamos una configuración visual uniforme para todas las gráficas.
    private void configurarCharts() {

        // Desactivamos animaciones para priorizar rendimiento en tiempo real.
        campoChart.setAnimated(false);
        potencialChart.setAnimated(false);
        trabajoChart.setAnimated(false);
        fuerzaChart.setAnimated(false);

        // Ocultamos leyendas porque cada gráfica representa una única serie.
        campoChart.setLegendVisible(false);
        potencialChart.setLegendVisible(false);
        trabajoChart.setLegendVisible(false);
        fuerzaChart.setLegendVisible(false);

        // Eliminamos marcadores para mantener líneas más limpias.
        campoChart.setCreateSymbols(false);
        potencialChart.setCreateSymbols(false);
        trabajoChart.setCreateSymbols(false);

        // Garantizamos una altura mínima cómoda para visualización.
        campoChart.setMinHeight(420);
        potencialChart.setMinHeight(420);
        trabajoChart.setMinHeight(420);
        fuerzaChart.setMinHeight(420);

        // Unificamos la altura preferida de todas las gráficas.
        campoChart.setPrefHeight(420);
        potencialChart.setPrefHeight(420);
        trabajoChart.setPrefHeight(420);
        fuerzaChart.setPrefHeight(420);

        // Activamos las líneas guía horizontales para facilitar lectura.
        campoChart.setHorizontalGridLinesVisible(true);
        potencialChart.setHorizontalGridLinesVisible(true);
        trabajoChart.setHorizontalGridLinesVisible(true);

        // Activamos las líneas guía verticales para ubicar valores con precisión.
        campoChart.setVerticalGridLinesVisible(true);
        potencialChart.setVerticalGridLinesVisible(true);
        trabajoChart.setVerticalGridLinesVisible(true);
    }

    // Actualizamos todas las gráficas según el estado actual del sistema.
    public void actualizar(
            List<Carga> cargas
    ) {

        // Regeneramos la gráfica de campo eléctrico.
        actualizarCampo(
                cargas
        );

        // Regeneramos la gráfica de potencial eléctrico.
        actualizarPotencial(
                cargas
        );

        // Regeneramos la gráfica de trabajo.
        actualizarTrabajo(
                cargas
        );

        // Regeneramos la gráfica comparativa de fuerzas.
        actualizarFuerzas(
                cargas
        );
    }

    // Reconstruimos la gráfica del campo eléctrico.
    private void actualizarCampo(
            List<Carga> cargas
    ) {

        // Generamos el eje de posiciones para el análisis.
        List<Float> ejeX
                = graficaController.generarEjeX();

        // Calculamos el campo eléctrico sobre cada punto del eje.
        List<Float> datos
                = graficaController.generarCampoX(
                        cargas,
                        0
                );

        // Creamos una nueva serie para poblar la gráfica.
        XYChart.Series<Number, Number> serie
                = new XYChart.Series<>();

        // Incorporamos cada punto calculado a la serie.
        for (int i = 0;
                i < ejeX.size();
                i++) {

            serie.getData().add(
                    new XYChart.Data<>(
                            ejeX.get(i),
                            datos.get(i)
                    )
            );
        }

        // Reemplazamos los datos actuales por la nueva simulación.
        campoChart.setData(
                FXCollections.observableArrayList(
                        serie
                )
        );
    }

    // Reconstruimos la gráfica del potencial eléctrico.
    private void actualizarPotencial(
            List<Carga> cargas
    ) {

        // Generamos el eje base para el recorrido.
        List<Float> ejeX
                = graficaController.generarEjeX();

        // Calculamos el potencial eléctrico en cada posición.
        List<Float> datos
                = graficaController.generarPotencialX(
                        cargas,
                        0
                );

        // Creamos una nueva serie de datos.
        XYChart.Series<Number, Number> serie
                = new XYChart.Series<>();

        // Incorporamos cada punto calculado a la serie.
        for (int i = 0;
                i < ejeX.size();
                i++) {

            serie.getData().add(
                    new XYChart.Data<>(
                            ejeX.get(i),
                            datos.get(i)
                    )
            );
        }

        // Actualizamos la gráfica con la información más reciente.
        potencialChart.setData(
                FXCollections.observableArrayList(
                        serie
                )
        );
    }

    // Reconstruimos la gráfica del trabajo realizado.
    private void actualizarTrabajo(
            List<Carga> cargas
    ) {

        // Generamos el eje base para el análisis.
        List<Float> ejeX
                = graficaController.generarEjeX();

        // Calculamos el trabajo entre los puntos definidos.
        List<Float> datos
                = graficaController.generarTrabajoX(
                        cargas,
                        0,
                        1
                );

        // Creamos la serie que representará el comportamiento.
        XYChart.Series<Number, Number> serie
                = new XYChart.Series<>();

        // Incorporamos cada punto calculado a la serie.
        for (int i = 0;
                i < ejeX.size();
                i++) {

            serie.getData().add(
                    new XYChart.Data<>(
                            ejeX.get(i),
                            datos.get(i)
                    )
            );
        }

        // Actualizamos la gráfica con la nueva simulación.
        trabajoChart.setData(
                FXCollections.observableArrayList(
                        serie
                )
        );
    }

    // Reconstruimos la gráfica comparativa de fuerzas netas.
    private void actualizarFuerzas(
            List<Carga> cargas
    ) {

        // Calculamos la fuerza neta individual de cada carga.
        List<Float> datos
                = graficaController.generarFuerzaNeta(
                        cargas
                );

        // Creamos una serie categórica para las barras.
        XYChart.Series<String, Number> serie
                = new XYChart.Series<>();

        // Asociamos cada fuerza con su identificador visual.
        for (int i = 0;
                i < datos.size();
                i++) {

            serie.getData().add(
                    new XYChart.Data<>(
                            "Q" + (i + 1),
                            datos.get(i)
                    )
            );
        }

        // Actualizamos la gráfica de barras con los nuevos datos.
        fuerzaChart.setData(
                FXCollections.observableArrayList(
                        serie
                )
        );
    }

    // Retornamos la gráfica del campo para exportación o captura.
    public javafx.scene.chart.Chart getCampoChart() {
        return campoChart;
    }

    // Retornamos la gráfica del potencial para exportación o captura.
    public javafx.scene.chart.Chart getPotencialChart() {
        return potencialChart;
    }

    // Retornamos la gráfica de fuerzas para exportación o captura.
    public javafx.scene.chart.Chart getFuerzasChart() {
        return fuerzaChart;
    }

    // Retornamos la gráfica del trabajo para exportación o captura.
    public javafx.scene.chart.Chart getTrabajoChart() {
        return trabajoChart;
    }
}
