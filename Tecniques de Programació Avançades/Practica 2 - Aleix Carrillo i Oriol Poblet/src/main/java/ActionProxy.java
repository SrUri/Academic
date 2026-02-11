import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class ActionProxy implements InvocationHandler {
    private final Controller<Map<String, Integer>, Integer> controller;
    private final String actionName;
    private final int memoriaRequerida;

    public ActionProxy(Controller<Map<String, Integer>, Integer> controller, String actionName, int memoriaRequerida) {
        this.controller = controller;
        this.actionName = actionName;
        this.memoriaRequerida = memoriaRequerida;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("sumar")) {
            return controller.invokeS(actionName, (Map<String, Integer>) args[0], memoriaRequerida);
        } else {
            throw new UnsupportedOperationException("Método no admitido: " + method.getName());
        }
    }
}
