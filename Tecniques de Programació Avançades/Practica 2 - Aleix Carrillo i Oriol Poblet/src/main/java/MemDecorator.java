import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MemDecorator extends Decorator {
    private final Map<Map<String, Integer>, Integer> cache = new HashMap<>();

    /**
     * Constructor de la clase MemDecorator
     * @param function Función a decorar
     */
    public MemDecorator(Function<Map<String, Integer>, Integer> function) {
        super(function);
    }

    /**
     * Método que guarda en la caché los resultados de las acciones
     * @param params Parámetros de la funcion
     * @return Memoria de la funcion
     */
    @Override
    public Integer apply(Map<String, Integer> params) {
        if (cache.containsKey(params)) {
            System.out.println("Result from cache");
            return cache.get(params);
        }

        int result = function.apply(params);
        cache.put(new HashMap<>(params), result);
        return result;
    }
}
