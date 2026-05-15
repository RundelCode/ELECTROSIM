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

        // Construimos el contenedor principal de la vista de detalle.
        root = new VBox(20);

        root.getStyleClass().add(
                "contenedor"
        );

        root.setPadding(
                new Insets(30)
        );

        // Reconstruimos la ubicación física donde fue almacenada la simulación.
        carpetaRegistro = construirRuta(
                registro
        );

        // Agregamos navegación para volver al listado de registros.
        Header header = new Header(
                "Registro",
                goBack
        );

        // Mostramos la información principal del experimento.
        Label usuario = crearTitulo(
                registro.getUsuario()
        );

        Label fecha = crearSubtitulo(
                registro.getFechaTexto()
        );

        // Recuperamos el resultado global más importante del sistema.
        Label fuerzaTitulo = crearSeccion(
                "Fuerza neta del sistema"
        );

        Label fuerza = crearTexto(
                leerArchivo(
                        "metadata.txt",
                        3
                )
        );

        // Mostramos la información general del experimento.
        Label titulo = crearSeccion(
                "Experimento"
        );

        Label tituloValor = crearTexto(
                leerArchivo(
                        "metadata.txt",
                        1
                )
        );

        // Recuperamos las observaciones registradas por el usuario.
        Label notasTitulo = crearSeccion(
                "Observaciones"
        );

        Label notas = crearTexto(
                leerCompleto(
                        "notas.txt"
                )
        );

        // Cargamos la configuración exacta usada en la simulación.
        Label cargasTitulo = crearSeccion(
                "Configuración de cargas"
        );

        Label cargas = crearTexto(
                leerCompleto(
                        "cargas.txt"
                )
        );

        // Mostramos la captura principal del experimento.
        Label simulacionTitulo = crearSeccion(
                "Simulación"
        );

        ImageView canvas = cargarImagen(
                "canvas.png",
                500
        );

        // Agrupamos todas las gráficas generadas durante el análisis.
        Label resultadosTitulo = crearSeccion(
                "Resultados"
        );

        FlowPane resultados = new FlowPane();

        resultados.setHgap(20);

        resultados.setVgap(20);

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

        // Ensamblamos toda la información para reconstruir el experimento.
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

        // Convertimos la fecha al formato usado por la bitácora.
        String fecha = registro.getFecha()
                .format(
                        DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd"
                        )
                );

        // Reconstruimos la ruta exacta donde vive este registro.
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

        // Solo agregamos la gráfica si fue exportada correctamente.
        ImageView img = cargarImagen(
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

        // Buscamos la imagen dentro del directorio del experimento.
        File archivo = new File(
                carpetaRegistro,
                nombre
        );

        // Evitamos errores si el recurso no existe.
        if (!archivo.exists()) {
            return null;
        }

        // Convertimos la imagen almacenada en un componente visual.
        ImageView view = new ImageView(
                new Image(
                        archivo
                                .toURI()
                                .toString()
                )
        );

        // Ajustamos el tamaño sin deformar la imagen original.
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

        // Extraemos una línea puntual de archivos estructurados.
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

        // Recuperamos archivos completos cuando el contenido es descriptivo.
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

        // Unificamos la apariencia de los títulos principales.
        Label l = new Label(
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

        // Usamos subtítulos para información contextual secundaria.
        Label l = new Label(
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

        // Marcamos visualmente cada bloque informativo del registro.
        Label l = new Label(
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

        // Aplicamos un formato uniforme para textos largos.
        Label l = new Label(
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

    // Exponemos la raíz visual para integrarla al flujo principal.
    public VBox getView() {

        return root;
    }
}
