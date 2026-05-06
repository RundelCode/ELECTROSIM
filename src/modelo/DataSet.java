package modelo;

import java.util.ArrayList;
import java.util.List;

public class DataSet {

    private List<DataPoint> puntos;

    public DataSet() {

        puntos = new ArrayList<>();
    }

    public void agregarPunto(
            double x,
            double y
    ) {

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

        puntos.add(
                new DataPoint(x, y)
        );
    }

    public void limpiar() {

        puntos.clear();
    }

    public List<DataPoint> getPuntos() {

        return puntos;
    }

    public double calcularAreaBajoCurva() {

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

            double base
                    = p2.getX() - p1.getX();

            double altura
                    = (p1.getY() + p2.getY())
                    / 2;

            area += base * altura;
        }

        return Math.abs(area);
    }

    public double obtenerMaximoY() {

        double max = 1;

        for (DataPoint p : puntos) {

            max
                    = Math.max(
                            max,
                            p.getY()
                    );
        }

        return max;
    }

    public double obtenerMinimoX() {

        if (puntos.isEmpty()) {
            return 0;
        }

        double min
                = puntos.get(0).getX();

        for (DataPoint p : puntos) {

            min
                    = Math.min(
                            min,
                            p.getX()
                    );
        }

        return min;
    }

    public double obtenerMaximoX() {

        if (puntos.isEmpty()) {
            return 1;
        }

        double max
                = puntos.get(0).getX();

        for (DataPoint p : puntos) {

            max
                    = Math.max(
                            max,
                            p.getX()
                    );
        }

        return max;
    }
}
