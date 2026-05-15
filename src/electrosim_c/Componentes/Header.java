package electrosim_c.Componentes;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.geometry.Pos;

public class Header {

    // Conservamos el contenedor principal que representa el encabezado completo.
    private VBox root;

    // Construimos el encabezado reutilizable para las distintas vistas de ELECTROSIM.
    public Header(String texto, Runnable goBack) {

        // Creamos el título principal para reforzar la identidad visual del proyecto.
        Label titulo = new Label("ELECTROSIM");

        // Aplicamos el estilo principal usado en los encabezados de la aplicación.
        titulo.getStyleClass().add("titulo");

        // Creamos el botón que permite regresar a la vista anterior.
        Button back = new Button("Volver");

        // Delegamos la navegación para mantener desacoplada la lógica visual.
        back.setOnAction(e -> goBack.run());

        // Usamos un espacio flexible para separar visualmente el título del botón.
        Region spacer = new Region();

        // Permitimos que este espacio ocupe todo el ancho disponible.
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Construimos la barra superior con los controles principales.
        HBox topBar = new HBox(20, titulo, spacer, back);

        // Permitimos que la barra aproveche completamente el ancho del contenedor.
        topBar.setMaxWidth(Double.MAX_VALUE);

        // Alineamos los elementos hacia la izquierda para mantener consistencia visual.
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Creamos el subtítulo que contextualiza la sección actual.
        Label subtitulo = new Label(texto);

        // Aplicamos el estilo visual usado para textos secundarios.
        subtitulo.getStyleClass().add("subtitulo");

        // Permitimos que el texto haga salto de línea si el espacio se reduce.
        subtitulo.setWrapText(true);

        // Dejamos que el subtítulo pueda expandirse dentro del layout.
        subtitulo.setMaxWidth(Double.MAX_VALUE);

        // Construimos el contenedor principal del encabezado.
        root = new VBox(10, topBar, subtitulo);

        // Ajustamos dinámicamente el ancho del subtítulo para mantener una lectura cómoda.
        subtitulo.prefWidthProperty().bind(root.widthProperty().multiply(0.3));
    }

    // Retornamos la vista completa para integrarla en cualquier módulo de ELECTROSIM.
    public VBox getView() {
        return root;
    }
}
