package electrosim_c.Vistas;

import electrosim_c.Componentes.CargaCard;
import electrosim_c.Componentes.Header;
import controlador.FuerzaController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import modelo.Carga;
import modelo.Fuerza;

import java.util.ArrayList;
import java.util.List;

public class Simulador {

    private BorderPane root;
    private Canvas canvas;
    private GridPane listaCargas;

    private List<Carga> cargas = new ArrayList<>();

    private FuerzaController fuerzaController;

    private Carga cargaSeleccionada;

    private final double RADIO_CARGA = 16;

    public Simulador(Runnable goBack) {

        fuerzaController = new FuerzaController();

        root = new BorderPane();
        root.getStyleClass().add("contenedor");

        Header header = new Header(
                "Simula fuerzas eléctricas entre cargas",
                goBack
        );

        root.setTop(header.getView());

        canvas = new Canvas();
        canvas.getStyleClass().add("canvas-simulador");

        HBox mainContent = new HBox();
        mainContent.setPadding(new Insets(20));

        HBox canvasContainer = new HBox();
        canvasContainer.setAlignment(Pos.CENTER);

        VBox panelDerecho = new VBox();
        panelDerecho.setSpacing(15);
        panelDerecho.getStyleClass().add("panel-lateral");

        Button btnAgregar = new Button("AGREGAR");
        Button btnLimpiar = new Button("LIMPIAR");

        btnAgregar.getStyleClass().add("btn-primary");
        btnLimpiar.getStyleClass().add("btn-primary");

        HBox acciones = new HBox(10, btnAgregar, btnLimpiar);

        listaCargas = new GridPane();
        listaCargas.setHgap(20);
        listaCargas.setVgap(20);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);

        listaCargas.getColumnConstraints().addAll(col1, col2);

        ScrollPane scroll = new ScrollPane(listaCargas);

        scroll.setFitToWidth(true);

        scroll.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        scroll.setPrefHeight(440);
        scroll.setMaxHeight(440);
        scroll.setMinHeight(440);

        scroll.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: transparent;"
        );

        panelDerecho.getChildren().addAll(
                acciones,
                scroll
        );

        canvasContainer.prefWidthProperty().bind(
                mainContent.widthProperty().multiply(0.6)
        );

        panelDerecho.prefWidthProperty().bind(
                mainContent.widthProperty().multiply(0.4)
        );

        panelDerecho.setMinWidth(350);

        canvas.widthProperty().bind(
                canvasContainer.widthProperty()
        );

        canvas.setHeight(530);

        canvas.widthProperty().addListener(
                (obs, o, n) -> renderAll()
        );

        canvas.heightProperty().addListener(
                (obs, o, n) -> renderAll()
        );

        canvasContainer.getChildren().add(canvas);

        mainContent.getChildren().addAll(
                canvasContainer,
                panelDerecho
        );

        root.setCenter(mainContent);

        btnAgregar.setOnAction(e -> agregarCarga());

        btnLimpiar.setOnAction(e -> limpiar());

        configurarEventosMouse();

        Platform.runLater(this::renderAll);
    }

    private void configurarEventosMouse() {

        canvas.setOnMousePressed(event -> {

            double mouseX = event.getX();
            double mouseY = event.getY();

            double w = canvas.getWidth();
            double h = canvas.getHeight();

            double centerX = w / 2;
            double centerY = h / 2;

            double spacingX = w / 20;
            double spacingY = h / 20;

            for (Carga c : cargas) {

                double x
                        = centerX
                        + c.getPosicionX() * spacingX;

                double y
                        = centerY
                        - c.getPosicionY() * spacingY;

                double dx = mouseX - x;
                double dy = mouseY - y;

                double distancia
                        = Math.sqrt(dx * dx + dy * dy);

                if (distancia <= RADIO_CARGA + 6) {

                    cargaSeleccionada = c;

                    break;
                }
            }
        });

        canvas.setOnMouseDragged(event -> {

            if (cargaSeleccionada == null) {
                return;
            }

            double mouseX = event.getX();
            double mouseY = event.getY();

            double w = canvas.getWidth();
            double h = canvas.getHeight();

            double centerX = w / 2;
            double centerY = h / 2;

            double spacingX = w / 20;
            double spacingY = h / 20;

            float nuevaX
                    = (float) ((mouseX - centerX) / spacingX);

            float nuevaY
                    = (float) ((centerY - mouseY) / spacingY);

            nuevaX = Math.max(-9, Math.min(9, nuevaX));
            nuevaY = Math.max(-9, Math.min(9, nuevaY));

            cargaSeleccionada.setPosicionX(nuevaX);
            cargaSeleccionada.setPosicionY(nuevaY);

            fuerzaController.calcularFuerzas(cargas);

            renderAll();
        });

        canvas.setOnMouseReleased(event -> {

            cargaSeleccionada = null;
        });
    }

    private void agregarCarga() {

        if (cargas.size() >= 4) {
            return;
        }

        float x = (float) (Math.random() * 12 - 6);
        float y = (float) (Math.random() * 12 - 6);

        x = Math.max(-8, Math.min(8, x));
        y = Math.max(-8, Math.min(8, y));

        float magnitud
                = Math.random() > 0.5 ? 5 : -5;

        Carga c = new Carga(
                x,
                y,
                magnitud,
                1
        );

        cargas.add(c);

        fuerzaController.calcularFuerzas(cargas);

        renderAll();
    }

    private void limpiar() {

        cargas.clear();

        fuerzaController.calcularFuerzas(cargas);

        renderAll();
    }

    private void renderAll() {

        if (canvas.getWidth() <= 0
                || canvas.getHeight() <= 0) {
            return;
        }

        render();
        renderPanel(cargas);
    }

    private void render() {

        GraphicsContext gc
                = canvas.getGraphicsContext2D();

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        double centerX = w / 2;
        double centerY = h / 2;

        double spacingX = w / 20;
        double spacingY = h / 20;

        gc.setFill(Color.web("#07090f"));

        gc.fillRect(0, 0, w, h);

        renderGrid(
                gc,
                w,
                h,
                centerX,
                centerY,
                spacingX,
                spacingY
        );

        renderCampoElectrico(
                gc,
                centerX,
                centerY,
                spacingX,
                spacingY
        );

        renderInteracciones(
                gc,
                centerX,
                centerY,
                spacingX,
                spacingY
        );

        renderCargas(
                gc,
                centerX,
                centerY,
                spacingX,
                spacingY
        );

        renderEtiquetas(
                gc,
                centerX,
                centerY,
                spacingX,
                spacingY
        );
    }

    private void renderGrid(
            GraphicsContext gc,
            double w,
            double h,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        gc.setStroke(Color.web("#161925"));

        gc.setLineWidth(1);

        for (int i = -10; i <= 10; i++) {

            double x = centerX + i * spacingX;
            double y = centerY + i * spacingY;

            gc.strokeLine(x, 0, x, h);

            gc.strokeLine(0, y, w, y);
        }

        gc.setStroke(Color.web("#2f3445"));

        gc.setLineWidth(2);

        gc.strokeLine(centerX, 0, centerX, h);

        gc.strokeLine(0, centerY, w, centerY);
    }

    private void renderCampoElectrico(
            GraphicsContext gc,
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
                    = Math.abs(c.getCarga());

            Color color = c.getCarga() > 0
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

            for (int i = 0; i < capas; i++) {

                double radius
                        = radioBase + i * 20;

                double alpha
                        = alphaBase - i * 0.002;

                alpha = Math.max(
                        0.008,
                        alpha
                );

                gc.setGlobalAlpha(alpha);

                gc.setFill(color);

                gc.fillOval(
                        x - radius,
                        y - radius,
                        radius * 2,
                        radius * 2
                );
            }
        }

        gc.setGlobalAlpha(1);
    }

    private void renderInteracciones(
            GraphicsContext gc,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        for (Fuerza fuerza
                : fuerzaController.getFuerzas()) {

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

            gc.setStroke(color);

            gc.setGlobalAlpha(0.75);

            gc.setLineWidth(2);

            gc.strokeLine(x1, y1, x2, y2);

            gc.setGlobalAlpha(1);

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

    private void renderCargas(
            GraphicsContext gc,
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

            double radius = RADIO_CARGA;

            Color glow
                    = c.getCarga() > 0
                    ? Color.web("#ff7849")
                    : Color.web("#4fc3ff");

            Color core
                    = c.getCarga() > 0
                    ? Color.web("#ff3b30")
                    : Color.web("#007aff");

            if (c == cargaSeleccionada) {

                glow = c.getCarga() > 0
                        ? Color.web("#ffb199")
                        : Color.web("#8ed8ff");
            }

            gc.setGlobalAlpha(0.18);

            gc.setFill(glow);

            gc.fillOval(
                    x - radius - 10,
                    y - radius - 10,
                    (radius + 10) * 2,
                    (radius + 10) * 2
            );

            gc.setGlobalAlpha(1);

            gc.setFill(core);

            gc.fillOval(
                    x - radius,
                    y - radius,
                    radius * 2,
                    radius * 2
            );

            gc.setFill(Color.WHITE);

            gc.setTextAlign(
                    javafx.scene.text.TextAlignment.CENTER
            );

            gc.setFont(
                    javafx.scene.text.Font.font(
                            "System",
                            javafx.scene.text.FontWeight.BOLD,
                            20
                    )
            );

            gc.fillText(
                    c.getCarga() > 0 ? "+" : "-",
                    x,
                    y + 7
            );
        }
    }

    private void renderEtiquetas(
            GraphicsContext gc,
            double centerX,
            double centerY,
            double spacingX,
            double spacingY
    ) {

        for (Fuerza fuerza
                : fuerzaController.getFuerzas()) {

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

            double mx = (x1 + x2) / 2;
            double my = (y1 + y2) / 2;

            double ancho = 78;
            double alto = 34;

            gc.setGlobalAlpha(0.38);

            gc.setFill(Color.web("#0d111c"));

            gc.fillRoundRect(
                    mx - ancho / 2,
                    my - alto / 2,
                    ancho,
                    alto,
                    12,
                    12
            );

            gc.setGlobalAlpha(0.55);

            gc.setStroke(Color.web("#7380a8"));

            gc.setLineWidth(1);

            gc.strokeRoundRect(
                    mx - ancho / 2,
                    my - alto / 2,
                    ancho,
                    alto,
                    12,
                    12
            );

            gc.setGlobalAlpha(1);

            gc.setTextAlign(
                    javafx.scene.text.TextAlignment.CENTER
            );

            gc.setFill(Color.WHITE);

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

            gc.setFill(Color.web("#c4cee8"));

            gc.setFont(
                    javafx.scene.text.Font.font(
                            "System",
                            javafx.scene.text.FontWeight.NORMAL,
                            8
                    )
            );

            gc.fillText(
                    "r = "
                    + String.format(
                            "%.2f",
                            fuerza.getDistancia()
                    ),
                    mx,
                    my + 10
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

        double dx = x2 - x1;
        double dy = y2 - y1;

        double angle = Math.atan2(dy, dx);

        double mx = (x1 + x2) / 2;
        double my = (y1 + y2) / 2;

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

            drawArrow(gc, mx, my, ax1, ay1);

            drawArrow(gc, mx, my, ax2, ay2);

        } else {

            drawArrow(gc, ax1, ay1, mx, my);

            drawArrow(gc, ax2, ay2, mx, my);
        }
    }

    private void drawArrow(
            GraphicsContext gc,
            double x1,
            double y1,
            double x2,
            double y2
    ) {

        gc.strokeLine(x1, y1, x2, y2);

        double angle
                = Math.atan2(y2 - y1, x2 - x1);

        double size = 8;

        double xA
                = x2 - size
                * Math.cos(angle - Math.PI / 6);

        double yA
                = y2 - size
                * Math.sin(angle - Math.PI / 6);

        double xB
                = x2 - size
                * Math.cos(angle + Math.PI / 6);

        double yB
                = y2 - size
                * Math.sin(angle + Math.PI / 6);

        gc.strokeLine(x2, y2, xA, yA);

        gc.strokeLine(x2, y2, xB, yB);
    }

    public void renderPanel(List<Carga> cargas) {

        listaCargas.getChildren().clear();

        for (int i = 0; i < cargas.size(); i++) {

            Carga c = cargas.get(i);

            int col = i % 2;
            int row = i / 2;

            CargaCard cardComponent
                    = new CargaCard(
                            c,
                            () -> {
                                cargas.remove(c);

                                fuerzaController.calcularFuerzas(cargas);

                                renderAll();
                            },
                            () -> {

                                fuerzaController.calcularFuerzas(cargas);

                                renderAll();
                            }
                    );

            VBox cardView
                    = cardComponent.getView();

            GridPane.setHgrow(
                    cardView,
                    Priority.ALWAYS
            );

            listaCargas.add(
                    cardView,
                    col,
                    row
            );
        }
    }

    public BorderPane getView() {
        return root;
    }
}
