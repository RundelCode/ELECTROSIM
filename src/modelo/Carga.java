package modelo;

public class Carga {

    private float posicionX;
    private float posicionY;
    private float carga;
    private float velocidadX;
    private float velocidadY;
    private float masa;

    public Carga(float posicionX, float posicionY, float carga, float masa) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.carga = carga;
        this.masa = masa;
        this.velocidadX = 0;
        this.velocidadY = 0;
    }

    public void moverA(float x, float y) {
        this.posicionX = x;
        this.posicionY = y;
    }

    public float obtenerDistancia(Carga otraCarga) {
        float dx = this.posicionX - otraCarga.posicionX;
        float dy = this.posicionY - otraCarga.posicionY;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public void actualizarPosicion(float deltaTiempo) {
        this.posicionX += velocidadX * deltaTiempo;
        this.posicionY += velocidadY * deltaTiempo;
    }

    public float getPosicionX() { return posicionX; }
    public void setPosicionX(float posicionX) { this.posicionX = posicionX; }

    public float getPosicionY() { return posicionY; }
    public void setPosicionY(float posicionY) { this.posicionY = posicionY; }

    public float getCarga() { return carga; }
    public void setCarga(float carga) { this.carga = carga; }

    public float getVelocidadX() { return velocidadX; }
    public void setVelocidadX(float velocidadX) { this.velocidadX = velocidadX; }

    public float getVelocidadY() { return velocidadY; }
    public void setVelocidadY(float velocidadY) { this.velocidadY = velocidadY; }

    public float getMasa() { return masa; }
    public void setMasa(float masa) { this.masa = masa; }
}