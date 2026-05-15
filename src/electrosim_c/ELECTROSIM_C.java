package electrosim_c;

import electrosim_c.Vistas.Buscador;
import electrosim_c.Vistas.Inicio;
import electrosim_c.Vistas.RegistroView;
import electrosim_c.Vistas.Simulador;
import electrosim_c.Vistas.Teoria;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

public class ELECTROSIM_C extends Application {

    // Conservamos la escena principal para poder intercambiar vistas dinámicamente.
    private Scene scene;

    // Conservamos la ventana principal para controlar tamaño y navegación.
    private Stage stage;

    @Override
    public void start(Stage stage) {

        // Guardamos la referencia de la ventana principal de la aplicación.
        this.stage = stage;

        // Cargamos la fuente principal usada en los títulos de ELECTROSIM.
        Font.loadFont(
                getClass().getResource("/electrosim_c/Fuentes/Poppins-ExtraBold.ttf").toExternalForm(),
                10
        );

        // Cargamos la fuente secundaria usada en textos e interfaz.
        Font.loadFont(
                getClass().getResource("/electrosim_c/Fuentes/Inter-Bold.ttf").toExternalForm(),
                10
        );

        // Inicializamos la escena base donde se irán montando todas las vistas.
        scene = new Scene(new StackPane(), 600, 400);

        // Aplicamos la hoja de estilos global para unificar toda la interfaz.
        scene.getStylesheets().add(
                getClass().getResource("styles.css").toExternalForm()
        );

        // Bloqueamos el redimensionamiento manual para preservar el diseño.
        stage.setResizable(false);

        // Aplicamos el logo oficial de ELECTROSIM a la ventana principal.
        stage.getIcons().add(
                new Image(
                        getClass()
                                .getResourceAsStream(
                                        "/electrosim_c/Recursos/Logo.png"
                                )
                )
        );

        // Mostramos la pantalla inicial como punto de entrada de la aplicación.
        showInicio();

        // Definimos el título visible de la ventana principal.
        stage.setTitle("ELECTROSIM");

        // Asociamos la escena preparada con la ventana.
        stage.setScene(scene);

        // Hacemos visible la aplicación para el usuario.
        stage.show();
    }

    // Construimos y mostramos la pantalla principal de navegación.
    private void showInicio() {

        // Ajustamos el tamaño ideal para la vista de inicio.
        setSize(455, 540);

        // Construimos la vista principal y conectamos sus rutas de navegación.
        Inicio inicio = new Inicio(
                () -> showSimulador(),
                () -> showBuscador(),
                () -> showTeoria()
        );

        // Montamos la vista dentro del contenedor principal con scroll.
        setRootScrollable(inicio.getView());
    }

    // Construimos y mostramos el módulo principal de simulación.
    private void showSimulador() {

        // Ajustamos el tamaño ideal para la interfaz de simulación.
        setSize(1200, 800);

        // Construimos el simulador y conectamos la navegación de regreso.
        Simulador simulador = new Simulador(() -> showInicio());

        // Montamos la vista dentro del contenedor principal con scroll.
        setRootScrollable(simulador.getView());
    }

    // Construimos y mostramos el módulo de búsqueda de experimentos.
    private void showBuscador() {

        // Ajustamos el tamaño ideal para explorar registros guardados.
        setSize(
                650,
                500
        );

        // Construimos la vista del buscador y conectamos sus eventos principales.
        Buscador buscador
                = new Buscador(
                        () -> showInicio(),
                        registro -> showRegistro(
                                registro
                        )
                );

        // Montamos la vista dentro del contenedor principal con scroll.
        setRootScrollable(
                buscador.getView()
        );
    }

    // Construimos la vista detallada de un registro previamente almacenado.
    private void showRegistro(
            modelo.Registro registro
    ) {

        // Ajustamos el tamaño ideal para revisar resultados completos.
        setSize(
                1200,
                800
        );

        // Construimos la vista de detalle usando el registro seleccionado.
        RegistroView vista
                = new RegistroView(
                        registro,
                        () -> showBuscador()
                );

        // Montamos la vista dentro del contenedor principal con scroll.
        setRootScrollable(
                vista.getView()
        );
    }

    // Construimos y mostramos el módulo educativo de teoría.
    private void showTeoria() {

        // Ajustamos el tamaño ideal para lectura de contenido teórico.
        setSize(900, 600);

        // Construimos la vista educativa y conectamos la navegación.
        Teoria teoria = new Teoria(() -> showInicio());

        // Montamos la vista dentro del contenedor principal con scroll.
        setRootScrollable(teoria.getView());
    }

    // Encapsulamos cualquier vista dentro de un contenedor desplazable.
    private void setRootScrollable(javafx.scene.Parent view) {

        // Creamos el scroll que permitirá manejar contenido de tamaño variable.
        ScrollPane scroll = new ScrollPane(view);

        // Ajustamos el contenido para que aproveche todo el ancho disponible.
        scroll.setFitToWidth(true);

        // Reemplazamos la vista actual por la nueva interfaz solicitada.
        scene.setRoot(scroll);
    }

    // Ajustamos el tamaño fijo de la ventana según el módulo activo.
    private void setSize(double width, double height) {

        // Definimos el ancho actual de la ventana.
        stage.setWidth(width);

        // Definimos la altura actual de la ventana.
        stage.setHeight(height);

        // Bloqueamos el ancho mínimo para mantener consistencia visual.
        stage.setMinWidth(width);

        // Bloqueamos el ancho máximo para evitar deformaciones.
        stage.setMaxWidth(width);

        // Bloqueamos la altura mínima para mantener la composición.
        stage.setMinHeight(height);

        // Bloqueamos la altura máxima para evitar cambios no previstos.
        stage.setMaxHeight(height);

        // Centramos la ventana para mejorar la experiencia del usuario.
        stage.centerOnScreen();
    }

    // Punto de entrada oficial de la aplicación de escritorio.
    public static void main(String[] args) {

        // Delegamos el ciclo de vida completo a JavaFX.
        launch();
    }
}
