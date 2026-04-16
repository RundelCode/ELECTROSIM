package modelo;

public class Fuerza {

    private float magnitud;
    private float fuerzaX;
    private float fuerzaY;

    public Fuerza() {
        this.magnitud = 0;
        this.fuerzaX = 0;
        this.fuerzaY = 0;
    }

    public void actualizar() {
    }

    public float getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(float magnitud) {
        this.magnitud = magnitud;
    }

    public float getFuerzaX() {
        return fuerzaX;
    }

    public void setFuerzaX(float fuerzaX) {
        this.fuerzaX = fuerzaX;
    }

    public float getFuerzaY() {
        return fuerzaY;
    }

    public void setFuerzaY(float fuerzaY) {
        this.fuerzaY = fuerzaY;
    }
}
