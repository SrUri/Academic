import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TestBigGroup {

    public static void main(String[] args) {




        
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(10, 1000, 15, new BigGroup(3));


        Function<Map<String, Integer>, Integer> f = x -> x.get("x") + x.get("y");
        controller.registerAction("addAction", f, 550);

        int numInvocations = 5;
        for (int i = 0; i < numInvocations; i++) {
            int res = controller.invokeS("addAction", Map.of("x", 6, "y", 2), 256);
            System.out.println(res);
        }

        List<Metrics> metricsList = controller.getMetricsList();
        for (Metrics metrics : metricsList) {
            System.out.println("Accion: " + metrics.getName());
            System.out.println("ID: " + metrics.getInvokerID());
            System.out.println("Tiempo de ejecucion: " + (metrics.getExecutionTime()-100) + " ms");
            System.out.println("Memoria Usada: " + metrics.getMemoryUsed() + " MB");
            System.out.println("------------------------");
        }

        long min = controller.getTiempoMinimo("addAction")-100;
        long max = controller.getTiempoMaximo("addAction")-100;
        double prome = controller.getTiempoMedio("addAction")-100;
        long total = controller.getTiempoTotal("addAction")-(100*numInvocations);

        System.out.println("Tiempo de ejecucion minima: " + min + " ms");
        System.out.println("Tiempo de ejecucion maxima: " + max + " ms");
        System.out.println("Tiempo de ejecucion promedio: " + prome + " ms");
        System.out.println("Tiempo de ejecucion total: " + total + " ms");
    }
}