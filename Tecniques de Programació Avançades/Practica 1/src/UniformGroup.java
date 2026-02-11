import java.util.List;
import java.util.NoSuchElementException;

public class UniformGroup implements PolicyManager {

    private int groupSize;

    /**
     * Politica Uniform Group: envia de forma uniforme a los invokers los datos a ejecutar
     * @param groupSize Tamaño del grupo
     */

    public UniformGroup(int groupSize) {
        this.groupSize = groupSize;
    }

    /**
     * Método que devuelve un invoker que cumple con los requisitos de memoria
     * @param invokers Lista de invokers
     * @param requiredMemory Memoria necesitada para la ejecución
     * @return Invoker que cumple con los requisitos de memoria
     */

    @Override
    public Invoker valorsInvoker(List<Invoker> invokers, int requiredMemory) {
        int totalInvokers = invokers.size();
        int functionsPerInvoker = requiredMemory / groupSize;

        for (int i = 0; i < totalInvokers && requiredMemory > 0; i++) {
            Invoker invoker = invokers.get(i);
            int assignedMemory = Math.min(functionsPerInvoker, invoker.getAvailableMemory());

            if (assignedMemory > 0) {
                requiredMemory -= assignedMemory;
                invoker.liberarMemoria(assignedMemory);
                return invoker;
            }
        }

        throw new NoSuchElementException("No hay Invokers disponibles");
    }

    /**
     * Devuelve el tamaño del grupo
     * @return groupSize
     */

    public int getGroupSize() {
        return groupSize;
    }
}
