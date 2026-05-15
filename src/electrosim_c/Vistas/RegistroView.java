package electrosim_c.Vistas;

import electrosim_c.Componentes.Header;

import javafx.geometry.Insets;

import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import modelo.Registro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.time.format.DateTimeFormatter;

public class RegistroView {

    private VBox root;

    private File carpetaRegistro;

    public RegistroView(
            Registro registro,
            Runnable goBack
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
                        30
                )
        );

        carpetaRegistro
                = construirRuta(
                        registro
                );

        Header header
                = new Header(
                        "Registro",
                        goBack
                );

        Label usuario
                = crearTitulo(
                        registro.getUsuario()
                );

        Label fecha
                = crearSubtitulo(
                        registro.getFechaTexto()
                );
        Label fuerzaTitulo
                = crearSeccion(
                        "Fuerza neta del sistema"
                );

        Label fuerza
                = crearTexto(
                        leerArchivo(
                                "metadata.txt",
                                3
                        )
                );

        Label titulo
                = crearSeccion(
                        "Experimento"
                );

        Label tituloValor
                = crearTexto(
                        leerArchivo(
                                "metadata.txt",
                                1
                        )
                );

        Label notasTitulo
                = crearSeccion(
                        "Observaciones"
                );

        Label notas
                = crearTexto(
                        leerCompleto(
                                "notas.txt"
                        )
                );

        Label cargasTitulo
                = crearSeccion(
                        "Configuración de cargas"
                );

        Label cargas
                = crearTexto(
                        leerCompleto(
                                "cargas.txt"
                        )
                );

        Label simulacionTitulo
                = crearSeccion(
                        "Simulación"
                );

        ImageView canvas
                = cargarImagen(
                        "canvas.png",
                        500
                );

        Label resultadosTitulo
                = crearSeccion(
                        "Resultados"
                );

        FlowPane resultados
                = new FlowPane();

        resultados.setHgap(
                20
        );

        resultados.setVgap(
                20
        );

        agregarGrafica(
                resultados,
                "campo.png"
        );

        agregarGrafica(
                resultados,
                "potencial.png"
        );

        agregarGrafica(
                resultados,
                "fuerzas.png"
        );

        agregarGrafica(
                resultados,
                "trabajo.png"
        );

        root.getChildren().addAll(
                header.getView(),
                usuario,
                fecha,
                fuerzaTitulo,
                fuerza,
                titulo,
                tituloValor,
                notasTitulo,
                notas,
                cargasTitulo,
                cargas,
                simulacionTitulo,
                canvas,
                resultadosTitulo,
                resultados
        );
    }

    private File construirRuta(
            Registro registro
    ) {

        String fecha
                = registro.getFecha()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd"
                                )
                        );

        return new File(
                "bitacoras/"
                + fecha
                + "/"
                + registro.getUsuario()
                + "/"
                + registro.getNombreArchivo()
        );
    }

    private void agregarGrafica(
            FlowPane parent,
            String nombre
    ) {

        ImageView img
                = cargarImagen(
                        nombre,
                        280
                );

        if (img != null) {

            parent.getChildren().add(
                    img
            );
        }
    }

    private ImageView cargarImagen(
            String nombre,
            double width
    ) {

        File archivo
                = new File(
                        carpetaRegistro,
                        nombre
                );

        if (!archivo.exists()) {
            return null;
        }

        ImageView view
                = new ImageView(
                        new Image(
                                archivo
                                        .toURI()
                                        .toString()
                        )
                );

        view.setPreserveRatio(
                true
        );

        view.setFitWidth(
                width
        );

        return view;
    }

    private String leerArchivo(
            String archivo,
            int lineaObjetivo
    ) {

        try {

            BufferedReader reader
                    = new BufferedReader(
                            new FileReader(
                                    new File(
                                            carpetaRegistro,
                                            archivo
                                    )
                            )
                    );

            String line;

            int i = 0;

            while ((line = reader.readLine()) != null) {

                if (i == lineaObjetivo) {

                    reader.close();

                    return line;
                }

                i++;
            }

        } catch (Exception e) {

        }

        return "";
    }

    private String leerCompleto(
            String archivo
    ) {

        try {

            BufferedReader reader
                    = new BufferedReader(
                            new FileReader(
                                    new File(
                                            carpetaRegistro,
                                            archivo
                                    )
                            )
                    );

            StringBuilder sb
                    = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {

                sb.append(
                        line
                );

                sb.append(
                        "\n"
                );
            }

            reader.close();

            return sb.toString();

        } catch (Exception e) {

            return "";
        }
    }

    private Label crearTitulo(
            String text
    ) {

        Label l
                = new Label(
                        text
                );

        l.getStyleClass().add(
                "titulo"
        );

        return l;
    }

    private Label crearSubtitulo(
            String text
    ) {

        Label l
                = new Label(
                        text
                );

        l.getStyleClass().add(
                "subtitulo"
        );

        return l;
    }

    private Label crearSeccion(
            String text
    ) {

        Label l
                = new Label(
                        text
                );

        l.getStyleClass().add(
                "theory-section"
        );

        return l;
    }

    private Label crearTexto(
            String text
    ) {

        Label l
                = new Label(
                        text
                );

        l.setWrapText(
                true
        );

        l.getStyleClass().add(
                "theory-text"
        );

        return l;
    }

    public VBox getView() {

        return root;
    }
}
