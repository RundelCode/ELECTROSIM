package electrosim_c.Vistas;

import controlador.RegistroController;
import electrosim_c.Componentes.Header;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import modelo.Registro;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Buscador {

    private VBox root;

    public Buscador(
            Runnable goBack,
            Consumer<Registro> onOpen
    ) {

        // Contenedor principal donde se organiza toda la vista.
        root = new VBox(20);

        root.getStyleClass().add("contenedor");

        root.setPadding(
                new Insets(20)
        );

        // Agregamos el encabezado con el título y navegación de regreso.
        Header header = new Header(
                "Registros",
                goBack
        );

        root.getChildren().add(
                header.getView()
        );

        // Recuperamos todos los registros guardados en bitácora.
        RegistroController controller
                = new RegistroController();

        List<Registro> registros
                = controller.cargarRegistros();

        // Agrupamos visualmente los registros por fecha de creación.
        Map<LocalDate, VBox> secciones
                = new LinkedHashMap<>();

        for (Registro registro : registros) {

            LocalDate fecha
                    = registro.getFecha()
                            .toLocalDate();

            // Si la fecha aún no existe, creamos su sección correspondiente.
            if (!secciones.containsKey(fecha)) {

                VBox bloque
                        = new VBox(10);

                Label fechaLabel
                        = new Label(
                                registro.getFechaTexto()
                                        .substring(0, 10)
                        );

                fechaLabel.getStyleClass().add(
                        "theory-subtitle"
                );

                bloque.getChildren().add(
                        fechaLabel
                );

                secciones.put(
                        fecha,
                        bloque
                );

                root.getChildren().add(
                        bloque
                );
            }

            // Cada registro se representa como un acceso rápido al detalle.
            Button item = new Button(
                    registro.getUsuario()
                    + " - "
                    + registro.getNombreArchivo()
            );

            item.getStyleClass().add(
                    "registro-item"
            );

            // Permitimos que el botón ocupe todo el ancho disponible.
            item.setMaxWidth(
                    Double.MAX_VALUE
            );

            // Al seleccionar un registro delegamos su apertura a la vista padre.
            item.setOnAction(
                    e -> onOpen.accept(registro)
            );

            // Insertamos el registro dentro de su grupo de fecha.
            secciones.get(fecha)
                    .getChildren()
                    .add(item);
        }
    }

    // Expone la raíz visual para integrarla dentro de otras escenas.
    public VBox getView() {

        return root;
    }
}
