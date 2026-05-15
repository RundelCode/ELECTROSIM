package modelo;

public class DataPoint {

    // Representa el valor independiente dentro de una serie.
    private double x;

    // Representa el resultado asociado al valor evaluado.
    private double y;

    public DataPoint(
            double x,
            double y
    ) {

        // Almacenamos un punto listo para ser usado en cálculos o gráficas.
        this.x = x;

        this.y = y;
    }

    // Retornamos el valor horizontal del punto.
    public double getX() {

        return x;
    }

    // Retornamos el valor vertical asociado al punto.
    public double getY() {

        return y;
    }
}
