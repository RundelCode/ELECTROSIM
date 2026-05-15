package controlador;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;

import modelo.Carga;
import modelo.Registro;

import javax.imageio.ImageIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.time.format.DateTimeFormatter;

import java.util.List;

public class RegistroController {

    public void guardarSimulacion(
            Registro registro,
            List<Carga> cargas,
            Canvas canvas,
            Chart campo,
            Chart potencial,
            Chart fuerzas,
            Chart trabajo
    ) {

        try {

            System.out.println(
                    "INICIANDO GUARDADO..."
            );

            File carpetaSimulacion
                    = crearCarpetaSimulacion(
                            registro
                    );

            guardarMetadata(
                    carpetaSimulacion,
                    registro
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

            System.out.println(
                    "GUARDADO EXITOSO"
            );

            System.out.println(
                    carpetaSimulacion.getAbsolutePath()
            );

        } catch (Exception e) {

            e.printStackTrace();
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
            Registro registro
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
