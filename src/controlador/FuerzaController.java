package controlador;

import modelo.Carga;
import modelo.Fuerza;

public class FuerzaController {

    private static final float K = 8.99e9f;

    public float calcularCoulomb(Carga origen, Carga destino) {
        float distancia = obtenerDistancia(origen, destino);
        if (distancia == 0) {
            return 0;
        }
        return K * Math.abs(origen.getCarga() * destino.getCarga()) / (distancia * distancia);
    }

    public float obtenerDistancia(Carga origen, Carga destino) {
        float dx = destino.getPosicionX() - origen.getPosicionX();
        float dy = destino.getPosicionY() - origen.getPosicionY();
        return (float) Math.sqrt((dx * dx) + (dy * dy));
    }

    public void obtenerDireccion() {
    }

    public void actualizarFuerza(Fuerza f, Carga o, Carga d) {
        float m = calcularCoulomb(o, d);
        f.setMagnitud(m);
        f.actualizar();
    }
}
