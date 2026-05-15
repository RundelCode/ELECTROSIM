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

    public VBox parse() {

        VBox content
                = new VBox(
                        18
                );

        content.setAlignment(
                Pos.TOP_LEFT
        );

        try {

            InputStream stream
                    = getClass()
                            .getResourceAsStream(
                                    "/electrosim_c/Recursos/Teoria.md"
                            );

            if (stream == null) {
                return content;
            }

            BufferedReader reader
                    = new BufferedReader(
                            new InputStreamReader(
                                    stream
                            )
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.trim()
                        .isEmpty()) {
                    continue;
                }

                if (line.startsWith(
                        "[IMG:"
                )) {

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

                    ImageView image
                            = crearImagen(
                                    nombre
                            );

                    content.getChildren().add(
                            image
                    );

                    continue;
                }

                Label label
                        = new Label();

                label.setWrapText(
                        true
                );

                label.setPrefWidth(
                        900
                );

                if (line.startsWith(
                        "# "
                )) {

                    label.setText(
                            line.substring(
                                    2
                            )
                    );

                    label.getStyleClass().add(
                            "theory-title"
                    );

                } else if (line.startsWith(
                        "## "
                )) {

                    label.setText(
                            line.substring(
                                    3
                            )
                    );

                    label.getStyleClass().add(
                            "theory-subtitle"
                    );

                } else if (line.startsWith(
                        "### "
                )) {

                    label.setText(
                            line.substring(
                                    4
                            )
                    );

                    label.getStyleClass().add(
                            "theory-section"
                    );

                } else {

                    label.setText(
                            line
                    );

                    label.getStyleClass().add(
                            "theory-text"
                    );
                }

                content.getChildren().add(
                        label
                );
            }

            reader.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return content;
    }

    private ImageView crearImagen(
            String nombre
    ) {

        InputStream stream
                = getClass()
                        .getResourceAsStream(
                                "/electrosim_c/Recursos/Teoria/"
                                + nombre
                                + ".png"
                        );

        if (stream == null) {

            Label error
                    = new Label(
                            "Imagen no encontrada: "
                            + nombre
                    );

            error.getStyleClass().add(
                    "theory-text"
            );

            return new ImageView();
        }

        Image image
                = new Image(
                        stream
                );

        ImageView view
                = new ImageView(
                        image
                );

        view.setPreserveRatio(
                true
        );

        view.setFitWidth(
                520
        );

        return view;
    }
}
