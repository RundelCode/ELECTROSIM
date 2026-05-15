package modelo;

public class Fuerza {

    private float magnitud;
    private float fuerzaX;
    private float fuerzaY;

    private Carga cargaOrigen;
    private Carga cargaDestino;

    public Fuerza(
            Carga cargaOrigen,
            Carga cargaDestino
    ) {

        this.cargaOrigen = cargaOrigen;
        this.cargaDestino = cargaDestino;

        calcular();
    }

    private void calcular() {

        float dx
                = cargaDestino.getPosicionX()
                - cargaOrigen.getPosicionX();

        float dy
                = cargaDestino.getPosicionY()
                - cargaOrigen.getPosicionY();

        float distancia
                = (float) Math.sqrt(
                        dx * dx
                        + dy * dy
                );

        distancia
                = Math.max(
                        0.15f,
                        distancia
                );

        magnitud
                = Math.abs(
                        Sistema.K
                        * cargaOrigen.getCarga()
                        * cargaDestino.getCarga()
                )
                / (distancia
                * distancia);

        float nx
                = dx / distancia;

        float ny
                = dy / distancia;

        fuerzaX
                = magnitud * nx;

        fuerzaY
                = magnitud * ny;
    }

    public boolean esRepulsion() {

        return cargaOrigen.getCarga()
                * cargaDestino.getCarga()
                > 0;
    }

    public boolean esAtraccion() {

        return cargaOrigen.getCarga()
                * cargaDestino.getCarga()
                < 0;
    }

    public float getDistancia() {

        return cargaOrigen.obtenerDistancia(
                cargaDestino
        );
    }

    public float getMagnitud() {

        return magnitud;
    }

    public float getFuerzaX() {

        return fuerzaX;
    }

    public float getFuerzaY() {

        return fuerzaY;
    }

    public Carga getCargaOrigen() {

        return cargaOrigen;
    }

    public Carga getCargaDestino() {

        return cargaDestino;
    }
}
