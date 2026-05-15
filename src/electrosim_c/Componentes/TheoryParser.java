package electrosim_c.Componentes;

import javafx.geometry.Pos;

import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TheoryParser {

    // Construimos dinámicamente todo el contenido teórico que se muestra en la sección educativa.
    public VBox parse() {

        // Creamos el contenedor principal donde se irán agregando textos e imágenes.
        VBox content
                = new VBox(
                        18
                );

        // Alineamos el contenido hacia la parte superior para facilitar la lectura.
        content.setAlignment(
                Pos.TOP_LEFT
        );

        try {

            // Cargamos el archivo base que contiene toda la teoría escrita en formato markdown.
            InputStream stream
                    = getClass()
                            .getResourceAsStream(
                                    "/electrosim_c/Recursos/Teoria.md"
                            );

            // Si el archivo no existe, retornamos el contenedor vacío para evitar errores visuales.
            if (stream == null) {
                return content;
            }

            // Preparamos un lector que nos permita recorrer el archivo línea por línea.
            BufferedReader reader
                    = new BufferedReader(
                            new InputStreamReader(
                                    stream
                            )
                    );

            // Usaremos esta variable para almacenar temporalmente cada línea leída.
            String line;

            // Recorremos todo el archivo hasta procesar completamente su contenido.
            while ((line = reader.readLine()) != null) {

                // Ignoramos líneas vacías para mantener el contenido limpio y ordenado.
                if (line.trim()
                        .isEmpty()) {
                    continue;
                }

                // Detectamos si la línea representa una instrucción para cargar una imagen.
                if (line.startsWith(
                        "[IMG:"
                )) {

                    // Extraemos el nombre real del recurso gráfico.
                    String nombre
                            = line
                                    .replace(
                                            "[IMG:",
                                            ""
                                    )
                                    .replace(
                                            "]",
                                            ""
                                    );

                    // Delegamos la construcción visual de la imagen a un método especializado.
                    ImageView image
                            = crearImagen(
                                    nombre
                            );

                    // Agregamos la imagen al flujo del contenido teórico.
                    content.getChildren().add(
                            image
                    );

                    // Saltamos al siguiente elemento del archivo.
                    continue;
                }

                // Creamos una etiqueta visual para representar contenido textual.
                Label label
                        = new Label();

                // Permitimos que el texto haga salto automático de línea.
                label.setWrapText(
                        true
                );

                // Definimos un ancho fijo para mantener una lectura consistente.
                label.setPrefWidth(
                        900
                );

                // Detectamos títulos principales dentro del markdown.
                if (line.startsWith(
                        "# "
                )) {

                    // Eliminamos la sintaxis markdown antes de mostrar el texto.
                    label.setText(
                            line.substring(
                                    2
                            )
                    );

                    // Aplicamos el estilo visual correspondiente a títulos principales.
                    label.getStyleClass().add(
                            "theory-title"
                    );

                    // Detectamos subtítulos dentro de la teoría.
                } else if (line.startsWith(
                        "## "
                )) {

                    // Limpiamos la sintaxis markdown antes de renderizar.
                    label.setText(
                            line.substring(
                                    3
                            )
                    );

                    // Aplicamos el estilo visual para subtítulos.
                    label.getStyleClass().add(
                            "theory-subtitle"
                    );

                    // Detectamos encabezados de secciones internas.
                } else if (line.startsWith(
                        "### "
                )) {

                    // Extraemos únicamente el contenido legible.
                    label.setText(
                            line.substring(
                                    4
                            )
                    );

                    // Aplicamos el estilo visual correspondiente a secciones.
                    label.getStyleClass().add(
                            "theory-section"
                    );

                } else {

                    // Si no es un encabezado, lo tratamos como texto normal.
                    label.setText(
                            line
                    );

                    // Aplicamos el estilo estándar del contenido educativo.
                    label.getStyleClass().add(
                            "theory-text"
                    );
                }

                // Incorporamos el bloque textual al contenido general de teoría.
                content.getChildren().add(
                        label
                );
            }

            // Cerramos el lector una vez termina el procesamiento completo.
            reader.close();

        } catch (Exception e) {

            // Mostramos cualquier error para facilitar el diagnóstico durante desarrollo.
            e.printStackTrace();
        }

        // Retornamos toda la teoría ya convertida a componentes visuales.
        return content;
    }

    // Construimos visualmente una imagen referenciada dentro del contenido teórico.
    private ImageView crearImagen(
            String nombre
    ) {

        // Buscamos la imagen solicitada dentro de los recursos del proyecto.
        InputStream stream
                = getClass()
                        .getResourceAsStream(
                                "/electrosim_c/Recursos/Teoria/"
                                + nombre
                                + ".png"
                        );

        // Validamos que el recurso exista antes de intentar cargarlo.
        if (stream == null) {

            // Creamos un mensaje interno para facilitar el diagnóstico de recursos faltantes.
            Label error
                    = new Label(
                            "Imagen no encontrada: "
                            + nombre
                    );

            // Aplicamos el estilo visual estándar de la teoría.
            error.getStyleClass().add(
                    "theory-text"
            );

            // Retornamos una vista vacía para no romper la interfaz.
            return new ImageView();
        }

        // Convertimos el recurso encontrado en una imagen de JavaFX.
        Image image
                = new Image(
                        stream
                );

        // Creamos el componente visual que mostrará la imagen.
        ImageView view
                = new ImageView(
                        image
                );

        // Conservamos las proporciones originales para evitar deformaciones.
        view.setPreserveRatio(
                true
        );

        // Limitamos el ancho para integrarlo correctamente con el contenido textual.
        view.setFitWidth(
                520
        );

        // Retornamos la imagen ya preparada para mostrarse en pantalla.
        return view;
    }
}
