package electrosim_c.Componentes;

import controlador.GraficaController;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import modelo.Carga;

import java.util.List;

public class ResultadoPanel extends VBox {

    private final GraficaController controller;

    private final TabPane tabPane;

    public ResultadoPanel() {

        controller
                = new GraficaController();

        getStyleClass().add(
                "result-panel"
        );

        tabPane
                = new TabPane();

        tabPane.setTabClosingPolicy(
                TabPane.TabClosingPolicy.UNAVAILABLE
        );

        setMinHeight(
                0
        );

        setPrefHeight(
                260
        );

        setMaxHeight(
                260
        );

        tabPane.setMinHeight(
                0
        );

        tabPane.setPrefHeight(
                260
        );

        tabPane.setMaxHeight(
                260
        );

        getChildren().add(
                tabPane
        );

        VBox.setVgrow(
                tabPane,
                Priority.ALWAYS
        );
    }

    public void actualizar(
            List<Carga> cargas
    ) {

        tabPane.getTabs().clear();

        crearTabSistema(
                cargas
        );

        for (int i = 0;
                i < cargas.size();
                i++) {

            crearTabCarga(
                    i,
                    cargas
            );
        }
    }

    private void crearTabSistema(
            List<Carga> cargas
    ) {

        List<Float> fuerzas
                = controller.generarFuerzaNeta(
                        cargas
                );

        float total = 0;

        for (Float f : fuerzas) {
            total += f;
        }

        VBox contenido
                = new VBox(
                        12
                );

        contenido.getChildren().addAll(
                texto(
                        "Cargas:"
                ),
                texto(
                        String.valueOf(
                                cargas.size()
                        )
                ),
                texto(
                        "Interacciones:"
                ),
                texto(
                        String.valueOf(
                                cargas.size()
                                * (cargas.size()
                                - 1)
                                / 2
                        )
                ),
                texto(
                        "Fuerza neta total:"
                ),
                texto(
                        String.format(
                                "%.2f N",
                                total
                        )
                )
        );

        ScrollPane scroll
                = crearScroll(
                        contenido
                );

        Tab tab
                = new Tab(
                        "Sistema",
                        scroll
                );

        tabPane.getTabs().add(
                tab
        );
    }

    private void crearTabCarga(
            int index,
            List<Carga> cargas
    ) {

        Carga c
                = cargas.get(
                        index
                );

        List<Float> fuerzas
                = controller.generarFuerzaNeta(
                        cargas
                );

        VBox contenido
                = new VBox(
                        12
                );

        contenido.getChildren().addAll(
                texto(
                        "Carga:"
                ),
                texto(
                        c.getCarga()
                        + " C"
                ),
                texto(
                        "Posición:"
                ),
                texto(
                        "("
                        + String.format(
                                "%.2f",
                                c.getPosicionX()
                        )
                        + ", "
                        + String.format(
                                "%.2f",
                                c.getPosicionY()
                        )
                        + ")"
                ),
                texto(
                        "Fuerza neta:"
                ),
                texto(
                        String.format(
                                "%.2f N",
                                fuerzas.get(
                                        index
                                )
                        )
                )
        );

        ScrollPane scroll
                = crearScroll(
                        contenido
                );

        Tab tab
                = new Tab(
                        "Q" + (index + 1),
                        scroll
                );

        tabPane.getTabs().add(
                tab
        );
    }

    private ScrollPane crearScroll(
            VBox contenido
    ) {

        ScrollPane scroll
                = new ScrollPane(
                        contenido
                );

        scroll.setFitToWidth(
                true
        );

        scroll.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scroll.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: transparent;"
        );

        return scroll;
    }

    private Text texto(
            String value
    ) {

        Text text
                = new Text(
                        value
                );

        text.getStyleClass().add(
                "result-value"
        );

        return text;
    }
}
