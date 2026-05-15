package modelo;

public class Carga {

    // Coordenadas actuales de la partícula dentro del plano.
    private float posicionX;

    private float posicionY;

    // Magnitud eléctrica asociada a esta carga.
    private float carga;

    // Componentes de velocidad usadas en la simulación.
    private float velocidadX;

    private float velocidadY;

    // Masa utilizada para futuros cálculos dinámicos.
    private float masa;

    public Carga(float posicionX, float posicionY, float carga, float masa) {

        // Inicializamos el estado físico base de la partícula.
        this.posicionX = posicionX;

        this.posicionY = posicionY;

        this.carga = carga;

        this.masa = masa;

        // Toda carga comienza en reposo al ser creada.
        this.velocidadX = 0;

        this.velocidadY = 0;
    }

    public void moverA(float x, float y) {

        // Reubicamos manualmente la carga dentro del plano.
        this.posicionX = x;

        this.posicionY = y;
    }

    public float obtenerDistancia(Carga otraCarga) {

        // Calculamos la separación real entre dos partículas.
        float dx = this.posicionX - otraCarga.posicionX;

        float dy = this.posicionY - otraCarga.posicionY;

        return (float) Math.sqrt(
                dx * dx + dy * dy
        );
    }

    public void actualizarPosicion(float deltaTiempo) {

        // Actualizamos la posición usando el desplazamiento acumulado.
        this.posicionX += velocidadX * deltaTiempo;

        this.posicionY += velocidadY * deltaTiempo;
    }

    // Exponemos la posición horizontal actual de la carga.
    public float getPosicionX() {
        return posicionX;
    }

    // Permitimos actualizar manualmente la posición horizontal.
    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }

    // Exponemos la posición vertical actual de la carga.
    public float getPosicionY() {
        return posicionY;
    }

    // Permitimos actualizar manualmente la posición vertical.
    public void setPosicionY(float posicionY) {
        this.posicionY = posicionY;
    }

    // Retornamos la magnitud eléctrica asociada a la partícula.
    public float getCarga() {
        return carga;
    }

    // Permitimos modificar el valor de la carga durante la simulación.
    public void setCarga(float carga) {
        this.carga = carga;
    }

    // Retornamos la velocidad actual sobre el eje X.
    public float getVelocidadX() {
        return velocidadX;
    }

    // Actualizamos la velocidad horizontal de la partícula.
    public void setVelocidadX(float velocidadX) {
        this.velocidadX = velocidadX;
    }

    // Retornamos la velocidad actual sobre el eje Y.
    public float getVelocidadY() {
        return velocidadY;
    }

    // Actualizamos la velocidad vertical de la partícula.
    public void setVelocidadY(float velocidadY) {
        this.velocidadY = velocidadY;
    }

    // Retornamos la masa asociada a esta carga.
    public float getMasa() {
        return masa;
    }

    // Permitimos ajustar la masa según el escenario simulado.
    public void setMasa(float masa) {
        this.masa = masa;
    }
}
