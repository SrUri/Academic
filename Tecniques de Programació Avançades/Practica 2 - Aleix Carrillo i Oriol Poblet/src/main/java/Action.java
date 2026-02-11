import java.util.Map;

public interface Action {
    /**
     * Interfície que implementaran las acciones a ejecutar
     * @param params
     * @return 
     */

    int add(Map<String, Integer> params);
}
