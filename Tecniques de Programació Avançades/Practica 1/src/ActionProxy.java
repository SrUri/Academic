import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class ActionProxy implements InvocationHandler {
    private final Controller<Map<String, Integer>, Integer> controller;
    private final String nombreAction;
    private final int memoriaRequerida;

    public ActionProxy(Controller<Map<String, Integer>, Integer> controller, String nombreAction, int memoriaRequerida) {
        this.controller = controller;
        this.nombreAction = nombreAction;
        this.memoriaRequerida = memoriaRequerida;
    }

    public static Action newInstance(Controller<Map<String, Integer>, Integer> controller, String nombreAction, int memoriaRequerida) {
        return (Action) Proxy.newProxyInstance(
                Action.class.getClassLoader(),
                new Class[]{Action.class},
                new ActionProxy(controller, nombreAction, memoriaRequerida)
        );
    }

    @Override
    public Object invoke(Object proxy, Method metodo, Object[] args) throws Throwable {
    try {
        System.out.println("Antes del metodo" + metodo.getName());
        Object result = controller.invokeS(nombreAction, (Map<String, Integer>) args[0], memoriaRequerida);
        System.out.println("Despues del metodo" + metodo.getName());
        return result;
    } catch (Exception e) {
        System.err.println("Invocation of " + metodo.getName() + " failed");
        throw e;
    }
}
}
