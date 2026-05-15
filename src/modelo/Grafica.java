package modelo;

public class Grafica {

    // Información descriptiva usada para presentar la gráfica al usuario.
    private String titulo;

    private String descripcion;

    private String unidadX;

    private String unidadY;

    // Conclusión generada automáticamente a partir de los datos.
    private String conclusion;

    // Colección de puntos que representa el comportamiento de la gráfica.
    private DataSet dataSet;

    public Grafica(
            String titulo,
            String descripcion,
            String unidadX,
            String unidadY
    ) {

        // Inicializamos la información base que define esta gráfica.
        this.titulo = titulo;

        this.descripcion = descripcion;

        this.unidadX = unidadX;

        this.unidadY = unidadY;

        // Cada gráfica administra su propia serie de datos.
        this.dataSet = new DataSet();

        // La conclusión se construirá una vez existan suficientes muestras.
        this.conclusion = "";
    }

    public void generarConclusion() {

        // Necesitamos al menos dos puntos para identificar una tendencia.
        if (dataSet.getPuntos().size() < 2) {

            conclusion
                    = "No hay suficientes datos.";

            return;
        }

        // Tomamos el primer valor como referencia inicial.
        double primerValor
                = dataSet.getPuntos()
                        .get(0)
                        .getY();

        // Tomamos el último valor para comparar la evolución del fenómeno.
        double ultimoValor
                = dataSet.getPuntos()
                        .get(
                                dataSet
                                        .getPuntos()
                                        .size() - 1
                        )
                        .getY();

        // Interpretamos automáticamente si la tendencia fue ascendente.
        if (ultimoValor > primerValor) {

            conclusion
                    = "La magnitud aumentó "
                    + "durante la interacción.";

        } else {

            // Si no aumenta, asumimos una tendencia descendente.
            conclusion
                    = "La magnitud disminuyó "
                    + "durante la interacción.";
        }
    }

    public void limpiar() {

        // Reiniciamos tanto los datos como la interpretación generada.
        dataSet.limpiar();

        conclusion = "";
    }

    // Retornamos el nombre principal de la gráfica.
    public String getTitulo() {
        return titulo;
    }

    // Retornamos la explicación conceptual asociada.
    public String getDescripcion() {
        return descripcion;
    }

    // Retornamos la unidad usada sobre el eje horizontal.
    public String getUnidadX() {
        return unidadX;
    }

    // Retornamos la unidad usada sobre el eje vertical.
    public String getUnidadY() {
        return unidadY;
    }

    // Exponemos la interpretación generada a partir de los datos.
    public String getConclusion() {
        return conclusion;
    }

    // Retornamos la serie numérica asociada a esta gráfica.
    public DataSet getDataSet() {
        return dataSet;
    }
}
