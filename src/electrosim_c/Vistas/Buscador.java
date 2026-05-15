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

        root
                = new VBox(
                        20
                );

        root.getStyleClass().add(
                "contenedor"
        );

        root.setPadding(
                new Insets(
                        20
                )
        );

        Header header
                = new Header(
                        "Registros",
                        goBack
                );

        root.getChildren().add(
                header.getView()
        );

        RegistroController controller
                = new RegistroController();

        List<Registro> registros
                = controller.cargarRegistros();

        Map<LocalDate, VBox> secciones
                = new LinkedHashMap<>();

        for (Registro registro : registros) {

            LocalDate fecha
                    = registro.getFecha()
                            .toLocalDate();

            if (!secciones.containsKey(
                    fecha
            )) {

                VBox bloque
                        = new VBox(
                                10
                        );

                Label fechaLabel
                        = new Label(
                                registro.getFechaTexto()
                                        .substring(
                                                0,
                                                10
                                        )
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

            Button item
                    = new Button(
                            registro.getUsuario()
                            + " - "
                            + registro.getNombreArchivo()
                    );

            item.getStyleClass().add(
                    "registro-item"
            );

            item.setMaxWidth(
                    Double.MAX_VALUE
            );

            item.setOnAction(
                    e -> onOpen.accept(
                            registro
                    )
            );

            secciones.get(
                    fecha
            ).getChildren().add(
                    item
            );
        }
    }

    public VBox getView() {

        return root;
    }
}
