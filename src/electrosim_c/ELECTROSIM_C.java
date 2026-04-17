package electrosim_c;

import electrosim_c.Vistas.Buscador;
import electrosim_c.Vistas.Inicio;
import electrosim_c.Vistas.Registro;
import electrosim_c.Vistas.Simulador;
import electrosim_c.Vistas.Teoria;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class ELECTROSIM_C extends Application {

    private Scene scene;
    private Stage stage;
    
    @Override
    public void start(Stage stage) {

        this.stage = stage;

        Font.loadFont(
                getClass().getResource("/electrosim_c/Fuentes/Poppins-ExtraBold.ttf").toExternalForm(),
                10
        );

        Font.loadFont(
                getClass().getResource("/electrosim_c/Fuentes/Inter-Bold.ttf").toExternalForm(),
                10
        );

        scene = new Scene(new StackPane(), 600, 400);

        scene.getStylesheets().add(
                getClass().getResource("styles.css").toExternalForm()
        );

        showInicio();

        stage.setTitle("ELECTROSIM");
        stage.setScene(scene);
        stage.show();
    }


    private void showInicio() {
        setSize(455, 484);

        Inicio inicio = new Inicio(
                () -> showSimulador(),
                () -> showBuscador(),
                () -> showTeoria()
        );

        scene.setRoot(inicio.getView());
    }

    private void showSimulador() {
        setSize(1000, 700);

        Simulador simulador = new Simulador(() -> showInicio());
        scene.setRoot(simulador.getView());
    }

    private void showBuscador() {
        setSize(800, 600);

        Buscador buscador = new Buscador(() -> showInicio());
        scene.setRoot(buscador.getView());
    }

    private void showRegistro() {
        setSize(700, 500);

        Registro registro = new Registro(() -> showInicio());
        scene.setRoot(registro.getView());
    }

    private void showTeoria() {
        setSize(900, 600);

        Teoria teoria = new Teoria(() -> showInicio());
        scene.setRoot(teoria.getView());
    }

    private void setSize(double width, double height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }

    public static void main(String[] args) {
        launch();
    }
}