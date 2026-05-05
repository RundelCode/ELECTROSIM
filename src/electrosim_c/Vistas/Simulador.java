package electrosim_c.Vistas;

import electrosim_c.Componentes.Header;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import modelo.Carga;
import electrosim_c.Componentes.CargaCard;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;

public class Simulador {

    private BorderPane root;
    private Canvas canvas;
    private GridPane listaCargas;

    private List<Carga> cargas = new ArrayList<>();

    public Simulador(Runnable goBack) {

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
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPrefHeight(440);
        scroll.setMaxHeight(440);
        scroll.setMinHeight(440);
        scroll.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button btnIniciar = new Button("INICIAR");
        btnIniciar.getStyleClass().add("btn-danger");

        panelDerecho.getChildren().addAll(
                acciones,
                scroll,
                spacer,
                btnIniciar
        );

        canvasContainer.prefWidthProperty().bind(mainContent.widthProperty().multiply(0.6));
        panelDerecho.prefWidthProperty().bind(mainContent.widthProperty().multiply(0.4));
        panelDerecho.setMinWidth(350);

        canvas.widthProperty().bind(canvasContainer.widthProperty());
        canvas.setHeight(530);

        canvas.widthProperty().addListener((obs, o, n) -> renderAll());
        canvas.heightProperty().addListener((obs, o, n) -> renderAll());

        canvasContainer.getChildren().add(canvas);

        mainContent.getChildren().addAll(canvasContainer, panelDerecho);
        root.setCenter(mainContent);

        btnAgregar.setOnAction(e -> agregarCarga());
        btnLimpiar.setOnAction(e -> limpiar());

        Platform.runLater(this::renderAll);
    }

    private void agregarCarga() {
        if (cargas.size() >= 4) {
            return;
        }

        Carga c = new Carga(0, 0, Math.random() > 0.5 ? 5 : -5, 1);
        cargas.add(c);
        renderAll();
    }

    private void limpiar() {
        cargas.clear();
        renderAll();
    }

    private void renderAll() {
        if (canvas.getWidth() <= 0 || canvas.getHeight() <= 0) {
            return;
        }
        render(cargas);
        renderPanel(cargas);
    }

    public void render(List<Carga> cargas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double w = canvas.getWidth();
        double h = canvas.getHeight();

        double centerX = w / 2;
        double centerY = h / 2;

        double spacingX = w / 20;
        double spacingY = h / 20;

        gc.setFill(Color.web("#0f0f0f"));
        gc.fillRect(0, 0, w, h);

        gc.setStroke(Color.web("#2a2a2a"));

        for (int i = -10; i <= 10; i++) {
            double x = centerX + i * spacingX;
            double y = centerY + i * spacingY;

            gc.strokeLine(x, 0, x, h);
            gc.strokeLine(0, y, w, y);
        }

        gc.setStroke(Color.web("#555555"));
        gc.setLineWidth(2);

        gc.strokeLine(centerX, 0, centerX, h);
        gc.strokeLine(0, centerY, w, centerY);

        for (Carga c : cargas) {

            double x = centerX + c.getPosicionX() * spacingX;
            double y = centerY - c.getPosicionY() * spacingY;
            double radius = 14;

            gc.setFill(c.getCarga() > 0 ? Color.RED : Color.BLUE);
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

            gc.setFill(Color.WHITE);
            gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
            gc.setFont(javafx.scene.text.Font.font("System", javafx.scene.text.FontWeight.BOLD, 18));

            String signo = c.getCarga() >= 0 ? "+" : "-";

            gc.fillText(signo, x, y + 5);
        }
    }

    public void renderPanel(List<Carga> cargas) {
        listaCargas.getChildren().clear();

        for (int i = 0; i < cargas.size(); i++) {
            Carga c = cargas.get(i);

            int col = i % 2;
            int row = i / 2;

            CargaCard cardComponent = new CargaCard(c, () -> {
                cargas.remove(c);
                renderAll();
            }, this::renderAll);

            VBox cardView = cardComponent.getView();
            GridPane.setHgrow(cardView, Priority.ALWAYS);

            listaCargas.add(cardView, col, row);
        }
    }

    public BorderPane getView() {
        return root;
    }
}
