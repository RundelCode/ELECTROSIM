package controlador;

import modelo.Carga;

import java.util.ArrayList;
import java.util.List;

public class GraficaController {

    // Conservamos la constante de Coulomb para todos los cálculos gráficos.
    private static final float K = 8.99e9f;

    // Definimos cuántos puntos usaremos para generar curvas suaves y precisas.
    private static final int MUESTRAS = 200;

    // Generamos el eje horizontal base sobre el que se construyen todas las gráficas.
    public List<Float> generarEjeX() {

        // Preparamos la colección donde almacenaremos cada punto del eje.
        List<Float> valores
                = new ArrayList<>();

        // Definimos el inicio del rango visible de simulación.
        float inicio = -10f;

        // Definimos el final del rango visible de simulación.
        float fin = 10f;

        // Calculamos la separación uniforme entre cada muestra.
        float paso
                = (fin - inicio)
                / (MUESTRAS - 1);

        // Generamos cada punto del eje para construir curvas continuas.
        for (int i = 0;
                i < MUESTRAS;
                i++) {

            // Registramos cada posición dentro del eje horizontal.
            valores.add(
                    inicio + i * paso
            );
        }

        // Retornamos el eje completo para reutilizarlo en otras gráficas.
        return valores;
    }

    // Calculamos la variación del campo eléctrico a lo largo del eje X.
    public List<Float> generarCampoX(
            List<Carga> cargas,
            float y
    ) {

        // Preparamos la colección donde se almacenarán los resultados.
        List<Float> resultado
                = new ArrayList<>();

        // Generamos el eje base sobre el cual se evaluará el campo.
        List<Float> ejeX
                = generarEjeX();

        // Evaluamos el campo eléctrico en cada punto del recorrido.
        for (Float x : ejeX) {

            // Registramos el valor calculado para cada posición.
            resultado.add(
                    calcularCampoEnX(
                            cargas,
                            x,
                            y
                    )
            );
        }

        // Retornamos todos los puntos necesarios para construir la gráfica.
        return resultado;
    }

    // Calculamos la distribución del potencial eléctrico sobre el eje X.
    public List<Float> generarPotencialX(
            List<Carga> cargas,
            float y
    ) {

        // Preparamos la colección donde se almacenarán los resultados.
        List<Float> resultado
                = new ArrayList<>();

        // Generamos el eje base para evaluar el potencial.
        List<Float> ejeX
                = generarEjeX();

        // Evaluamos el potencial eléctrico en cada punto del recorrido.
        for (Float x : ejeX) {

            // Registramos el valor calculado para cada posición.
            resultado.add(
                    calcularPotencial(
                            cargas,
                            x,
                            y
                    )
            );
        }

        // Retornamos todos los puntos para construir la curva.
        return resultado;
    }

    // Calculamos el trabajo acumulado que experimenta una carga de prueba.
    public List<Float> generarTrabajoX(
            List<Carga> cargas,
            float y,
            float cargaPrueba
    ) {

        // Obtenemos previamente la distribución del campo eléctrico.
        List<Float> campo
                = generarCampoX(
                        cargas,
                        y
                );

        // Generamos el eje base sobre el que integraremos el trabajo.
        List<Float> ejeX
                = generarEjeX();

        // Preparamos la colección donde almacenaremos la integración acumulada.
        List<Float> resultado
                = new ArrayList<>();

        // Inicializamos el acumulador de trabajo desde el punto de partida.
        float acumulado = 0f;

        // El primer punto siempre inicia sin trabajo acumulado.
        resultado.add(
                acumulado
        );

        // Recorremos cada tramo para integrar el trabajo progresivamente.
        for (int i = 1;
                i < campo.size();
                i++) {

            // Calculamos el desplazamiento entre dos muestras consecutivas.
            float dx
                    = ejeX.get(i)
                    - ejeX.get(i - 1);

            // Recuperamos el campo del punto anterior.
            float e1
                    = campo.get(i - 1);

            // Recuperamos el campo del punto actual.
            float e2
                    = campo.get(i);

            // Aplicamos integración trapezoidal para aproximar el área.
            float area
                    = ((e1 + e2) * 0.5f)
                    * dx;

            // Acumulamos el trabajo realizado por la carga de prueba.
            acumulado
                    += cargaPrueba
                    * area;

            // Registramos el valor acumulado en este punto.
            resultado.add(
                    acumulado
            );
        }

        // Retornamos la curva completa de trabajo acumulado.
        return resultado;
    }

    // Calculamos la fuerza neta que experimenta cada carga del sistema.
    public List<Float> generarFuerzaNeta(
            List<Carga> cargas
    ) {

        // Preparamos la colección donde almacenaremos cada resultado.
        List<Float> resultado
                = new ArrayList<>();

        // Evaluamos cada carga como objetivo del análisis.
        for (Carga objetivo : cargas) {

            // Inicializamos la componente horizontal de la fuerza.
            float fx = 0f;

            // Inicializamos la componente vertical de la fuerza.
            float fy = 0f;

            // Comparamos la carga objetivo contra todas las demás.
            for (Carga otra : cargas) {

                // Ignoramos la interacción consigo misma.
                if (objetivo == otra) {
                    continue;
                }

                // Calculamos la separación horizontal entre cargas.
                float dx
                        = objetivo.getPosicionX()
                        - otra.getPosicionX();

                // Calculamos la separación vertical entre cargas.
                float dy
                        = objetivo.getPosicionY()
                        - otra.getPosicionY();

                // Calculamos la distancia real entre ambas partículas.
                float distancia
                        = (float) Math.sqrt(
                                dx * dx
                                + dy * dy
                        );

                // Establecemos una distancia mínima para evitar explosiones numéricas.
                distancia
                        = Math.max(
                                0.15f,
                                distancia
                        );

                // Aplicamos la Ley de Coulomb para obtener la magnitud.
                float magnitud
                        = (K
                        * objetivo.getCarga()
                        * otra.getCarga())
                        / (distancia * distancia);

                // Normalizamos la dirección horizontal.
                float nx
                        = dx / distancia;

                // Normalizamos la dirección vertical.
                float ny
                        = dy / distancia;

                // Acumulamos la componente horizontal de la interacción.
                fx
                        += magnitud * nx;

                // Acumulamos la componente vertical de la interacción.
                fy
                        += magnitud * ny;
            }

            // Calculamos la magnitud final de la fuerza resultante.
            float neta
                    = (float) Math.sqrt(
                            fx * fx
                            + fy * fy
                    );

            // Registramos la fuerza neta de esta carga.
            resultado.add(
                    neta
            );
        }

        // Retornamos todas las magnitudes calculadas.
        return resultado;
    }

    // Calculamos el campo eléctrico resultante en un punto específico.
    private float calcularCampoEnX(
            List<Carga> cargas,
            float x,
            float y
    ) {

        // Inicializamos la componente horizontal del campo.
        float ex = 0f;

        // Sumamos la contribución individual de cada carga.
        for (Carga carga : cargas) {

            // Calculamos la separación horizontal respecto al punto.
            float dx
                    = x
                    - carga.getPosicionX();

            // Calculamos la separación vertical respecto al punto.
            float dy
                    = y
                    - carga.getPosicionY();

            // Calculamos la distancia entre la carga y el punto analizado.
            float distancia
                    = (float) Math.sqrt(
                            dx * dx
                            + dy * dy
                    );

            // Limitamos la distancia mínima para mantener estabilidad numérica.
            distancia
                    = Math.max(
                            0.15f,
                            distancia
                    );

            // Calculamos la magnitud del campo generado por esta carga.
            float magnitud
                    = (K * carga.getCarga())
                    / (distancia * distancia);

            // Normalizamos la dirección horizontal.
            float nx
                    = dx / distancia;

            // Acumulamos la contribución horizontal al campo total.
            ex
                    += magnitud * nx;
        }

        // Retornamos el campo eléctrico resultante.
        return ex;
    }

    // Calculamos el potencial eléctrico total en un punto específico.
    private float calcularPotencial(
            List<Carga> cargas,
            float x,
            float y
    ) {

        // Inicializamos el potencial acumulado.
        float v = 0f;

        // Sumamos el aporte individual de cada carga.
        for (Carga carga : cargas) {

            // Calculamos la separación horizontal respecto al punto.
            float dx
                    = x
                    - carga.getPosicionX();

            // Calculamos la separación vertical respecto al punto.
            float dy
                    = y
                    - carga.getPosicionY();

            // Calculamos la distancia entre la carga y el punto evaluado.
            float distancia
                    = (float) Math.sqrt(
                            dx * dx
                            + dy * dy
                    );

            // Limitamos la distancia mínima para mantener estabilidad física.
            distancia
                    = Math.max(
                            0.15f,
                            distancia
                    );

            // Acumulamos el potencial generado por esta carga.
            v
                    += (K * carga.getCarga())
                    / distancia;
        }

        // Retornamos el potencial total en el punto analizado.
        return v;
    }
}
