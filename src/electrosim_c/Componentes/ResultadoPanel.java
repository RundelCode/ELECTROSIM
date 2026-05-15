package electrosim_c.Componentes;

import controlador.GraficaController;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import modelo.Carga;

import java.util.List;

public class ResultadoPanel extends VBox {

    // Centralizamos la lógica que transforma datos físicos en información visual.
    private final GraficaController controller;

    // Organizamos los resultados en pestañas para facilitar la exploración del usuario.
    private final TabPane tabPane;

    // Construimos el panel encargado de mostrar los resultados de la simulación.
    public ResultadoPanel() {

        // Inicializamos el controlador que procesa los datos numéricos del sistema.
        controller
                = new GraficaController();

        // Aplicamos el estilo principal del panel dentro del dashboard.
        getStyleClass().add(
                "result-panel"
        );

        // Creamos el contenedor de pestañas que organizará toda la información.
        tabPane
                = new TabPane();

        // Bloqueamos el cierre manual de pestañas para mantener la estructura fija.
        tabPane.setTabClosingPolicy(
                TabPane.TabClosingPolicy.UNAVAILABLE
        );

        // Permitimos que el panel pueda adaptarse cuando el layout se comprima.
        setMinHeight(
                0
        );

        // Definimos una altura consistente para mantener la interfaz estable.
        setPrefHeight(
                260
        );

        // Limitamos el crecimiento vertical para conservar el diseño del simulador.
        setMaxHeight(
                260
        );

        // Permitimos que el contenedor interno también pueda reducirse si es necesario.
        tabPane.setMinHeight(
                0
        );

        // Sincronizamos la altura visual de las pestañas con el panel.
        tabPane.setPrefHeight(
                260
        );

        // Evitamos que el contenedor rompa la composición general del dashboard.
        tabPane.setMaxHeight(
                260
        );

        // Incorporamos el sistema de pestañas al panel principal.
        getChildren().add(
                tabPane
        );

        // Permitimos que el panel aproveche todo el espacio vertical disponible.
        VBox.setVgrow(
                tabPane,
                Priority.ALWAYS
        );
    }

    // Reconstruimos el panel cada vez que el estado físico de la simulación cambia.
    public void actualizar(
            List<Carga> cargas
    ) {

        // Limpiamos cualquier resultado anterior antes de recalcular la interfaz.
        tabPane.getTabs().clear();

        // Generamos primero el resumen general del sistema completo.
        crearTabSistema(
                cargas
        );

        // Creamos una pestaña individual para cada carga activa.
        for (int i = 0;
                i < cargas.size();
                i++) {

            // Delegamos la construcción de cada pestaña específica.
            crearTabCarga(
                    i,
                    cargas
            );
        }
    }

    // Construimos la pestaña que resume el comportamiento global del sistema.
    private void crearTabSistema(
            List<Carga> cargas
    ) {

        // Calculamos la fuerza neta individual de cada carga.
        List<Float> fuerzas
                = controller.generarFuerzaNeta(
                        cargas
                );

        // Inicializamos el acumulador de fuerza total del sistema.
        float total = 0;

        // Sumamos todas las fuerzas para obtener el comportamiento global.
        for (Float f : fuerzas) {
            total += f;
        }

        // Creamos el contenedor visual que agrupará toda la información.
        VBox contenido
                = new VBox(
                        12
                );

        // Agregamos las métricas principales del sistema actual.
        contenido.getChildren().addAll(
                texto(
                        "Cargas:"
                ),
                texto(
                        String.valueOf(
                                cargas.size()
                        )
                ),
                texto(
                        "Interacciones:"
                ),
                texto(
                        String.valueOf(
                                cargas.size()
                                * (cargas.size()
                                - 1)
                                / 2
                        )
                ),
                texto(
                        "Fuerza neta total:"
                ),
                texto(
                        String.format(
                                "%.2f N",
                                total
                        )
                )
        );

        // Encapsulamos el contenido para permitir navegación vertical si es necesario.
        ScrollPane scroll
                = crearScroll(
                        contenido
                );

        // Creamos la pestaña principal con el resumen global.
        Tab tab
                = new Tab(
                        "Sistema",
                        scroll
                );

        // Incorporamos la pestaña al panel de resultados.
        tabPane.getTabs().add(
                tab
        );
    }

    // Construimos una pestaña dedicada a una carga específica del sistema.
    private void crearTabCarga(
            int index,
            List<Carga> cargas
    ) {

        // Recuperamos la carga que corresponde a esta pestaña.
        Carga c
                = cargas.get(
                        index
                );

        // Calculamos nuevamente las fuerzas netas para acceder al valor individual.
        List<Float> fuerzas
                = controller.generarFuerzaNeta(
                        cargas
                );

        // Creamos el contenedor que mostrará los datos físicos de esta carga.
        VBox contenido
                = new VBox(
                        12
                );

        // Organizamos toda la información relevante de la carga seleccionada.
        contenido.getChildren().addAll(
                texto(
                        "Carga:"
                ),
                texto(
                        c.getCarga()
                        + " C"
                ),
                texto(
                        "Posición:"
                ),
                texto(
                        "("
                        + String.format(
                                "%.2f",
                                c.getPosicionX()
                        )
                        + ", "
                        + String.format(
                                "%.2f",
                                c.getPosicionY()
                        )
                        + ")"
                ),
                texto(
                        "Fuerza neta:"
                ),
                texto(
                        String.format(
                                "%.2f N",
                                fuerzas.get(
                                        index
                                )
                        )
                )
        );

        // Encapsulamos el contenido para mantener consistencia visual con otras pestañas.
        ScrollPane scroll
                = crearScroll(
                        contenido
                );

        // Nombramos la pestaña siguiendo la nomenclatura visual de ELECTROSIM.
        Tab tab
                = new Tab(
                        "Q" + (index + 1),
                        scroll
                );

        // Agregamos la pestaña individual al panel general.
        tabPane.getTabs().add(
                tab
        );
    }

    // Creamos un contenedor con scroll para manejar contenido dinámico sin romper el layout.
    private ScrollPane crearScroll(
            VBox contenido
    ) {

        // Construimos el scroll con el contenido ya preparado.
        ScrollPane scroll
                = new ScrollPane(
                        contenido
                );

        // Ajustamos automáticamente el ancho para mejorar la lectura.
        scroll.setFitToWidth(
                true
        );

        // Desactivamos el scroll horizontal porque el diseño ya está controlado.
        scroll.setHbarPolicy(
                ScrollPane.ScrollBarPolicy.NEVER
        );

        // Eliminamos fondos y bordes para integrarlo visualmente con el dashboard.
        scroll.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: transparent;"
        );

        // Retornamos el contenedor listo para usarse.
        return scroll;
    }

    // Construimos un bloque de texto con el estilo visual estándar de resultados.
    private Text texto(
            String value
    ) {

        // Creamos el nodo textual con el valor recibido.
        Text text
                = new Text(
                        value
                );

        // Aplicamos el estilo visual usado en todo el módulo de resultados.
        text.getStyleClass().add(
                "result-value"
        );

        // Retornamos el texto listo para integrarse al panel.
        return text;
    }
}
