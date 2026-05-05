package controlador;

import modelo.Carga;
import modelo.Sistema;

public class SistemaController {

    private Sistema sistema;

    public SistemaController(Sistema sistema) {
        this.sistema = sistema;
    }

    public void agregarCarga(float x, float y, float valor, float masa) {
        sistema.agregarCarga(new Carga(x, y, valor, masa));
    }

    public void actualizar(float deltaTiempo) {

        for (Carga c1 : sistema.getCargas()) {

            float fx = 0;
            float fy = 0;

            for (Carga c2 : sistema.getCargas()) {
                if (c1 == c2) {
                    continue;
                }

                float dx = c2.getPosicionX() - c1.getPosicionX();
                float dy = c2.getPosicionY() - c1.getPosicionY();

                float distancia = (float) Math.sqrt(dx * dx + dy * dy);
                if (distancia < 1) {
                    continue;
                }

                float fuerza = sistema.getConstanteK()
                        * (c1.getCarga() * c2.getCarga())
                        / (distancia * distancia);

                float nx = dx / distancia;
                float ny = dy / distancia;

                fx += fuerza * nx;
                fy += fuerza * ny;
            }

            float ax = fx / c1.getMasa();
            float ay = fy / c1.getMasa();

            c1.setVelocidadX(c1.getVelocidadX() + ax * deltaTiempo);
            c1.setVelocidadY(c1.getVelocidadY() + ay * deltaTiempo);
        }

        for (Carga c : sistema.getCargas()) {
            c.actualizarPosicion(deltaTiempo);
        }
    }

    public Sistema getSistema() {
        return sistema;
    }
}
