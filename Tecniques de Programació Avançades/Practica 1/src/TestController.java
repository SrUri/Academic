import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestController {

    @Test
    public void testControllerInvocation() {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(4, 1024, 10, new RoundRobin());

        Function<Map<String, Integer>, Integer> f = x -> x.get("x") + x.get("y");
        controller.registerAction("addAction", f, 256);

        int result = controller.invokeS("addAction", Map.of("x", 6, "y", 2), 256);

        assertEquals(8, result);
    }
    @Test
    public void testControllerGroupInvocation() {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(4, 1024, 10, new RoundRobin());

        Function<Map<String, Integer>, Integer> f = x -> x.get("x") + x.get("y");
        controller.registerAction("addAction", f, 256);

        List<Map<String, Integer>> input = Arrays.asList(
                Map.of("x", 2, "y", 3),
                Map.of("x", 9, "y", 1),
                Map.of("x", 8, "y", 8)
        );

        List<Integer> result = controller.invoke("addAction", input, 256);

        assertEquals(Arrays.asList(5, 10, 16), result);
    }
    @Test
    public void testAsyncControllerInvocation() throws Exception {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(4, 1024, 10, null);

        Function<Map<String, Integer>, Integer> sleepAction = params -> {
            try {
                TimeUnit.SECONDS.sleep(params.get("duration"));
                return 42;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        controller.registerAction("sleepAction", sleepAction, 256);
        Map<String, Integer> params = Map.of("duration", 5);
        Instant start = Instant.now();

        FuturResultat fut1 = controller.invoke_async("sleepAction", params, 256);
        FuturResultat fut2 = controller.invoke_async("sleepAction", params, 256);
        FuturResultat fut3 = controller.invoke_async("sleepAction", params, 256);

        fut1.get();
        fut2.get();
        fut3.get();

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);

        System.out.println(duration.getSeconds());

        assertEquals(5, duration.getSeconds()); 
    }
    
    @Test
    public void testObserver() {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(4, 1024, 10, new RoundRobin());
        Function<Map<String, Integer>, Integer> f = x -> x.get("x") + x.get("y");
        controller.registerAction("addAction", f, 256);

        int result = controller.invokeS("addAction", Map.of("x", 6234, "y", 2234), 256);

        List<Metrics> metricsList = controller.getMetricsList();
        assertEquals(1, metricsList.size());

        Metrics metrics = metricsList.get(0);
        assertEquals("addAction", metrics.getName()); 
        assertEquals(6234 + 2234, result); 

        System.out.println("Accion: " + metrics.getName());
        System.out.println("ID: " + metrics.getInvokerID());
        System.out.println("Tiempo de ejecucion: " + metrics.getExecutionTime() + " ms");
        System.out.println("Memoria Usada: " + metrics.getMemoryUsed() + " MB");
        System.out.println("------------------------");
    }

    @Test
    public void testTimeDecorator() {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(4, 1024, 10, new RoundRobin());
        Function<Map<String, Integer>, Integer> funcion = params -> {
            int n = params.get("n");
            int result = 1;
            for (int i = 2; i <= n; i++) {
                result *= i;
            }
            return result;
        };
        Function<Map<String, Integer>, Integer> timeFunction = new TimeDecorator(funcion);
        controller.registerAction("timeAction", timeFunction, 256);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int result = controller.invokeS("timeAction", Map.of("n", 5), 256);

        assertFalse(outContent.toString().isEmpty());
        assertEquals(120, result);
    }

    @Test
    public void testMemDecorator() {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(4, 1024, 10, new RoundRobin());
        Function<Map<String, Integer>, Integer> funcion = params -> {
            int n = params.get("n");
            int result = 1;
            for (int i = 2; i <= n; i++) {
                result *= i;
            }
            return result;
        };
        Function<Map<String, Integer>, Integer> memFunction = new MemDecorator(funcion);
        controller.registerAction("memAction", memFunction, 256);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int result1 = controller.invokeS("memAction", Map.of("n", 5), 256);
        int result2 = controller.invokeS("memAction", Map.of("n", 5), 256);

        assertTrue(outContent.toString().contains("Result from cache"));

        assertEquals(120, result1);
        assertEquals(120, result2);
    }

    @Test
    public void testTimeDecoratorAndMemDecorator() {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(4, 1024, 10, new RoundRobin());
        Function<Map<String, Integer>, Integer> funcion = params -> {
            int n = params.get("n");
            int result = 1;
            for (int i = 2; i <= n; i++) {
                result *= i;
            }
            return result;
        };
        Function<Map<String, Integer>, Integer> timeAndMemFunction = new TimeDecorator(new MemDecorator(funcion));
        controller.registerAction("timeAndMemAction", timeAndMemFunction, 256);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int result1 = controller.invokeS("timeAndMemAction", Map.of("n", 5), 256);
        int result2 = controller.invokeS("timeAndMemAction", Map.of("n", 5), 256);

        assertTrue(outContent.toString().contains("ms"));
        assertTrue(outContent.toString().contains("Result from cache"));

        assertEquals(120, result1);
        assertEquals(120, result2);
    }

    @Test
    public void testActionProxy() {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(3, 100, 5, new RoundRobin());
        Function<Map<String, Integer>, Integer> addAction = x -> x.get("x") + x.get("y");
        controller.registerAction("addAction", addAction, 256);
        
        // Crear un proxy para Action
        Action calcular = ActionFactory.crearActionProxy(controller, "addAction", 10);

        // Llamar al método add de manera orientada a objetos
        int result = calcular.add(Map.of("x", 5, "y", 10));

        assertEquals(15, result);
    }
    @Test
    public void testRoundRobinPolicy() {
        int totalMemory = 1000; 
        int memoria = 400;

        
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(10, 1000, 15, new RoundRobin());


        Function<Map<String, Integer>, Integer> f = x -> x.get("x") + x.get("y");
        controller.registerAction("addAction", f, 400);

        int numInvocations = 5;
        String previousInvokerID = "0000"; 

        for (int i = 0; i < numInvocations; i++) {
            controller.invokeS("addAction", Map.of("x", 6, "y", 2), 400);

            List<Metrics> metricsList = controller.getMetricsList();

            for (Metrics metrics : metricsList) {
                String currentInvokerID = metrics.getInvokerID();

                if (totalMemory - memoria < memoria)
                {
                    assertNotEquals(previousInvokerID, currentInvokerID);
                    previousInvokerID = currentInvokerID;
    
                }
                assertTrue(metrics.getExecutionTime() >= 0);
                assertTrue(metrics.getMemoryUsed() >= 0);
        }
    }
}
}