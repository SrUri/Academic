import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TestUniformGroup {

    public static void main(String[] args) {
        
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(10, 1000, 15, new UniformGroup(2));

      
        Function<Map<String, Integer>, Integer> f = x -> x.get("x") + x.get("y");
        controller.registerAction("addAction", f, 400);

        List<Map<String, Integer>> input = Arrays.asList(
                Map.of("x", 2, "y", 3),
                Map.of("x", 9, "y", 1),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8),
                Map.of("x", 8, "y", 8)
        );

        List<Integer> result = controller.invoke("addAction", input, 400);
        
        List<Metrics> metricsList = controller.getMetricsList();
        for (Metrics metrics : metricsList) {
            System.out.println("Accion: " + metrics.getName());
            System.out.println("ID: " + metrics.getInvokerID());
            System.out.println("Tiempo de ejecucion: " + metrics.getExecutionTime() + " ms");
            System.out.println("Memoria Usada: " + metrics.getMemoryUsed() + " MB");
            System.out.println("------------------------");
        }

        long min = controller.getTiempoMinimo("addAction");
        long max = controller.getTiempoMaximo("addAction");
        double prome = controller.getTiempoMedio("addAction");
        long total = controller.getTiempoTotal("addAction");

        System.out.println("Tiempo de ejecucion minima: " + min + " ms");
        System.out.println("Tiempo de ejecucion maxima: " + max + " ms");
        System.out.println("Tiempo de ejecucion promedio: " + prome + " ms");
        System.out.println("Tiempo de ejecucion total: " + total + " ms");
        System.out.println(result);

    }
}