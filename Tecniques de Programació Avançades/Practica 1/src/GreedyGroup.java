import java.util.List;
import java.util.NoSuchElementException;

public class GreedyGroup implements PolicyManager {
    private int j;

    /**
     * Constructor de la clase GreedyGroup
     */
    public GreedyGroup() {
        this.j = 0;
    }

    /**
     * Método que devuelve un invoker que cumple con los requisitos de memoria
     * @param invokers Lista de invokers
     * @param requiredMemory Memoria requerida
     * @return Invoker que cumple con los requisitos de memoria
     */
    
    @Override
    public Invoker valorsInvoker(List<Invoker> invokers, int requiredMemory) {
        for (int i = 0; i < invokers.size(); i++) {
            Invoker invoker = invokers.get(i);
            if (invoker.estaDisponible(requiredMemory)) {
                j = (j + 1) % invokers.size();
                return invoker;
            }
        }
        throw new NoSuchElementException("No hay Invokers disponibles");
    }
}
