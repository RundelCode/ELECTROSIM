package modelo;

import java.util.ArrayList;

public class DataSet {

    private ArrayList<Float> muestrasFuerza;
    private ArrayList<Float> muestrasDistancia;
    private Carga cargaOrigen;
    private Carga cargaDestino;

    public DataSet(Carga cargaOrigen, Carga cargaDestino) {
        this.cargaOrigen = cargaOrigen;
        this.cargaDestino = cargaDestino;
        this.muestrasFuerza = new ArrayList<>();
        this.muestrasDistancia = new ArrayList<>();
    }

    public void registrarPunto(float distancia, float fuerza) {
    }

    public void limpiarHistorial() {
    }

    public float[] generarEstadisticas() {
        return null;
    }

    public String getEtiqueta() {
        return null;
    }

    public ArrayList<Float> getMuestrasFuerza() { return muestrasFuerza; }
    public void setMuestrasFuerza(ArrayList<Float> muestrasFuerza) { this.muestrasFuerza = muestrasFuerza; }

    public ArrayList<Float> getMuestrasDistancia() { return muestrasDistancia; }
    public void setMuestrasDistancia(ArrayList<Float> muestrasDistancia) { this.muestrasDistancia = muestrasDistancia; }

    public Carga getCargaOrigen() { return cargaOrigen; }
    public void setCargaOrigen(Carga cargaOrigen) { this.cargaOrigen = cargaOrigen; }

    public Carga getCargaDestino() { return cargaDestino; }
    public void setCargaDestino(Carga cargaDestino) { this.cargaDestino = cargaDestino; }
}