package modelo;

import java.util.ArrayList;
import java.util.List;

public class DataSet {

    // Almacena la secuencia de puntos que forman una serie de datos.
    private List<DataPoint> puntos;

    public DataSet() {

        // Inicializamos una colección vacía lista para recibir muestras.
        puntos = new ArrayList<>();
    }

    public void agregarPunto(
            double x,
            double y
    ) {

        // Evitamos registrar puntos casi idénticos para reducir ruido visual.
        if (!puntos.isEmpty()) {

            DataPoint ultimo
                    = puntos.get(
                            puntos.size() - 1
                    );

            double dx
                    = Math.abs(
                            ultimo.getX() - x
                    );

            double dy
                    = Math.abs(
                            ultimo.getY() - y
                    );

            if (dx < 0.05 && dy < 0.05) {
                return;
            }
        }

        // Incorporamos el nuevo punto a la serie.
        puntos.add(
                new DataPoint(x, y)
        );
    }

    public void limpiar() {

        // Reiniciamos completamente la colección de datos.
        puntos.clear();
    }

    public List<DataPoint> getPuntos() {

        // Exponemos la serie completa para análisis o renderizado.
        return puntos;
    }

    public double calcularAreaBajoCurva() {

        // Necesitamos al menos dos puntos para estimar un área.
        if (puntos.size() < 2) {
            return 0;
        }

        double area = 0;

        for (int i = 0;
                i < puntos.size() - 1;
                i++) {

            DataPoint p1 = puntos.get(i);

            DataPoint p2
                    = puntos.get(i + 1);

            // Calculamos la separación horizontal entre muestras.
            double base
                    = p2.getX() - p1.getX();

            // Aproximamos la altura usando el promedio de ambos extremos.
            double altura
                    = (p1.getY() + p2.getY())
                    / 2;

            // Sumamos el área del segmento usando la regla del trapecio.
            area += base * altura;
        }

        // Retornamos el área en magnitud positiva para facilitar interpretación.
        return Math.abs(area);
    }

    public double obtenerMaximoY() {

        // Usamos un valor base para evitar escalas vacías en gráficas.
        double max = 1;

        for (DataPoint p : puntos) {

            // Buscamos el valor vertical más alto de toda la serie.
            max
                    = Math.max(
                            max,
                            p.getY()
                    );
        }

        return max;
    }

    public double obtenerMinimoX() {

        // Si no existen puntos retornamos un valor seguro.
        if (puntos.isEmpty()) {
            return 0;
        }

        // Partimos del primer punto como referencia inicial.
        double min
                = puntos.get(0).getX();

        for (DataPoint p : puntos) {

            // Buscamos el menor valor horizontal registrado.
            min
                    = Math.min(
                            min,
                            p.getX()
                    );
        }

        return min;
    }

    public double obtenerMaximoX() {

        // Si no existen datos retornamos un rango base.
        if (puntos.isEmpty()) {
            return 1;
        }

        // Partimos del primer punto como referencia inicial.
        double max
                = puntos.get(0).getX();

        for (DataPoint p : puntos) {

            // Buscamos el mayor valor horizontal registrado.
            max
                    = Math.max(
                            max,
                            p.getX()
                    );
        }

        return max;
    }
}
