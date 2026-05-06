package controlador;

import modelo.Carga;
import modelo.Fuerza;
import modelo.Grafica;
import modelo.Sistema;

import java.util.ArrayList;
import java.util.List;

public class SistemaController {

    private Sistema sistema;

    private FuerzaController fuerzaController;

    private List<Grafica> graficas;

    private int indiceInteraccionSeleccionada = 0;

    public SistemaController(Sistema sistema) {

        this.sistema = sistema;

        this.fuerzaController
                = new FuerzaController();

        inicializarGraficas();
    }

    private void inicializarGraficas() {

        graficas = new ArrayList<>();

        graficas.add(
                new Grafica(
                        "Fuerza vs Distancia",
                        "Relación entre fuerza eléctrica y distancia",
                        "Distancia (m)",
                        "Fuerza (N)"
                )
        );

        graficas.add(
                new Grafica(
                        "Campo Eléctrico",
                        "Variación del campo eléctrico",
                        "Distancia (m)",
                        "Campo (N/C)"
                )
        );

        graficas.add(
                new Grafica(
                        "Potencial Eléctrico",
                        "Variación del potencial eléctrico",
                        "Distancia (m)",
                        "Voltaje (V)"
                )
        );

        graficas.add(
                new Grafica(
                        "Trabajo Eléctrico",
                        "Trabajo realizado por el campo",
                        "Distancia (m)",
                        "Trabajo (J)"
                )
        );
    }

    public void agregarCarga(
            float x,
            float y,
            float valor,
            float masa
    ) {

        if (sistema.getCargas().size() >= 4) {
            return;
        }

        sistema.agregarCarga(
                new Carga(
                        x,
                        y,
                        valor,
                        masa
                )
        );

        actualizarDatos();
    }

    public void actualizarDatos() {

        fuerzaController.calcularFuerzas(
                sistema.getCargas()
        );

        registrarDatosExperimentales();
    }

    private void registrarDatosExperimentales() {

        List<Fuerza> fuerzas
                = fuerzaController.getFuerzas();

        if (fuerzas.isEmpty()) {
            return;
        }

        if (indiceInteraccionSeleccionada >= fuerzas.size()) {

            indiceInteraccionSeleccionada = 0;
        }

        Fuerza fuerza
                = fuerzas.get(
                        indiceInteraccionSeleccionada
                );

        double distancia
                = fuerza.getDistancia();

        if (distancia <= 0) {
            return;
        }

        double magnitud
                = Math.abs(
                        fuerza.getMagnitud()
                );

        double cargaDestino
                = Math.abs(
                        fuerza
                                .getCargaDestino()
                                .getCarga()
                );

        if (cargaDestino <= 0) {
            return;
        }

        double campo
                = magnitud / cargaDestino;

        double potencial
                = Sistema.K
                * fuerza
                        .getCargaOrigen()
                        .getCarga()
                / distancia;

        graficas.get(0)
                .getDataSet()
                .agregarPunto(
                        distancia,
                        magnitud
                );

        graficas.get(1)
                .getDataSet()
                .agregarPunto(
                        distancia,
                        campo
                );

        graficas.get(2)
                .getDataSet()
                .agregarPunto(
                        distancia,
                        Math.abs(potencial)
                );

        double trabajo
                = graficas.get(0)
                        .getDataSet()
                        .calcularAreaBajoCurva();

        graficas.get(3)
                .getDataSet()
                .agregarPunto(
                        distancia,
                        trabajo
                );

        limitarPuntos();

        generarConclusiones();
    }

    private void limitarPuntos() {

        for (Grafica grafica : graficas) {

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

    private void generarConclusiones() {

        for (Grafica grafica : graficas) {

            grafica.generarConclusion();
        }
    }

    public void limpiarGraficas() {

        for (Grafica grafica : graficas) {

            grafica.limpiar();
        }
    }

    public void reiniciarExperimento() {

        limpiarGraficas();

        actualizarDatos();
    }

    public void seleccionarInteraccion(
            int indice
    ) {

        indiceInteraccionSeleccionada
                = indice;

        limpiarGraficas();

        actualizarDatos();
    }

    public Fuerza getInteraccionActual() {

        List<Fuerza> fuerzas
                = fuerzaController.getFuerzas();

        if (fuerzas.isEmpty()) {
            return null;
        }

        if (indiceInteraccionSeleccionada
                >= fuerzas.size()) {

            indiceInteraccionSeleccionada = 0;
        }

        return fuerzas.get(
                indiceInteraccionSeleccionada
        );
    }

    public String getNombreInteraccionActual() {

        Fuerza fuerza
                = getInteraccionActual();

        if (fuerza == null) {
            return "Sin interacción";
        }

        int q1
                = sistema.getCargas()
                        .indexOf(
                                fuerza.getCargaOrigen()
                        ) + 1;

        int q2
                = sistema.getCargas()
                        .indexOf(
                                fuerza.getCargaDestino()
                        ) + 1;

        return "Q" + q1 + " ↔ Q" + q2;
    }

    public List<String> getInteraccionesDisponibles() {

        List<String> nombres
                = new ArrayList<>();

        List<Fuerza> fuerzas
                = fuerzaController.getFuerzas();

        for (int i = 0;
                i < fuerzas.size();
                i++) {

            Fuerza fuerza
                    = fuerzas.get(i);

            int q1
                    = sistema.getCargas()
                            .indexOf(
                                    fuerza.getCargaOrigen()
                            ) + 1;

            int q2
                    = sistema.getCargas()
                            .indexOf(
                                    fuerza.getCargaDestino()
                            ) + 1;

            nombres.add(
                    "Q" + q1 + " ↔ Q" + q2
            );
        }

        return nombres;
    }

    public int getIndiceInteraccionSeleccionada() {

        return indiceInteraccionSeleccionada;
    }

    public Grafica getGraficaFuerza() {

        return graficas.get(0);
    }

    public Grafica getGraficaCampo() {

        return graficas.get(1);
    }

    public Grafica getGraficaPotencial() {

        return graficas.get(2);
    }

    public Grafica getGraficaTrabajo() {

        return graficas.get(3);
    }

    public List<Grafica> getGraficas() {

        return graficas;
    }

    public FuerzaController getFuerzaController() {

        return fuerzaController;
    }

    public Sistema getSistema() {

        return sistema;
    }
}
