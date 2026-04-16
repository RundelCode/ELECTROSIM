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
    
    ////Hola
    public void moverA(float x, float y) {
    }

    public float obtenerDistancia(Carga otraCarga) {
        return 0;
    }


}