package modelo;

public class Grafica {

    private TiposGrafica tipo;
    private String etiquetaX;
    private String etiquetaY;
    private int limiteX;
    private int limiteY;
    private DataSet dataSet;

    public Grafica(TiposGrafica tipo, DataSet dataSet) {
        this.tipo = tipo;
        this.dataSet = dataSet;
        this.limiteX = 0;
        this.limiteY = 0;
    }

    public void renderizar() {
    }

    public void zoom(float nivel) {
    }

    public void exportarImagen() {
    }

    public void ajustarLimites() {
    }

    public TiposGrafica getTipo() { return tipo; }
    public void setTipo(TiposGrafica tipo) { this.tipo = tipo; }

    public String getEtiquetaX() { return etiquetaX; }
    public void setEtiquetaX(String etiquetaX) { this.etiquetaX = etiquetaX; }

    public String getEtiquetaY() { return etiquetaY; }
    public void setEtiquetaY(String etiquetaY) { this.etiquetaY = etiquetaY; }

    public int getLimiteX() { return limiteX; }
    public void setLimiteX(int limiteX) { this.limiteX = limiteX; }

    public int getLimiteY() { return limiteY; }
    public void setLimiteY(int limiteY) { this.limiteY = limiteY; }

    public DataSet getDataSet() { return dataSet; }
    public void setDataSet(DataSet dataSet) { this.dataSet = dataSet; }
}