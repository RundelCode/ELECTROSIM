package electrosim_c.Vistas;

import electrosim_c.Componentes.BitacoraPanel;
import electrosim_c.Componentes.CargaCard;
import electrosim_c.Componentes.GraficaPanel;
import electrosim_c.Componentes.Header;
import electrosim_c.Componentes.ResultadoPanel;
import electrosim_c.Interacciones.CanvasInteractionHandler;
import electrosim_c.Render.CargaRenderer;
import electrosim_c.Render.FuerzaRenderer;
import electrosim_c.Render.PlanoRenderer;
import controlador.FuerzaController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import modelo.Carga;
import controlador.RegistroController;
import modelo.Registro;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.List;

public class Simulador {

    private BorderPane root;
    private Canvas canvas;
    private GridPane listaCargas;

    private GraficaPanel graficaPanel;
    private ResultadoPanel resultadoPanel;
    private BitacoraPanel bitacoraPanel;

    private final List<Carga> cargas;

    private final FuerzaController fuerzaController;
    private final RegistroController registroController;

    private final PlanoRenderer planoRenderer;
    private final CargaRenderer cargaRenderer;
    private final FuerzaRenderer fuerzaRenderer;

    private CanvasInteractionHandler interactionHandler;

    private final double RADIO_CARGA = 16;

    public Simulador(
            Runnable goBack
    ) {

        cargas
                = new ArrayList<>();

        fuerzaController
                = new FuerzaController();

        registroController
                = new RegistroController();

        planoRenderer
                = new PlanoRenderer();

        cargaRenderer
                = new CargaRenderer(
                        RADIO_CARGA
                );

        fuerzaRenderer
                = new FuerzaRenderer();

        construirVista(
                goBack
        );

        configurarEventos();
        configurarBitacora();

        Platform.runLater(
                this::renderAll
        );
    }

    private void construirVista(
            Runnable goBack
    ) {

        root
                = new BorderPane();

        root.getStyleClass().add(
                "contenedor"
        );

        Header header
                = new Header(
                        "Agrega las cargas al simulador, define su posición en el espacio y ajusta su magnitud según el caso que desees analizar;"
                                + " observa cómo cambian las fuerzas eléctricas entre ellas en tiempo real según la Ley de Coulomb. Para una mejor"
                                + " comprensión de los resultados, se recomienda revisar la fundamentación teórica disponible en la aplicación.",
                        goBack
                );

        root.setTop(
                header.getView()
        );

        canvas
                = new Canvas();

        canvas.getStyleClass().add(
                "canvas-simulador"
        );

        canvas.setHeight(
                530
        );

        listaCargas
                = new GridPane();

        listaCargas.setHgap(
                20
        );

        listaCargas.setVgap(
                20
        );

        ColumnConstraints col1
                = new ColumnConstraints();

        ColumnConstraints col2
                = new ColumnConstraints();

        col1.setPercentWidth(
                50
        );

        col2.setPercentWidth(
                50
        );

        listaCargas.getColumnConstraints().addAll(
                col1,
                col2
        );

        ScrollPane scroll
                = new ScrollPane(
                        listaCargas
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

        Button btnAgregar
                = new Button(
                        "AGREGAR"
                );

        Button btnLimpiar
                = new Button(
                        "LIMPIAR"
                );

        btnAgregar.getStyleClass().add(
                "btn-primary"
        );

        btnLimpiar.getStyleClass().add(
                "btn-primary"
        );

        HBox acciones
                = new HBox(
                        12,
                        btnAgregar,
                        btnLimpiar
                );

        btnAgregar.setMaxWidth(
                Double.MAX_VALUE
        );

        btnLimpiar.setMaxWidth(
                Double.MAX_VALUE
        );

        HBox.setHgrow(
                btnAgregar,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                btnLimpiar,
                Priority.ALWAYS
        );

        btnAgregar.prefWidthProperty().bind(
                acciones.widthProperty()
                        .multiply(0.48)
        );

        btnLimpiar.prefWidthProperty().bind(
                acciones.widthProperty()
                        .multiply(0.48)
        );

        VBox panelCargas
                = new VBox(
                        15,
                        acciones,
                        scroll
                );

        panelCargas.getStyleClass().add(
                "panel-lateral"
        );

        graficaPanel
                = new GraficaPanel();

        resultadoPanel
                = new ResultadoPanel();

        bitacoraPanel
                = new BitacoraPanel();

        VBox panelDerechoInferior
                = new VBox(
                        15,
                        resultadoPanel,
                        bitacoraPanel
                );

        VBox.setVgrow(
                resultadoPanel,
                Priority.ALWAYS
        );

        VBox.setVgrow(
                bitacoraPanel,
                Priority.ALWAYS
        );

        resultadoPanel.prefHeightProperty().bind(
                panelDerechoInferior.heightProperty()
                        .multiply(0.45)
        );

        bitacoraPanel.prefHeightProperty().bind(
                panelDerechoInferior.heightProperty()
                        .multiply(0.55)
        );

        HBox filaSuperior
                = new HBox(
                        20,
                        canvas,
                        panelCargas
                );

        HBox filaInferior
                = new HBox(
                        20,
                        graficaPanel,
                        panelDerechoInferior
                );

        VBox dashboard
                = new VBox(
                        20,
                        filaSuperior,
                        filaInferior
                );

        dashboard.setPadding(
                new Insets(
                        20
                )
        );

        HBox.setHgrow(
                canvas,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                panelCargas,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                graficaPanel,
                Priority.ALWAYS
        );

        HBox.setHgrow(
                panelDerechoInferior,
                Priority.ALWAYS
        );

        canvas.widthProperty().bind(
                filaSuperior.widthProperty()
                        .multiply(0.60)
        );

        panelCargas.prefWidthProperty().bind(
                filaSuperior.widthProperty()
                        .multiply(0.40)
        );

        graficaPanel.prefWidthProperty().bind(
                filaInferior.widthProperty()
                        .multiply(0.60)
        );

        panelDerechoInferior.prefWidthProperty().bind(
                filaInferior.widthProperty()
                        .multiply(0.40)
        );

        root.setCenter(
                dashboard
        );

        btnAgregar.setOnAction(
                e -> agregarCarga()
        );

        btnLimpiar.setOnAction(
                e -> limpiar()
        );
    }

    private void configurarEventos() {

        interactionHandler
                = new CanvasInteractionHandler(
                        canvas,
                        cargas,
                        fuerzaController,
                        RADIO_CARGA
                );

        interactionHandler.configurar(
                this::renderAll
        );

        canvas.widthProperty().addListener(
                (obs, o, n)
                -> renderAll()
        );

        canvas.heightProperty().addListener(
                (obs, o, n)
                -> renderAll()
        );
    }

    private void agregarCarga() {

        if (cargas.size() >= 4) {
            return;
        }

        float x
                = (float) (Math.random() * 12 - 6);

        float y
                = (float) (Math.random() * 12 - 6);

        float magnitud
                = Math.random() > 0.5
                ? 5
                : -5;

        cargas.add(
                new Carga(
                        x,
                        y,
                        magnitud,
                        1
                )
        );

        fuerzaController.calcularFuerzas(
                cargas
        );

        renderAll();
    }

    private void limpiar() {

        cargas.clear();

        fuerzaController.calcularFuerzas(
                cargas
        );

        renderAll();
    }

    private void renderAll() {

        if (canvas.getWidth() <= 0
                || canvas.getHeight() <= 0) {
            return;
        }

        render();

        renderPanel();

        graficaPanel.actualizar(
                cargas
        );

        resultadoPanel.actualizar(
                cargas
        );
    }

    private void render() {

        GraphicsContext gc
                = canvas.getGraphicsContext2D();

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

        planoRenderer.render(
                gc,
                width,
                height,
                centerX,
                centerY,
                spacingX,
                spacingY
        );

        cargaRenderer.renderCampoElectrico(
                gc,
                cargas,
                centerX,
                centerY,
                spacingX,
                spacingY
        );

        fuerzaRenderer.renderInteracciones(
                gc,
                fuerzaController.getFuerzas(),
                centerX,
                centerY,
                spacingX,
                spacingY
        );

        cargaRenderer.renderCargas(
                gc,
                cargas,
                interactionHandler.getCargaSeleccionada(),
                centerX,
                centerY,
                spacingX,
                spacingY
        );

        fuerzaRenderer.renderEtiquetas(
                gc,
                fuerzaController.getFuerzas(),
                centerX,
                centerY,
                spacingX,
                spacingY
        );
    }

    private void renderPanel() {

        listaCargas.getChildren().clear();

        for (int i = 0;
                i < cargas.size();
                i++) {

            Carga carga
                    = cargas.get(i);

            CargaCard card
                    = new CargaCard(
                            i,
                            carga,
                            () -> {

                                cargas.remove(
                                        carga
                                );

                                fuerzaController.calcularFuerzas(
                                        cargas
                                );

                                renderAll();
                            },
                            () -> {

                                fuerzaController.calcularFuerzas(
                                        cargas
                                );

                                renderAll();
                            }
                    );

            listaCargas.add(
                    card.getView(),
                    i % 2,
                    i / 2
            );
        }
    }

    public BorderPane getView() {

        return root;
    }

    private void configurarBitacora() {

        bitacoraPanel.getGuardarButton().setOnAction(
                e -> {

                    String usuario
                    = bitacoraPanel
                            .getNombreField()
                            .getText()
                            .trim();

                    if (usuario.isEmpty()) {
                        return;
                    }

                    String titulo
                    = bitacoraPanel
                            .getTituloField()
                            .getText()
                            .trim();

                    String descripcion
                    = bitacoraPanel
                            .getNotasArea()
                            .getText()
                            .trim();

                    Registro registro
                    = new Registro(
                            usuario,
                            titulo,
                            descripcion,
                            new ArrayList<>()
                    );

                    registroController.guardarSimulacion(
                            registro,
                            cargas,
                            canvas,
                            graficaPanel.getCampoChart(),
                            graficaPanel.getPotencialChart(),
                            graficaPanel.getFuerzasChart(),
                            graficaPanel.getTrabajoChart()
                    );

                    Button boton
                    = bitacoraPanel.getGuardarButton();

                    boton.setText(
                            "✓ SIMULACIÓN GUARDADA"
                    );

                    boton.setStyle(
                            "-fx-background-color:#2e8b57;"
                            + "-fx-text-fill:white;"
                            + "-fx-font-size:16px;"
                            + "-fx-font-weight:bold;"
                            + "-fx-background-radius:14;"
                    );

                    javafx.animation.PauseTransition pausa
                    = new javafx.animation.PauseTransition(
                            javafx.util.Duration.seconds(
                                    2
                            )
                    );

                    pausa.setOnFinished(
                            event -> {

                                boton.setText(
                                        "GUARDAR RESULTADOS DE LA SIMULACIÓN"
                                );

                                boton.setStyle(
                                        ""
                                );
                            }
                    );

                    pausa.play();
                }
        );
    }
}
