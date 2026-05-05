package modelo;

import java.util.ArrayList;

public class Sistema {

    private float constanteK;
    private float escalaPixelesAMetros;
    private ArrayList<Carga> cargas;

    public Sistema(float escalaPixelesAMetros) {
        this.constanteK = 8.99e9f;
        this.escalaPixelesAMetros = escalaPixelesAMetros;
        this.cargas = new ArrayList<>();
    }

    public void agregarCarga(Carga carga) {
        cargas.add(carga);
    }

    public void eliminarCarga(Carga carga) {
        cargas.remove(carga);
    }

    public ArrayList<Carga> getCargas() {
        return cargas;
    }

    public float getConstanteK() {
        return constanteK;
    }

    public float getEscalaPixelesAMetros() {
        return escalaPixelesAMetros;
    }
}
