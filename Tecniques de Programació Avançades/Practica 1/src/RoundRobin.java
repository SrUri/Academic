import java.util.List;
import java.util.NoSuchElementException;

public class RoundRobin implements PolicyManager {
    private int j;  

    /**
     * Constructor de la clase RoundRobin
     */

    public RoundRobin() {
        this.j = 0;
    }
    /**
     * Método que devuelve una lista de invokers que cumplen con los requisitos de memoria
     * @param invokers
     * @param requiredMemory
     * @return numInvokers lista de invokers en uso
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
        throw new NoSuchElementException("No hi ha Invokers");
    }
}
