import java.util.Map;
import java.util.function.Function;

public class TimeDecorator extends Decorator {
    /**
     * Constructor de la clase timeDecorator
     * @param function Función a decorar
     */
    public TimeDecorator(Function<Map<String, Integer>, Integer> function) {
        super(function);
    }

    /**
     * Nos muestra el tiempo de ejecución de la acción
     * @param params Parámetros de la función
     * @return tiempo de ejecución
     */
    @Override
    public Integer apply(Map<String, Integer> params) {
        long startTime = System.currentTimeMillis();
        int result = function.apply(params);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + "ms");
        return result;
    }
}
