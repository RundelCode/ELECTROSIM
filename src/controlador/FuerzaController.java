package controlador;

import modelo.Carga;
import modelo.Fuerza;

import java.util.ArrayList;
import java.util.List;

public class FuerzaController {

    private List<Fuerza> fuerzas;

    public FuerzaController() {
        fuerzas = new ArrayList<>();
    }

    public void calcularFuerzas(List<Carga> cargas) {

        fuerzas.clear();

        for (int i = 0; i < cargas.size(); i++) {

            for (int j = i + 1; j < cargas.size(); j++) {

                Fuerza fuerza = new Fuerza(
                        cargas.get(i),
                        cargas.get(j)
                );

                fuerzas.add(fuerza);
            }
        }
    }

    public List<Fuerza> getFuerzas() {
        return fuerzas;
    }
}