package modelo;

public class Fuerza {

    private float magnitud;
    private float fuerzaX;
    private float fuerzaY;
    private Carga cargaOrigen;
    private Carga cargaDestino;

    public Fuerza(Carga cargaOrigen, Carga cargaDestino) {
        this.cargaOrigen = cargaOrigen;
        this.cargaDestino = cargaDestino;
        this.magnitud = 0;
        this.fuerzaX = 0;
        this.fuerzaY = 0;
    }

    public void calcularMagnitud() {
    }

    public void obtenerDireccion() {
    }

    public void actualizar() {
    }

    public float getMagnitud() { return magnitud; }
    public void setMagnitud(float magnitud) { this.magnitud = magnitud; }

    public float getFuerzaX() { return fuerzaX; }
    public void setFuerzaX(float fuerzaX) { this.fuerzaX = fuerzaX; }

    public float getFuerzaY() { return fuerzaY; }
    public void setFuerzaY(float fuerzaY) { this.fuerzaY = fuerzaY; }

    public Carga getCargaOrigen() { return cargaOrigen; }
    public void setCargaOrigen(Carga cargaOrigen) { this.cargaOrigen = cargaOrigen; }

    public Carga getCargaDestino() { return cargaDestino; }
    public void setCargaDestino(Carga cargaDestino) { this.cargaDestino = cargaDestino; }
}