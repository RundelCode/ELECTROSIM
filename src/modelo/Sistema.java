package modelo;

import java.util.ArrayList;

public class Sistema {

    // Constante de Coulomb usada en todos los cálculos físicos.
    public static final float K = 8.99e9f;

    // Factor que permite convertir coordenadas visuales a unidades físicas.
    private float escalaPixelesAMetros;

    // Colección de partículas activas dentro del sistema.
    private ArrayList<Carga> cargas;

    public Sistema(
            float escalaPixelesAMetros
    ) {

        // Definimos la escala base con la que operará la simulación.
        this.escalaPixelesAMetros
                = escalaPixelesAMetros;

        // Inicializamos el conjunto de cargas del sistema.
        this.cargas = new ArrayList<>();
    }

    public void agregarCarga(
            Carga carga
    ) {

        // Incorporamos una nueva partícula al entorno físico.
        cargas.add(carga);
    }

    public void eliminarCarga(
            Carga carga
    ) {

        // Removemos una partícula del sistema actual.
        cargas.remove(carga);
    }

    public ArrayList<Carga> getCargas() {

        // Exponemos todas las cargas activas para cálculos o renderizado.
        return cargas;
    }

    public float getEscalaPixelesAMetros() {

        // Retornamos la relación actual entre espacio visual y espacio físico.
        return escalaPixelesAMetros;
    }
}
