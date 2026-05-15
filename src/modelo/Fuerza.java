package modelo;

public class Fuerza {

    // Magnitud total de la interacción entre ambas cargas.
    private float magnitud;

    // Componentes vectoriales de la fuerza sobre cada eje.
    private float fuerzaX;

    private float fuerzaY;

    // Referencias a las partículas involucradas en la interacción.
    private Carga cargaOrigen;

    private Carga cargaDestino;

    public Fuerza(
            Carga cargaOrigen,
            Carga cargaDestino
    ) {

        // Conservamos ambas cargas para calcular y consultar la interacción.
        this.cargaOrigen = cargaOrigen;

        this.cargaDestino = cargaDestino;

        // Calculamos la fuerza apenas se crea la relación física.
        calcular();
    }

    private void calcular() {

        // Calculamos el desplazamiento entre ambas partículas.
        float dx
                = cargaDestino.getPosicionX()
                - cargaOrigen.getPosicionX();

        float dy
                = cargaDestino.getPosicionY()
                - cargaOrigen.getPosicionY();

        // Obtenemos la separación real dentro del plano.
        float distancia
                = (float) Math.sqrt(
                        dx * dx
                        + dy * dy
                );

        // Evitamos divisiones extremas cuando las cargas están muy cerca.
        distancia
                = Math.max(
                        0.15f,
                        distancia
                );

        // Aplicamos directamente la Ley de Coulomb.
        magnitud
                = Math.abs(
                        Sistema.K
                        * cargaOrigen.getCarga()
                        * cargaDestino.getCarga()
                )
                / (distancia
                * distancia);

        // Normalizamos la dirección para obtener un vector unitario.
        float nx
                = dx / distancia;

        float ny
                = dy / distancia;

        // Descomponemos la fuerza en componentes cartesianas.
        fuerzaX
                = magnitud * nx;

        fuerzaY
                = magnitud * ny;
    }

    public boolean esRepulsion() {

        // Cargas del mismo signo generan repulsión.
        return cargaOrigen.getCarga()
                * cargaDestino.getCarga()
                > 0;
    }

    public boolean esAtraccion() {

        // Cargas de signo opuesto generan atracción.
        return cargaOrigen.getCarga()
                * cargaDestino.getCarga()
                < 0;
    }

    public float getDistancia() {

        // Retornamos la distancia actual entre ambas partículas.
        return cargaOrigen.obtenerDistancia(
                cargaDestino
        );
    }

    public float getMagnitud() {

        // Exponemos la intensidad total de la interacción.
        return magnitud;
    }

    public float getFuerzaX() {

        // Exponemos la componente horizontal de la fuerza.
        return fuerzaX;
    }

    public float getFuerzaY() {

        // Exponemos la componente vertical de la fuerza.
        return fuerzaY;
    }

    public Carga getCargaOrigen() {

        // Retornamos la partícula desde la cual nace la interacción.
        return cargaOrigen;
    }

    public Carga getCargaDestino() {

        // Retornamos la partícula que recibe la interacción.
        return cargaDestino;
    }
}
