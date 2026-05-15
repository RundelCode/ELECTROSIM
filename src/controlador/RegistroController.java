package controlador;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;

import modelo.Carga;
import modelo.Registro;

import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RegistroController {

    // Persistimos una simulación completa junto con sus datos e imágenes asociadas.
    public void guardarSimulacion(
            Registro registro,
            List<Carga> cargas,
            Canvas canvas,
            Chart campo,
            Chart potencial,
            Chart fuerzas,
            Chart trabajo,
            double fuerzaNetaSistema
    ) {

        try {

            // Creamos la estructura de carpetas donde se almacenará el experimento.
            File carpetaSimulacion
                    = crearCarpetaSimulacion(
                            registro
                    );

            // Guardamos la información general que identifica esta simulación.
            guardarMetadata(
                    carpetaSimulacion,
                    registro,
                    fuerzaNetaSistema
            );

            // Persistimos el estado físico de todas las cargas utilizadas.
            guardarCargas(
                    carpetaSimulacion,
                    cargas
            );

            // Guardamos las observaciones escritas por el usuario.
            guardarNotas(
                    carpetaSimulacion,
                    registro
            );

            // Exportamos una captura del canvas principal del experimento.
            exportarImagen(
                    canvas.snapshot(
                            new SnapshotParameters(),
                            null
                    ),
                    new File(
                            carpetaSimulacion,
                            "canvas.png"
                    )
            );

            // Exportamos la gráfica del campo eléctrico para consulta futura.
            exportarImagen(
                    campo.snapshot(
                            new SnapshotParameters(),
                            null
                    ),
                    new File(
                            carpetaSimulacion,
                            "campo.png"
                    )
            );

            // Exportamos la gráfica del potencial eléctrico.
            exportarImagen(
                    potencial.snapshot(
                            new SnapshotParameters(),
                            null
                    ),
                    new File(
                            carpetaSimulacion,
                            "potencial.png"
                    )
            );

            // Exportamos la gráfica comparativa de fuerzas netas.
            exportarImagen(
                    fuerzas.snapshot(
                            new SnapshotParameters(),
                            null
                    ),
                    new File(
                            carpetaSimulacion,
                            "fuerzas.png"
                    )
            );

            // Exportamos la gráfica del trabajo realizado durante la simulación.
            exportarImagen(
                    trabajo.snapshot(
                            new SnapshotParameters(),
                            null
                    ),
                    new File(
                            carpetaSimulacion,
                            "trabajo.png"
                    )
            );

        } catch (Exception e) {

            // Mostramos cualquier error para facilitar el diagnóstico del guardado.
            e.printStackTrace();
        }
    }

    // Recuperamos todos los experimentos previamente almacenados en bitácora.
    public List<Registro> cargarRegistros() {

        // Preparamos la colección donde se irán reconstruyendo los registros.
        List<Registro> registros
                = new ArrayList<>();

        // Localizamos la carpeta raíz donde viven todas las simulaciones.
        File root
                = new File(
                        "bitacoras"
                );

        // Si aún no existen bitácoras, retornamos una lista vacía.
        if (!root.exists()) {
            return registros;
        }

        // Recorremos toda la estructura de carpetas buscando experimentos.
        recorrer(
                root,
                registros
        );

        // Ordenamos los resultados para mostrar primero los más recientes.
        registros.sort(
                Comparator.comparing(
                        Registro::getFecha
                ).reversed()
        );

        // Retornamos todos los registros encontrados.
        return registros;
    }

    // Recorremos recursivamente las carpetas buscando metadatos válidos.
    private void recorrer(
            File carpeta,
            List<Registro> registros
    ) {

        // Recuperamos todos los archivos y subcarpetas del nivel actual.
        File[] archivos
                = carpeta.listFiles();

        // Si no hay contenido disponible, detenemos la exploración.
        if (archivos == null) {
            return;
        }

        // Analizamos cada elemento encontrado dentro de la carpeta.
        for (File archivo : archivos) {

            // Ignoramos archivos sueltos y procesamos únicamente carpetas.
            if (!archivo.isDirectory()) {
                continue;
            }

            // Buscamos el archivo que identifica una simulación guardada.
            File metadata
                    = new File(
                            archivo,
                            "metadata.txt"
                    );

            // Si encontramos metadata válida, intentamos reconstruir el registro.
            if (metadata.exists()) {

                // Reconstruimos el experimento a partir del archivo persistido.
                Registro registro
                        = leerRegistro(
                                metadata
                        );

                // Si el registro es válido, lo incorporamos a la colección.
                if (registro != null) {

                    registros.add(
                            registro
                    );
                }
            }

            // Continuamos explorando niveles más profundos de la estructura.
            recorrer(
                    archivo,
                    registros
            );
        }
    }

    // Reconstruimos un registro a partir de su archivo de metadatos.
    private Registro leerRegistro(
            File metadata
    ) {

        try {

            // Abrimos el archivo de metadata para leerlo línea por línea.
            BufferedReader reader
                    = new BufferedReader(
                            new FileReader(
                                    metadata
                            )
                    );

            // Inicializamos el nombre del usuario del experimento.
            String usuario = "";

            // Inicializamos el título del experimento.
            String titulo = "";

            // Inicializamos la fecha almacenada del experimento.
            String fechaTexto = "";

            // Usaremos esta variable para recorrer cada línea del archivo.
            String line;

            // Procesamos completamente el archivo de metadata.
            while ((line = reader.readLine()) != null) {

                // Extraemos el nombre del usuario si existe.
                if (line.startsWith(
                        "Usuario:"
                )) {

                    usuario
                            = line.replace(
                                    "Usuario:",
                                    ""
                            ).trim();

                    // Extraemos el título del experimento si existe.
                } else if (line.startsWith(
                        "Titulo:"
                )) {

                    titulo
                            = line.replace(
                                    "Titulo:",
                                    ""
                            ).trim();

                    // Extraemos la fecha de creación del experimento.
                } else if (line.startsWith(
                        "Fecha:"
                )) {

                    fechaTexto
                            = line.replace(
                                    "Fecha:",
                                    ""
                            ).trim();
                }
            }

            // Cerramos el lector una vez finaliza la lectura.
            reader.close();

            // Reconstruimos el objeto base del registro.
            Registro registro
                    = new Registro(
                            usuario,
                            titulo,
                            "",
                            new ArrayList<>()
                    );

            // Restauramos la fecha original del experimento.
            registro.setFecha(
                    LocalDateTime.parse(
                            fechaTexto
                    )
            );

            // Asociamos el nombre físico de la carpeta para navegación futura.
            registro.setNombreArchivo(
                    metadata
                            .getParentFile()
                            .getName()
            );

            // Retornamos el registro completamente reconstruido.
            return registro;

        } catch (Exception e) {

            // Mostramos cualquier error durante la reconstrucción.
            e.printStackTrace();

            // Indicamos que el registro no pudo recuperarse correctamente.
            return null;
        }
    }

    // Construimos la ruta física donde se almacenará una nueva simulación.
    private File crearCarpetaSimulacion(
            Registro registro
    ) {

        // Generamos la fecha base que organizará los experimentos.
        String fecha
                = registro.getFecha()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd"
                                )
                        );

        // Creamos la carpeta correspondiente al día del experimento.
        File carpetaFecha
                = new File(
                        "bitacoras/"
                        + fecha
                );

        // Garantizamos que la carpeta exista antes de continuar.
        carpetaFecha.mkdirs();

        // Limpiamos el nombre del usuario para usarlo como ruta válida.
        String usuario
                = limpiar(
                        registro.getUsuario()
                );

        // Creamos la carpeta dedicada a ese usuario.
        File carpetaUsuario
                = new File(
                        carpetaFecha,
                        usuario
                );

        // Garantizamos la existencia de la carpeta del usuario.
        carpetaUsuario.mkdirs();

        // Calculamos el consecutivo de esta nueva simulación.
        int numero
                = carpetaUsuario.listFiles().length
                + 1;

        // Creamos la carpeta específica del experimento actual.
        File carpetaSimulacion
                = new File(
                        carpetaUsuario,
                        String.format(
                                "simulacion_%03d",
                                numero
                        )
                );

        // Garantizamos la existencia física de la carpeta final.
        carpetaSimulacion.mkdirs();

        // Retornamos la ruta lista para guardar contenido.
        return carpetaSimulacion;
    }

    // Persistimos la información general que identifica la simulación.
    private void guardarMetadata(
            File carpeta,
            Registro registro,
            double fuerzaNetaSistema
    ) throws Exception {

        // Creamos el archivo que almacenará la metadata principal.
        File archivo
                = new File(
                        carpeta,
                        "metadata.txt"
                );

        // Preparamos un escritor para registrar la información.
        BufferedWriter writer
                = new BufferedWriter(
                        new FileWriter(
                                archivo
                        )
                );

        // Guardamos el nombre del usuario responsable del experimento.
        writer.write(
                "Usuario: "
                + registro.getUsuario()
        );

        // Saltamos a la siguiente línea del archivo.
        writer.newLine();

        // Guardamos la fuerza neta global del sistema.
        writer.write(
                "FuerzaNeta: "
                + fuerzaNetaSistema
        );

        // Saltamos a la siguiente línea del archivo.
        writer.newLine();

        // Guardamos el título asignado al experimento.
        writer.write(
                "Titulo: "
                + registro.getTitulo()
        );

        // Saltamos a la siguiente línea del archivo.
        writer.newLine();

        // Guardamos la fecha exacta de creación del experimento.
        writer.write(
                "Fecha: "
                + registro.getFecha()
        );

        // Cerramos el archivo para persistir correctamente la información.
        writer.close();
    }

    // Persistimos las observaciones escritas durante el experimento.
    private void guardarNotas(
            File carpeta,
            Registro registro
    ) throws Exception {

        // Creamos el archivo que almacenará las notas del usuario.
        File archivo
                = new File(
                        carpeta,
                        "notas.txt"
                );

        // Preparamos el escritor de contenido textual.
        BufferedWriter writer
                = new BufferedWriter(
                        new FileWriter(
                                archivo
                        )
                );

        // Guardamos la descripción registrada en la bitácora.
        writer.write(
                registro.getDescripcion()
        );

        // Cerramos el archivo para asegurar la persistencia.
        writer.close();
    }

    // Persistimos el estado físico de todas las cargas de la simulación.
    private void guardarCargas(
            File carpeta,
            List<Carga> cargas
    ) throws Exception {

        // Creamos el archivo que almacenará la configuración de cargas.
        File archivo
                = new File(
                        carpeta,
                        "cargas.txt"
                );

        // Preparamos el escritor que documentará cada partícula.
        BufferedWriter writer
                = new BufferedWriter(
                        new FileWriter(
                                archivo
                        )
                );

        // Recorremos cada carga para documentar su estado físico.
        for (int i = 0;
                i < cargas.size();
                i++) {

            // Recuperamos la carga actual del recorrido.
            Carga c
                    = cargas.get(
                            i
                    );

            // Guardamos magnitud y posición de la carga actual.
            writer.write(
                    "Q"
                    + (i + 1)
                    + " -> "
                    + c.getCarga()
                    + "C "
                    + "("
                    + c.getPosicionX()
                    + ", "
                    + c.getPosicionY()
                    + ")"
            );

            // Saltamos a la siguiente línea para la próxima carga.
            writer.newLine();
        }

        // Cerramos el archivo después de registrar todas las cargas.
        writer.close();
    }

    // Exportamos cualquier componente visual como imagen PNG.
    private void exportarImagen(
            WritableImage image,
            File destino
    ) throws Exception {

        // Convertimos la imagen de JavaFX y la persistimos en disco.
        ImageIO.write(
                SwingFXUtils.fromFXImage(
                        image,
                        null
                ),
                "png",
                destino
        );
    }

    // Limpiamos textos para convertirlos en nombres válidos de carpetas.
    private String limpiar(
            String value
    ) {

        // Reemplazamos caracteres incompatibles por separadores seguros.
        return value.replaceAll(
                "[^a-zA-Z0-9 ]",
                "_"
        );
    }
}
