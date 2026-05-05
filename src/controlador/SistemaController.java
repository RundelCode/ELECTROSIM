package controlador;

import modelo.Carga;
import modelo.Sistema;

public class SistemaController {

    private Sistema sistema;
    private FuerzaController fuerzaController;

    public SistemaController(Sistema sistema) {
        this.sistema = sistema;
        this.fuerzaController = new FuerzaController();
    }

    public void agregarCarga(float x, float y, float valor, float masa) {

        if (sistema.getCargas().size() >= 4) {
            return;
        }

        sistema.agregarCarga(
                new Carga(x, y, valor, masa)
        );
    }

    public void iniciarSimulacion() {

        fuerzaController.calcularFuerzas(
                sistema.getCargas()
        );
    }

    public FuerzaController getFuerzaController() {
        return fuerzaController;
    }

    public Sistema getSistema() {
        return sistema;
    }
}
