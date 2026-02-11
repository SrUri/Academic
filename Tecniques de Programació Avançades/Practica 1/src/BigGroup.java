import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BigGroup implements PolicyManager {
    private int groupSize;

    /**
     * Politica Big Group: Envia a los invokers según el tamaño de los grupos
     * @param groupSize Tamaño de los grupo
     */

    public BigGroup(int groupSize) {
        this.groupSize = groupSize;
    }

    /**
     * Método que devuelve un invoker que cumple con los requisitos de memoria
     * @param invokers lista de invokers
     * @param requiredMemory memoria necesitada para la ejecución
     * @return invokerDisponible un invoker en uso
     */

    @Override
    public Invoker valorsInvoker(List<Invoker> invokers, int requiredMemory) {
        int remainingMemory = requiredMemory;
        int j = 0;

        while (remainingMemory > 0) {
            int currentGroupSize = Math.min(groupSize, remainingMemory);
            List<Invoker> currentGroup = new ArrayList<>();

            for (int i = 0; i < currentGroupSize; i++) {
                if (j >= invokers.size()) {
                    j = 0;
                }

                Invoker invoker = invokers.get(j++);
                currentGroup.add(invoker);
            }

            for (Invoker invoker : currentGroup) {
                if (invoker.estaDisponible(requiredMemory)) {
                    return invoker;  // Return the first available invoker
                }
            }

            remainingMemory -= currentGroupSize;
        }

        throw new NoSuchElementException("No hay Invokers disponibles");
    }
}
