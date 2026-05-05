package controlador;

import modelo.Carga;
import java.util.ArrayList;
import java.util.List;

public class CargaController {

    private List<Carga> cargas;
    private static final float K = 9_000_000_000f;

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

        return K * (c1.getCarga() * c2.getCarga()) / (distancia * distancia);
    }

    public void actualizarSistema(float deltaTiempo) {
        for (Carga c : cargas) {
            c.actualizarPosicion(deltaTiempo);
        }
    }
}