import java.util.Map;
import java.util.function.Function;

public abstract class Decorator implements Function<Map<String, Integer>, Integer> {
    protected Function<Map<String, Integer>, Integer> function;

    /**
     * Constructor de la clase Decorator
     * @param function Función a decorar
     */
    public Decorator(Function<Map<String, Integer>, Integer> function) {
        this.function = function;
    }

    /**
     * Aplica la función decorada segun se llame a MemoryDecorator o TimeDecorator
     * @param params Parámetros de la función
     */
    public abstract Integer apply(Map<String, Integer> params);
}