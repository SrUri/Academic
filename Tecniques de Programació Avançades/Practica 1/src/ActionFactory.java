import java.lang.reflect.Proxy;
import java.util.Map;

public class ActionFactory {
    
    /**
     * Crea un proxy para la acción indicada, que delega en el controlador
     * @param controller
     * @param actionName
     * @param memoriaRequerida
     * @return
     */

    public static Action crearActionProxy(Controller<Map<String, Integer>, Integer> controller, String actionName, int memoriaRequerida) {
        return (Action) Proxy.newProxyInstance(
                Action.class.getClassLoader(),
                new Class[]{Action.class},
                new ActionProxy(controller, actionName, memoriaRequerida)
        );
    }
}
