package controlador;

import modelo.Carga;
import java.util.ArrayList;
import java.util.List;
import modelo.Sistema;

public class CargaController {

    private List<Carga> cargas;

    public CargaController() {
        cargas = new ArrayList<>();
    }

    public void agregarCarga(Carga carga) {
        cargas.add(carga);
    }

    public List<Carga> obtenerCargas() {
        return cargas;
    }

    public float calcularFuerza(Carga c1, Carga c2) {
        float distancia = c1.obtenerDistancia(c2);
        if (distancia == 0) return 0;

        return Sistema.K * (c1.getCarga() * c2.getCarga()) / (distancia * distancia);
    }

    public void actualizarSistema(float deltaTiempo) {
        for (Carga c : cargas) {
            c.actualizarPosicion(deltaTiempo);
        }
    }
}