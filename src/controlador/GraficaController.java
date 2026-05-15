package controlador;

import modelo.Carga;

import java.util.ArrayList;
import java.util.List;

public class GraficaController {

    private static final float K = 8.99e9f;
    private static final int MUESTRAS = 200;

    public List<Float> generarEjeX() {

        List<Float> valores
                = new ArrayList<>();

        float inicio = -10f;
        float fin = 10f;

        float paso
                = (fin - inicio)
                / (MUESTRAS - 1);

        for (int i = 0;
                i < MUESTRAS;
                i++) {

            valores.add(
                    inicio + i * paso
            );
        }

        return valores;
    }

    public List<Float> generarCampoX(
            List<Carga> cargas,
            float y
    ) {

        List<Float> resultado
                = new ArrayList<>();

        List<Float> ejeX
                = generarEjeX();

        for (Float x : ejeX) {

            resultado.add(
                    calcularCampoEnX(
                            cargas,
                            x,
                            y
                    )
            );
        }

        return resultado;
    }

    public List<Float> generarPotencialX(
            List<Carga> cargas,
            float y
    ) {

        List<Float> resultado
                = new ArrayList<>();

        List<Float> ejeX
                = generarEjeX();

        for (Float x : ejeX) {

            resultado.add(
                    calcularPotencial(
                            cargas,
                            x,
                            y
                    )
            );
        }

        return resultado;
    }

    public List<Float> generarTrabajoX(
            List<Carga> cargas,
            float y,
            float cargaPrueba
    ) {

        List<Float> campo
                = generarCampoX(
                        cargas,
                        y
                );

        List<Float> ejeX
                = generarEjeX();

        List<Float> resultado
                = new ArrayList<>();

        float acumulado = 0f;

        resultado.add(
                acumulado
        );

        for (int i = 1;
                i < campo.size();
                i++) {

            float dx
                    = ejeX.get(i)
                    - ejeX.get(i - 1);

            float e1
                    = campo.get(i - 1);

            float e2
                    = campo.get(i);

            float area
                    = ((e1 + e2) * 0.5f)
                    * dx;

            acumulado
                    += cargaPrueba
                    * area;

            resultado.add(
                    acumulado
            );
        }

        return resultado;
    }

    public List<Float> generarFuerzaNeta(
            List<Carga> cargas
    ) {

        List<Float> resultado
                = new ArrayList<>();

        for (Carga objetivo : cargas) {

            float fx = 0f;
            float fy = 0f;

            for (Carga otra : cargas) {

                if (objetivo == otra) {
                    continue;
                }

                float dx
                        = objetivo.getPosicionX()
                        - otra.getPosicionX();

                float dy
                        = objetivo.getPosicionY()
                        - otra.getPosicionY();

                float distancia
                        = (float) Math.sqrt(
                                dx * dx
                                + dy * dy
                        );

                distancia
                        = Math.max(
                                0.15f,
                                distancia
                        );

                float magnitud
                        = (K
                        * objetivo.getCarga()
                        * otra.getCarga())
                        / (distancia * distancia);

                float nx
                        = dx / distancia;

                float ny
                        = dy / distancia;

                fx
                        += magnitud * nx;

                fy
                        += magnitud * ny;
            }

            float neta
                    = (float) Math.sqrt(
                            fx * fx
                            + fy * fy
                    );

            resultado.add(
                    neta
            );
        }

        return resultado;
    }

    private float calcularCampoEnX(
            List<Carga> cargas,
            float x,
            float y
    ) {

        float ex = 0f;

        for (Carga carga : cargas) {

            float dx
                    = x
                    - carga.getPosicionX();

            float dy
                    = y
                    - carga.getPosicionY();

            float distancia
                    = (float) Math.sqrt(
                            dx * dx
                            + dy * dy
                    );

            distancia
                    = Math.max(
                            0.15f,
                            distancia
                    );

            float magnitud
                    = (K * carga.getCarga())
                    / (distancia * distancia);

            float nx
                    = dx / distancia;

            ex
                    += magnitud * nx;
        }

        return ex;
    }

    private float calcularPotencial(
            List<Carga> cargas,
            float x,
            float y
    ) {

        float v = 0f;

        for (Carga carga : cargas) {

            float dx
                    = x
                    - carga.getPosicionX();

            float dy
                    = y
                    - carga.getPosicionY();

            float distancia
                    = (float) Math.sqrt(
                            dx * dx
                            + dy * dy
                    );

            distancia
                    = Math.max(
                            0.15f,
                            distancia
                    );

            v
                    += (K * carga.getCarga())
                    / distancia;
        }

        return v;
    }
}
