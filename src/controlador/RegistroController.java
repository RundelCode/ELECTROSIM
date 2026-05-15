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

            File carpetaSimulacion
                    = crearCarpetaSimulacion(
                            registro
                    );

            guardarMetadata(
                    carpetaSimulacion,
                    registro,
                    fuerzaNetaSistema
            );

            guardarCargas(
                    carpetaSimulacion,
                    cargas
            );

            guardarNotas(
                    carpetaSimulacion,
                    registro
            );

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

            e.printStackTrace();
        }
    }

    public List<Registro> cargarRegistros() {

        List<Registro> registros
                = new ArrayList<>();

        File root
                = new File(
                        "bitacoras"
                );

        if (!root.exists()) {
            return registros;
        }

        recorrer(
                root,
                registros
        );

        registros.sort(
                Comparator.comparing(
                        Registro::getFecha
                ).reversed()
        );

        return registros;
    }

    private void recorrer(
            File carpeta,
            List<Registro> registros
    ) {

        File[] archivos
                = carpeta.listFiles();

        if (archivos == null) {
            return;
        }

        for (File archivo : archivos) {

            if (!archivo.isDirectory()) {
                continue;
            }

            File metadata
                    = new File(
                            archivo,
                            "metadata.txt"
                    );

            if (metadata.exists()) {

                Registro registro
                        = leerRegistro(
                                metadata
                        );

                if (registro != null) {

                    registros.add(
                            registro
                    );
                }
            }

            recorrer(
                    archivo,
                    registros
            );
        }
    }

    private Registro leerRegistro(
            File metadata
    ) {

        try {

            BufferedReader reader
                    = new BufferedReader(
                            new FileReader(
                                    metadata
                            )
                    );

            String usuario = "";
            String titulo = "";
            String fechaTexto = "";

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith(
                        "Usuario:"
                )) {

                    usuario
                            = line.replace(
                                    "Usuario:",
                                    ""
                            ).trim();
                } else if (line.startsWith(
                        "Titulo:"
                )) {

                    titulo
                            = line.replace(
                                    "Titulo:",
                                    ""
                            ).trim();
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

            reader.close();

            Registro registro
                    = new Registro(
                            usuario,
                            titulo,
                            "",
                            new ArrayList<>()
                    );

            registro.setFecha(
                    LocalDateTime.parse(
                            fechaTexto
                    )
            );

            registro.setNombreArchivo(
                    metadata
                            .getParentFile()
                            .getName()
            );

            return registro;

        } catch (Exception e) {

            System.out.println(
                    "ERROR leyendo: "
                    + metadata.getAbsolutePath()
            );

            e.printStackTrace();

            return null;
        }
    }

    private File crearCarpetaSimulacion(
            Registro registro
    ) {

        String fecha
                = registro.getFecha()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd"
                                )
                        );

        File carpetaFecha
                = new File(
                        "bitacoras/"
                        + fecha
                );

        carpetaFecha.mkdirs();

        String usuario
                = limpiar(
                        registro.getUsuario()
                );

        File carpetaUsuario
                = new File(
                        carpetaFecha,
                        usuario
                );

        carpetaUsuario.mkdirs();

        int numero
                = carpetaUsuario.listFiles().length
                + 1;

        File carpetaSimulacion
                = new File(
                        carpetaUsuario,
                        String.format(
                                "simulacion_%03d",
                                numero
                        )
                );

        carpetaSimulacion.mkdirs();

        return carpetaSimulacion;
    }

    private void guardarMetadata(
            File carpeta,
            Registro registro,
            double fuerzaNetaSistema
    ) throws Exception {

        File archivo
                = new File(
                        carpeta,
                        "metadata.txt"
                );

        BufferedWriter writer
                = new BufferedWriter(
                        new FileWriter(
                                archivo
                        )
                );

        writer.write(
                "Usuario: "
                + registro.getUsuario()
        );

        writer.newLine();

        writer.write(
                "FuerzaNeta: "
                + fuerzaNetaSistema
        );

        writer.newLine();

        writer.write(
                "Titulo: "
                + registro.getTitulo()
        );

        writer.newLine();

        writer.write(
                "Fecha: "
                + registro.getFecha()
        );

        writer.close();
    }

    private void guardarNotas(
            File carpeta,
            Registro registro
    ) throws Exception {

        File archivo
                = new File(
                        carpeta,
                        "notas.txt"
                );

        BufferedWriter writer
                = new BufferedWriter(
                        new FileWriter(
                                archivo
                        )
                );

        writer.write(
                registro.getDescripcion()
        );

        writer.close();
    }

    private void guardarCargas(
            File carpeta,
            List<Carga> cargas
    ) throws Exception {

        File archivo
                = new File(
                        carpeta,
                        "cargas.txt"
                );

        BufferedWriter writer
                = new BufferedWriter(
                        new FileWriter(
                                archivo
                        )
                );

        for (int i = 0;
                i < cargas.size();
                i++) {

            Carga c
                    = cargas.get(
                            i
                    );

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

            writer.newLine();
        }

        writer.close();
    }

    private void exportarImagen(
            WritableImage image,
            File destino
    ) throws Exception {

        ImageIO.write(
                SwingFXUtils.fromFXImage(
                        image,
                        null
                ),
                "png",
                destino
        );
    }

    private String limpiar(
            String value
    ) {

        return value.replaceAll(
                "[^a-zA-Z0-9 ]",
                "_"
        );
    }
}
