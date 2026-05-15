package controlador;

import modelo.Carga;
import modelo.Fuerza;
import modelo.Grafica;
import modelo.Sistema;

import java.util.ArrayList;
import java.util.List;

public class SistemaController {

    // Conservamos la referencia del sistema físico principal de ELECTROSIM.
    private Sistema sistema;

    // Delegamos el cálculo de interacciones a un controlador especializado.
    private FuerzaController fuerzaController;

    // Centralizamos todas las gráficas experimentales generadas durante la simulación.
    private List<Grafica> graficas;

    // Conservamos qué interacción está siendo analizada actualmente.
    private int indiceInteraccionSeleccionada = 0;

    // Construimos el controlador principal que conecta simulación, física y análisis.
    public SistemaController(Sistema sistema) {

        // Conservamos el sistema recibido como núcleo del experimento.
        this.sistema = sistema;

        // Inicializamos el controlador encargado de calcular fuerzas.
        this.fuerzaController
                = new FuerzaController();

        // Preparamos todas las gráficas que acompañarán el análisis experimental.
        inicializarGraficas();
    }

    // Construimos las gráficas base que alimentan el módulo de resultados.
    private void inicializarGraficas() {

        // Inicializamos la colección que agrupará todas las visualizaciones.
        graficas = new ArrayList<>();

        // Registramos la gráfica que analiza fuerza en función de distancia.
        graficas.add(
                new Grafica(
                        "Fuerza vs Distancia",
                        "Relación entre fuerza eléctrica y distancia",
                        "Distancia (m)",
                        "Fuerza (N)"
                )
        );

        // Registramos la gráfica que analiza el campo eléctrico.
        graficas.add(
                new Grafica(
                        "Campo Eléctrico",
                        "Variación del campo eléctrico",
                        "Distancia (m)",
                        "Campo (N/C)"
                )
        );

        // Registramos la gráfica que analiza el potencial eléctrico.
        graficas.add(
                new Grafica(
                        "Potencial Eléctrico",
                        "Variación del potencial eléctrico",
                        "Distancia (m)",
                        "Voltaje (V)"
                )
        );

        // Registramos la gráfica que analiza el trabajo realizado.
        graficas.add(
                new Grafica(
                        "Trabajo Eléctrico",
                        "Trabajo realizado por el campo",
                        "Distancia (m)",
                        "Trabajo (J)"
                )
        );
    }

    // Incorporamos una nueva carga al experimento activo.
    public void agregarCarga(
            float x,
            float y,
            float valor,
            float masa
    ) {

        // Limitamos la cantidad de partículas para mantener claridad visual.
        if (sistema.getCargas().size() >= 4) {
            return;
        }

        // Registramos la nueva carga dentro del sistema físico.
        sistema.agregarCarga(
                new Carga(
                        x,
                        y,
                        valor,
                        masa
                )
        );

        // Recalculamos inmediatamente todo el estado experimental.
        actualizarDatos();
    }

    // Sincronizamos fuerzas, gráficas y resultados después de cualquier cambio.
    public void actualizarDatos() {

        // Recalculamos todas las interacciones entre cargas activas.
        fuerzaController.calcularFuerzas(
                sistema.getCargas()
        );

        // Registramos los nuevos datos experimentales obtenidos.
        registrarDatosExperimentales();
    }

    // Extraemos datos físicos para alimentar el módulo analítico.
    private void registrarDatosExperimentales() {

        // Recuperamos todas las interacciones calculadas.
        List<Fuerza> fuerzas
                = fuerzaController.getFuerzas();

        // Si no existen interacciones, no hay datos por registrar.
        if (fuerzas.isEmpty()) {
            return;
        }

        // Garantizamos que el índice seleccionado siga siendo válido.
        if (indiceInteraccionSeleccionada >= fuerzas.size()) {

            indiceInteraccionSeleccionada = 0;
        }

        // Recuperamos la interacción actualmente seleccionada.
        Fuerza fuerza
                = fuerzas.get(
                        indiceInteraccionSeleccionada
                );

        // Calculamos la distancia entre ambas cargas.
        double distancia
                = fuerza.getDistancia();

        // Evitamos registrar datos inválidos.
        if (distancia <= 0) {
            return;
        }

        // Obtenemos la magnitud absoluta de la interacción.
        double magnitud
                = Math.abs(
                        fuerza.getMagnitud()
                );

        // Recuperamos la carga destino para cálculos derivados.
        double cargaDestino
                = Math.abs(
                        fuerza
                                .getCargaDestino()
                                .getCarga()
                );

        // Evitamos divisiones inválidas en el cálculo del campo.
        if (cargaDestino <= 0) {
            return;
        }

        // Calculamos el campo eléctrico generado en la interacción.
        double campo
                = magnitud / cargaDestino;

        // Calculamos el potencial eléctrico asociado a la interacción.
        double potencial
                = Sistema.K
                * fuerza
                        .getCargaOrigen()
                        .getCarga()
                / distancia;

        // Registramos el nuevo punto experimental en la gráfica de fuerza.
        graficas.get(0)
                .getDataSet()
                .agregarPunto(
                        distancia,
                        magnitud
                );

        // Registramos el nuevo punto experimental en la gráfica de campo.
        graficas.get(1)
                .getDataSet()
                .agregarPunto(
                        distancia,
                        campo
                );

        // Registramos el nuevo punto experimental en la gráfica de potencial.
        graficas.get(2)
                .getDataSet()
                .agregarPunto(
                        distancia,
                        Math.abs(potencial)
                );

        // Calculamos el trabajo acumulado a partir de la curva de fuerza.
        double trabajo
                = graficas.get(0)
                        .getDataSet()
                        .calcularAreaBajoCurva();

        // Registramos el nuevo punto experimental en la gráfica de trabajo.
        graficas.get(3)
                .getDataSet()
                .agregarPunto(
                        distancia,
                        trabajo
                );

        // Mantenemos las gráficas dentro de un tamaño manejable.
        limitarPuntos();

        // Generamos conclusiones automáticas basadas en los nuevos datos.
        generarConclusiones();
    }

    // Limitamos la cantidad de puntos almacenados para mantener rendimiento.
    private void limitarPuntos() {

        // Recorremos cada gráfica experimental del sistema.
        for (Grafica grafica : graficas) {

            // Eliminamos los puntos más antiguos si se supera el límite.
            while (grafica
                    .getDataSet()
                    .getPuntos()
                    .size() > 100) {

                grafica
                        .getDataSet()
                        .getPuntos()
                        .remove(0);
            }
        }
    }

    // Actualizamos las conclusiones automáticas de cada gráfica.
    private void generarConclusiones() {

        // Recorremos cada gráfica para reconstruir su interpretación.
        for (Grafica grafica : graficas) {

            // Delegamos el análisis al modelo de gráfica.
            grafica.generarConclusion();
        }
    }

    // Limpiamos completamente el historial experimental de todas las gráficas.
    public void limpiarGraficas() {

        // Recorremos todas las gráficas activas.
        for (Grafica grafica : graficas) {

            // Reiniciamos el contenido experimental de cada una.
            grafica.limpiar();
        }
    }

    // Reiniciamos el análisis conservando la configuración actual del sistema.
    public void reiniciarExperimento() {

        // Limpiamos todas las mediciones previas.
        limpiarGraficas();

        // Generamos nuevamente los datos con el estado actual.
        actualizarDatos();
    }

    // Cambiamos la interacción que será analizada en el módulo experimental.
    public void seleccionarInteraccion(
            int indice
    ) {

        // Guardamos la nueva interacción seleccionada.
        indiceInteraccionSeleccionada
                = indice;

        // Reiniciamos las gráficas para el nuevo análisis.
        limpiarGraficas();

        // Recalculamos los datos experimentales correspondientes.
        actualizarDatos();
    }

    // Retornamos la interacción actualmente activa.
    public Fuerza getInteraccionActual() {

        // Recuperamos todas las interacciones disponibles.
        List<Fuerza> fuerzas
                = fuerzaController.getFuerzas();

        // Si no existen fuerzas, retornamos un valor nulo.
        if (fuerzas.isEmpty()) {
            return null;
        }

        // Garantizamos que el índice seleccionado siga siendo válido.
        if (indiceInteraccionSeleccionada
                >= fuerzas.size()) {

            indiceInteraccionSeleccionada = 0;
        }

        // Retornamos la interacción actualmente seleccionada.
        return fuerzas.get(
                indiceInteraccionSeleccionada
        );
    }

    // Construimos el nombre visual de la interacción activa.
    public String getNombreInteraccionActual() {

        // Recuperamos la interacción actualmente seleccionada.
        Fuerza fuerza
                = getInteraccionActual();

        // Si no existe interacción, devolvemos un mensaje neutro.
        if (fuerza == null) {
            return "Sin interacción";
        }

        // Identificamos el índice visual de la carga origen.
        int q1
                = sistema.getCargas()
                        .indexOf(
                                fuerza.getCargaOrigen()
                        ) + 1;

        // Identificamos el índice visual de la carga destino.
        int q2
                = sistema.getCargas()
                        .indexOf(
                                fuerza.getCargaDestino()
                        ) + 1;

        // Retornamos una etiqueta clara para la interfaz.
        return "Q" + q1 + " ↔ Q" + q2;
    }

    // Retornamos todas las interacciones disponibles para selección.
    public List<String> getInteraccionesDisponibles() {

        // Preparamos la colección de nombres visibles.
        List<String> nombres
                = new ArrayList<>();

        // Recuperamos todas las interacciones calculadas.
        List<Fuerza> fuerzas
                = fuerzaController.getFuerzas();

        // Recorremos cada interacción disponible.
        for (int i = 0;
                i < fuerzas.size();
                i++) {

            // Recuperamos la interacción actual del recorrido.
            Fuerza fuerza
                    = fuerzas.get(i);

            // Calculamos el índice visual de la carga origen.
            int q1
                    = sistema.getCargas()
                            .indexOf(
                                    fuerza.getCargaOrigen()
                            ) + 1;

            // Calculamos el índice visual de la carga destino.
            int q2
                    = sistema.getCargas()
                            .indexOf(
                                    fuerza.getCargaDestino()
                            ) + 1;

            // Construimos la etiqueta legible para el usuario.
            nombres.add(
                    "Q" + q1 + " ↔ Q" + q2
            );
        }

        // Retornamos todas las opciones disponibles.
        return nombres;
    }

    // Retornamos el índice de la interacción seleccionada.
    public int getIndiceInteraccionSeleccionada() {

        return indiceInteraccionSeleccionada;
    }

    // Retornamos la gráfica dedicada al análisis de fuerza.
    public Grafica getGraficaFuerza() {

        return graficas.get(0);
    }

    // Retornamos la gráfica dedicada al análisis de campo eléctrico.
    public Grafica getGraficaCampo() {

        return graficas.get(1);
    }

    // Retornamos la gráfica dedicada al análisis de potencial eléctrico.
    public Grafica getGraficaPotencial() {

        return graficas.get(2);
    }

    // Retornamos la gráfica dedicada al análisis de trabajo.
    public Grafica getGraficaTrabajo() {

        return graficas.get(3);
    }

    // Retornamos todas las gráficas experimentales del sistema.
    public List<Grafica> getGraficas() {

        return graficas;
    }

    // Retornamos el controlador encargado de calcular fuerzas.
    public FuerzaController getFuerzaController() {

        return fuerzaController;
    }

    // Retornamos el sistema físico principal del experimento.
    public Sistema getSistema() {

        return sistema;
    }
}
