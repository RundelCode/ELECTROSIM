package modelo;

import java.util.ArrayList;

public class Sistema {

    private float escalaPixelesAMetros;
    private ArrayList<Carga> cargas;
    private ArrayList<Fuerza> fuerzas;
    private ArrayList<DataSet> dataSets;

    public Sistema(float escalaPixelesAMetros) {
        this.escalaPixelesAMetros = escalaPixelesAMetros;
        this.cargas = new ArrayList<>();
        this.fuerzas = new ArrayList<>();
        this.dataSets = new ArrayList<>();
    }

    public void agregarCarga(Carga carga) {
        cargas.add(carga);
    }

    public void eliminarCarga(int id) {
        cargas.remove(id);
    }

    public void ejecutarSimulacion() {
    }

    public void limpiarEscena() {
        cargas.clear();
    }

    public void ejecutarTick() {
    }

    public void actualizarFisica() {
    }

    public float getEscalaPixelesAMetros() { return escalaPixelesAMetros; }
    public void setEscalaPixelesAMetros(float escalaPixelesAMetros) { this.escalaPixelesAMetros = escalaPixelesAMetros; }

    public ArrayList<Carga> getCargas() { return cargas; }
    public void setCargas(ArrayList<Carga> cargas) { this.cargas = cargas; }

    public ArrayList<Fuerza> getFuerzas() { return fuerzas; }
    public void setFuerzas(ArrayList<Fuerza> fuerzas) { this.fuerzas = fuerzas; }

    public ArrayList<DataSet> getDataSets() { return dataSets; }
    public void setDataSets(ArrayList<DataSet> dataSets) { this.dataSets = dataSets; }
}