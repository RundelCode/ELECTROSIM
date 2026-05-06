package modelo;

public class Grafica {

    private String titulo;

    private String descripcion;

    private String unidadX;

    private String unidadY;

    private String conclusion;

    private DataSet dataSet;

    public Grafica(
            String titulo,
            String descripcion,
            String unidadX,
            String unidadY
    ) {

        this.titulo = titulo;

        this.descripcion = descripcion;

        this.unidadX = unidadX;

        this.unidadY = unidadY;

        this.dataSet = new DataSet();

        this.conclusion = "";
    }

    public void generarConclusion() {

        if (dataSet.getPuntos().size() < 2) {

            conclusion
                    = "No hay suficientes datos.";

            return;
        }

        double primerValor
                = dataSet.getPuntos()
                        .get(0)
                        .getY();

        double ultimoValor
                = dataSet.getPuntos()
                        .get(
                                dataSet
                                        .getPuntos()
                                        .size() - 1
                        )
                        .getY();

        if (ultimoValor > primerValor) {

            conclusion
                    = "La magnitud aumentó "
                    + "durante la interacción.";

        } else {

            conclusion
                    = "La magnitud disminuyó "
                    + "durante la interacción.";
        }
    }

    public void limpiar() {

        dataSet.limpiar();

        conclusion = "";
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUnidadX() {
        return unidadX;
    }

    public String getUnidadY() {
        return unidadY;
    }

    public String getConclusion() {
        return conclusion;
    }

    public DataSet getDataSet() {
        return dataSet;
    }
}
