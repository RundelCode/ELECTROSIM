package modelo;

import java.util.ArrayList;

public class Sistema {

    public static final float K = 8.99e3f;

    private float escalaPixelesAMetros;

    private ArrayList<Carga> cargas;

    public Sistema(
            float escalaPixelesAMetros
    ) {

        this.escalaPixelesAMetros
                = escalaPixelesAMetros;

        this.cargas = new ArrayList<>();
    }

    public void agregarCarga(
            Carga carga
    ) {

        cargas.add(carga);
    }

    public void eliminarCarga(
            Carga carga
    ) {

        cargas.remove(carga);
    }

    public ArrayList<Carga> getCargas() {

        return cargas;
    }

    public float getEscalaPixelesAMetros() {

        return escalaPixelesAMetros;
    }
}
