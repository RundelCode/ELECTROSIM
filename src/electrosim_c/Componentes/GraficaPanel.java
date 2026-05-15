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

    private final GraficaController graficaController;

    private final TabPane tabPane;

    private final LineChart<Number, Number> campoChart;
    private final LineChart<Number, Number> potencialChart;
    private final LineChart<Number, Number> trabajoChart;
    private final BarChart<String, Number> fuerzaChart;

    public GraficaPanel() {

        graficaController
                = new GraficaController();

        NumberAxis campoX
                = new NumberAxis();

        NumberAxis campoY
                = new NumberAxis();

        NumberAxis potencialX
                = new NumberAxis();

        NumberAxis potencialY
                = new NumberAxis();

        NumberAxis trabajoX
                = new NumberAxis();

        NumberAxis trabajoY
                = new NumberAxis();

        CategoryAxis fuerzaX
                = new CategoryAxis();

        NumberAxis fuerzaY
                = new NumberAxis();

        campoX.setLabel(
                "Posición X"
        );

        campoY.setLabel(
                "Campo Eléctrico (N/C)"
        );

        potencialX.setLabel(
                "Posición X"
        );

        potencialY.setLabel(
                "Potencial (V)"
        );

        trabajoX.setLabel(
                "Posición X"
        );

        trabajoY.setLabel(
                "Trabajo (J)"
        );

        fuerzaX.setLabel(
                "Cargas"
        );

        fuerzaY.setLabel(
                "Fuerza Neta (N)"
        );

        campoChart
                = new LineChart<>(
                        campoX,
                        campoY
                );

        potencialChart
                = new LineChart<>(
                        potencialX,
                        potencialY
                );

        trabajoChart
                = new LineChart<>(
                        trabajoX,
                        trabajoY
                );

        fuerzaChart
                = new BarChart<>(
                        fuerzaX,
                        fuerzaY
                );

        configurarCharts();

        Tab campoTab
                = new Tab(
                        "Campo",
                        campoChart
                );

        Tab potencialTab
                = new Tab(
                        "Potencial",
                        potencialChart
                );

        Tab fuerzaTab
                = new Tab(
                        "Fuerzas",
                        fuerzaChart
                );

        Tab trabajoTab
                = new Tab(
                        "Trabajo",
                        trabajoChart
                );

        campoTab.setClosable(false);
        potencialTab.setClosable(false);
        fuerzaTab.setClosable(false);
        trabajoTab.setClosable(false);

        tabPane
                = new TabPane();

        tabPane.getTabs().addAll(
                campoTab,
                potencialTab,
                fuerzaTab,
                trabajoTab
        );

        getChildren().add(
                tabPane
        );

        VBox.setVgrow(
                tabPane,
                Priority.ALWAYS
        );

        getStyleClass().add(
                "result-panel"
        );
    }

    private void configurarCharts() {

        campoChart.setAnimated(false);
        potencialChart.setAnimated(false);
        trabajoChart.setAnimated(false);
        fuerzaChart.setAnimated(false);

        campoChart.setLegendVisible(false);
        potencialChart.setLegendVisible(false);
        trabajoChart.setLegendVisible(false);
        fuerzaChart.setLegendVisible(false);

        campoChart.setCreateSymbols(false);
        potencialChart.setCreateSymbols(false);
        trabajoChart.setCreateSymbols(false);

        campoChart.setMinHeight(420);
        potencialChart.setMinHeight(420);
        trabajoChart.setMinHeight(420);
        fuerzaChart.setMinHeight(420);

        campoChart.setPrefHeight(420);
        potencialChart.setPrefHeight(420);
        trabajoChart.setPrefHeight(420);
        fuerzaChart.setPrefHeight(420);

        campoChart.setHorizontalGridLinesVisible(true);
        potencialChart.setHorizontalGridLinesVisible(true);
        trabajoChart.setHorizontalGridLinesVisible(true);

        campoChart.setVerticalGridLinesVisible(true);
        potencialChart.setVerticalGridLinesVisible(true);
        trabajoChart.setVerticalGridLinesVisible(true);
    }

    public void actualizar(
            List<Carga> cargas
    ) {

        actualizarCampo(
                cargas
        );

        actualizarPotencial(
                cargas
        );

        actualizarTrabajo(
                cargas
        );

        actualizarFuerzas(
                cargas
        );
    }

    private void actualizarCampo(
            List<Carga> cargas
    ) {

        List<Float> ejeX
                = graficaController.generarEjeX();

        List<Float> datos
                = graficaController.generarCampoX(
                        cargas,
                        0
                );

        XYChart.Series<Number, Number> serie
                = new XYChart.Series<>();

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

        campoChart.setData(
                FXCollections.observableArrayList(
                        serie
                )
        );
    }

    private void actualizarPotencial(
            List<Carga> cargas
    ) {

        List<Float> ejeX
                = graficaController.generarEjeX();

        List<Float> datos
                = graficaController.generarPotencialX(
                        cargas,
                        0
                );

        XYChart.Series<Number, Number> serie
                = new XYChart.Series<>();

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

        potencialChart.setData(
                FXCollections.observableArrayList(
                        serie
                )
        );
    }

    private void actualizarTrabajo(
            List<Carga> cargas
    ) {

        List<Float> ejeX
                = graficaController.generarEjeX();

        List<Float> datos
                = graficaController.generarTrabajoX(
                        cargas,
                        0,
                        1
                );

        XYChart.Series<Number, Number> serie
                = new XYChart.Series<>();

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

        trabajoChart.setData(
                FXCollections.observableArrayList(
                        serie
                )
        );
    }

    private void actualizarFuerzas(
            List<Carga> cargas
    ) {

        List<Float> datos
                = graficaController.generarFuerzaNeta(
                        cargas
                );

        XYChart.Series<String, Number> serie
                = new XYChart.Series<>();

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

        fuerzaChart.setData(
                FXCollections.observableArrayList(
                        serie
                )
        );
    }

    public javafx.scene.chart.Chart getCampoChart() {
        return campoChart;
    }

    public javafx.scene.chart.Chart getPotencialChart() {
        return potencialChart;
    }

    public javafx.scene.chart.Chart getFuerzasChart() {
        return fuerzaChart;
    }

    public javafx.scene.chart.Chart getTrabajoChart() {
        return trabajoChart;
    }
}
