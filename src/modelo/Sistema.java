package modelo;

import java.util.ArrayList;

public class Sistema {

    private float constanteK;
    private float escalaPixelesAMetros;
    private ArrayList<Carga> cargas;
    private ArrayList<Fuerza> fuerzas;
    private ArrayList<DataSet> dataSets;

    public Sistema(float escalaPixelesAMetros) {
        this.constanteK = 8.99e9f;
        this.escalaPixelesAMetros = escalaPixelesAMetros;
        this.cargas = new ArrayList<>();
        this.fuerzas = new ArrayList<>();
        this.dataSets = new ArrayList<>();
    }

    public void agregarCarga(Carga carga) {
    }

    public void eliminarCarga(int id) {
    }

    public void ejecutarSimulacion() {
    }

    public void limpiarEscena() {
    }

    public void ejecutarTick() {
    }

    public void actualizarFisica() {
    }

    public float getConstanteK() { return constanteK; }
    public void setConstanteK(float constanteK) { this.constanteK = constanteK; }

    public float getEscalaPixelesAMetros() { return escalaPixelesAMetros; }
    public void setEscalaPixelesAMetros(float escalaPixelesAMetros) { this.escalaPixelesAMetros = escalaPixelesAMetros; }

    public ArrayList<Carga> getCargas() { return cargas; }
    public void setCargas(ArrayList<Carga> cargas) { this.cargas = cargas; }

    public ArrayList<Fuerza> getFuerzas() { return fuerzas; }
    public void setFuerzas(ArrayList<Fuerza> fuerzas) { this.fuerzas = fuerzas; }

    public ArrayList<DataSet> getDataSets() { return dataSets; }
    public void setDataSets(ArrayList<DataSet> dataSets) { this.dataSets = dataSets; }
}