import org.junit.Test;
import java.util.Map;
import java.util.function.Function;
import scala.Function1;
import scala.runtime.AbstractFunction1;

import static org.junit.Assert.assertEquals;

public class TestController {

    @Test
    public void testControllerInvocation() {
        Controller<Map<String, Integer>, Integer> controller = new Controller<>(4, 1024, 10, new RoundRobin());

        Function<Map<String, Integer>, Integer> javaFunction = x -> x.get("x") + x.get("y");
        Function1<Map<String, Integer>, Integer> scalaFunction = new AbstractFunction1<Map<String, Integer>, Integer>() {
            @Override
            public Integer apply(Map<String, Integer> x) {
                return javaFunction.apply(x);
            }
        };
        controller.registerAction("addAction", scalaFunction, 256);

        int result = controller.invokeS("addAction", Map.of("x", 6, "y", 2), 256);

        assertEquals(8, result);
    }

    @Test
    public void testRoundRobinPolicy() {
        int numInvokers = 5;
        int totalMemory = 1000;
        int maxActions = 10;
        int memoria = 400;

        Controller<Map<String, Integer>, Integer> controller = new Controller<>(10, 1000, 15, new RoundRobin());

        Function<Map<String, Integer>, Integer> javaFunction = x -> x.get("x") + x.get("y");
        Function1<Map<String, Integer>, Integer> scalaFunction = new AbstractFunction1<Map<String, Integer>, Integer>() {
            @Override
            public Integer apply(Map<String, Integer> x) {
                return javaFunction.apply(x);
            }
        };
        controller.registerAction("addAction", scalaFunction, 400);

        int numInvocations = 5;
        String previousInvokerID = "0000";

        for (int i = 0; i < numInvocations; i++) {
            controller.invokeS("addAction", Map.of("x", 6, "y", 2), 400);
        }
    }
}