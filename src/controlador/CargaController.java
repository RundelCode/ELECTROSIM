package controlador;

import modelo.Carga;
import java.util.ArrayList;
import java.util.List;
import modelo.Sistema;

public class CargaController {

    // Centralizamos todas las cargas activas para manipularlas desde la simulación.
    private List<Carga> cargas;

    // Inicializamos el controlador encargado de administrar las partículas del sistema.
    public CargaController() {

        // Preparamos la colección base donde se registrarán las cargas creadas.
        cargas = new ArrayList<>();
    }

    // Incorporamos una nueva carga al sistema activo de ELECTROSIM.
    public void agregarCarga(Carga carga) {

        // Persistimos la nueva partícula para que participe en la simulación.
        cargas.add(carga);
    }

    // Retornamos todas las cargas registradas para otras capas del sistema.
    public List<Carga> obtenerCargas() {

        // Compartimos el estado actual de las partículas activas.
        return cargas;
    }

    // Aplicamos directamente la Ley de Coulomb entre dos cargas específicas.
    public float calcularFuerza(Carga c1, Carga c2) {

        // Calculamos la separación real entre ambas partículas.
        float distancia = c1.obtenerDistancia(c2);

        // Evitamos divisiones inválidas cuando ambas cargas coinciden en posición.
        if (distancia == 0) {
            return 0;
        }

        // Calculamos la magnitud de la interacción eléctrica entre ambas cargas.
        return Sistema.K * (c1.getCarga() * c2.getCarga()) / (distancia * distancia);
    }

    // Actualizamos la posición de todas las cargas según el tiempo transcurrido.
    public void actualizarSistema(float deltaTiempo) {

        // Recorremos cada partícula para aplicar su desplazamiento correspondiente.
        for (Carga c : cargas) {

            // Delegamos el cálculo del movimiento al propio modelo de carga.
            c.actualizarPosicion(deltaTiempo);
        }
    }
}
