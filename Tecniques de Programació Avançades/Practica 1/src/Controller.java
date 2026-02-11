import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class Controller<U, V> implements Observer {
    private int maxActions;
    private int maxMemory;
    private Map<String, Function<U, V>> actions;
    private List<Invoker> invokers;
    private PolicyManager policyManager;
    private List<Metrics> metrics;
    private ExecutorService executorService;
    
    /**
     * Constructor de la clase Controller
     * @param numInvokers
     * @param totalMemory
     * @param policyManager
     * @param maxActions
     */

    public Controller(int numInvokers, int totalMemory, int maxActions, PolicyManager policyManager) {
        this.invokers = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(numInvokers);
        for (int i = 0; i < numInvokers; i++) {
            Invoker<U,V> invoker = new Invoker(totalMemory, executorService);
            invoker.addObserver(this);
            this.invokers.add(invoker);
        }
        this.policyManager = policyManager;
        this.metrics = new ArrayList<>();
        this.actions = new HashMap<>();
        this.maxActions = maxActions;
    }

    /**
     * Registra una acción en el controlador
     * @param name
     * @param action
     * @param memoriaRequerida
     */

    public void registerAction(String name, Function<U,V> action, int memoriaRequerida){
        if (actions.size() == maxActions) {
            throw new IllegalStateException("Max actions reached");
        }
        if (memoriaRequerida <= 0) {
            throw new IllegalArgumentException("La cantidad de memoria requerida debe ser positiva");
        }
        actions.put(name,action);
    }

    /**
     * Invocador individual
     * @param name
     * @param params
     * @param memoriaRequerida
     * @return
     */
    
    public Integer invokeS(String name, U params, int memoriaRequerida) {
        if (!actions.containsKey(name)) {
            throw new IllegalArgumentException("Action not found");
        }
        @SuppressWarnings("unchecked")
        Function<Map<String, Integer>, Integer> action = (Function<Map<String, Integer>, Integer>) actions.get(name);
        int resultat=0;
        Invoker invoker = policyManager.valorsInvoker(this.obtenerInvocadores(), memoriaRequerida);
        if(invoker != null) {
            resultat = (int) invoker.executeAction(action, (Map<String, Integer>) params,memoriaRequerida);
        } else System.out.println("No hi ha invokers");
        return resultat;
    }

    /**
     * Invocador múltiple
     * @param name
     * @param params
     * @param memoriaRequerida
     * @return 
     */
    public List<V> invoke(String name, List<U> params, int memoriaRequerida) {
        if (!actions.containsKey(name)) {
            throw new IllegalArgumentException("Action not found");
        }
        @SuppressWarnings("unchecked")
        Function<Map<String, Integer>, Integer> action = (Function<Map<String, Integer>, Integer>) actions.get(name);
        List<V> resultats = new ArrayList<>();

        for (U parametre : params) {
            Invoker invoker = policyManager.valorsInvoker(this.obtenerInvocadores(), memoriaRequerida);
            try {
                Thread.sleep(100);
               } catch (InterruptedException e) {
                 e.printStackTrace();
               }
            
            if(invoker != null) {
                V resultat = (V) invoker.executeAction(action, parametre,memoriaRequerida);

                resultats.add(resultat);
            } else {
                System.out.println("No hi ha invokers");
                break;
            }
        }
        
        return (List<V>) resultats;
    }

    /**
     * Invocador asíncrono
     * @param name
     * @param params
     * @param memoriaRequerida
     * @return futur futuro resultado
     */

    public FuturResultat invoke_async(String name, Map<String, Integer> params, int memoriaRequerida){
        if (!actions.containsKey(name)) {
            throw new IllegalArgumentException("Action not found");
        }
        FuturResultat futur = new FuturResultat();

        int resultat;
        
        @SuppressWarnings("unchecked")
        Function<Map<String, Integer>, Integer> action = (Function<Map<String, Integer>, Integer>) actions.get(name);
        try {
            Invoker invoker = invokerDisponible(memoriaRequerida);
                if(invoker != null){

                    futur = invoker.executeActionAsync(memoriaRequerida, params, action);
                }
                else {
                    System.out.println("No hay invocadores disponibles");
                }
        } catch (Exception e) {
            futur.setException(e);
        }
        return futur;
    }

    /**
     * Mira si hay un invoker disponible
     * @param memoriaRequerida
     * @return invoker
     */

    private Invoker invokerDisponible(int memoriaRequerida) {
        for (Invoker invoker : invokers) {
            if (invoker.estaDisponible(memoriaRequerida)) {
                return invoker;
            }
        }
        return null;
    }


    /**
     * Obtiene el número máximo de acciones
     * @return número máximo de acciones
     */

    public int getMaxActions() {
        return maxActions;
    }

    /**
     * Obtiene la memoria máxima
     * @return memoria maxima
     */

    public int getMaxMemory() {
        return maxMemory;
    }

    /**
     * Obtiene las acciones
     * @return acciones
     */

    public Map<String, Function<U, V>> getActions() {
        return actions;
    }


    /**
     * Actualiza las métricas
     * @param metrics
     */
    @Override
    public void update(Metrics metrics) {
        this.metrics.add(metrics);
    }

    //JAVA COLLECTIONS Y STREAMS METRICS
    /**
     * Obtiene el tiempo mínimo
     * @param actionId
     * @return tiempo mínimo
     */

    public long getTiempoMinimo(String actionId) {
    return metrics.stream()
                .filter(metrics -> metrics.getName().equals(actionId))
                .mapToLong(Metrics::getExecutionTime)
                .min()
                .orElse(0L);
    }

    /**
     * Obtiene el tiempo máximo
     * @param actionId
     * @return tiempo máximo
     */

    public long getTiempoMaximo(String actionId) {
        return metrics.stream()
                .filter(metrics -> metrics.getName().equals(actionId))
                .mapToLong(Metrics::getExecutionTime)
                .max()
                .orElse(0L);
    }

    /**
     * Obtiene el tiempo medio
     * @param actionId
     * @return tiempo medio
     */
    public double getTiempoMedio(String actionId) {
        return metrics.stream()
                .filter(metrics -> metrics.getName().equals(actionId))
                .mapToLong(Metrics::getExecutionTime)
                .average()
                .orElse(0.0);
    }

    /**
     * Obtiene el tiempo total
     * @param actionId
     * @return tiempo total
     */
    public long getTiempoTotal(String actionId) {
        return metrics.stream()
                .filter(metrics -> metrics.getName().equals(actionId))
                .mapToLong(Metrics::getExecutionTime)
                .sum();
    }

    /**
     * Obtiene la memoria usada media
     * @param invokerId
     * @return memoria usada media
     */
    public double getMemoriaUsadaMedia(String invokerId) {
        return metrics.stream()
                .filter(metrics -> metrics.getInvokerID().equals(invokerId))
                .mapToInt(Metrics::getMemoryUsed)
                .average()
                .orElse(0.0);
    }
    
    
    public List<Invoker> obtenerInvocadores(){
        return invokers;
    }

    public List<Metrics> getMetricsList(){
        return metrics;
    }

    public void actualizarInvocadores(List<Invoker> invocadores){
        this.invokers = invocadores;
    }
}
