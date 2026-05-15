package controlador;

import modelo.Carga;
import modelo.Fuerza;

import java.util.ArrayList;
import java.util.List;

public class FuerzaController {

    // Centralizamos todas las interacciones físicas calculadas entre cargas.
    private List<Fuerza> fuerzas;

    // Inicializamos el controlador encargado de gestionar las fuerzas del sistema.
    public FuerzaController() {

        // Preparamos la colección donde se almacenarán las interacciones activas.
        fuerzas = new ArrayList<>();
    }

    // Recalculamos todas las interacciones eléctricas según el estado actual.
    public void calcularFuerzas(List<Carga> cargas) {

        // Limpiamos cálculos anteriores antes de reconstruir el sistema físico.
        fuerzas.clear();

        // Seleccionamos cada carga como posible origen de interacción.
        for (int i = 0; i < cargas.size(); i++) {

            // Comparamos únicamente con las cargas siguientes para evitar duplicados.
            for (int j = i + 1; j < cargas.size(); j++) {

                // Construimos la interacción física entre ambas partículas.
                Fuerza fuerza = new Fuerza(
                        cargas.get(i),
                        cargas.get(j)
                );

                // Registramos la fuerza para su posterior visualización y análisis.
                fuerzas.add(fuerza);
            }
        }
    }

    // Retornamos todas las interacciones calculadas del sistema actual.
    public List<Fuerza> getFuerzas() {

        // Compartimos las fuerzas activas con la interfaz y otros módulos.
        return fuerzas;
    }
}
