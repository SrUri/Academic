import java.util.List;

public interface PolicyManager {
    /**
     * Interfície de PolicyManager
     * @param list
     * @param requiredMemory
     */
    
     public Invoker valorsInvoker(List<Invoker> list, int requiredMemory);
}