import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class Invoker<U,V> {
    private int memoriaTotal;
    private int memoriaUsada;
    private List<Observer> observers = new ArrayList<Observer>();
    private final Semaphore memorySemaphore = new Semaphore(1);
    private final ExecutorService executorService;

    /**
     * Constructor Invoker
     * @param totalMemory
     */
    
    public Invoker(int totalMemory, ExecutorService executorService) {
        this.memoriaTotal = totalMemory;
        this.memoriaUsada = 0;
        this.observers = new ArrayList<>();
        this.executorService = executorService;

    }

    /**
     * Ejecutar acción síncrona
     * @param action
     * @param params
     * @param requiredMemory
     * @throws InterruptedException
     * @return memoriaUsada memoria usada en el momento de la ejecución
     */

    public V executeAction(Function<U,V> action, U params, int requiredMemory) {
        if (requiredMemory <= 0) {
            throw new IllegalArgumentException("La cantidad de memoria requerida debe ser positiva");
        }
        this.memoriaUsada += requiredMemory;

        long startTime = System.currentTimeMillis();
        try {
            Thread.sleep(100);
           } catch (InterruptedException e) {
             e.printStackTrace();
           }
        V resultat = action.apply(params);
        long endtime = System.currentTimeMillis();

        long executionTime = endtime - startTime;

        Metrics metrics = new Metrics("addAction", this.getInvokerId(), executionTime, memoriaUsada);
        notifyObservers(metrics);

        return resultat;
    }

    /**
     * Ejecutar acción asíncrona
     * @param requiredMemory
     * @throws InterruptedException
     */

    public FuturResultat executeActionAsync(Integer requiredMemory,Map<String, Integer> params, Function<Map<String, Integer>, Integer> action) throws InterruptedException {
        if (requiredMemory <= 0) {
            throw new IllegalArgumentException("La cantidad de memoria requerida debe ser positiva");
        }

        FuturResultat futur = new FuturResultat();

        executorService.submit(() -> {
            try{
                memorySemaphore.acquire();
                this.memoriaUsada += requiredMemory;
                memorySemaphore.release();

                long startTime = System.currentTimeMillis();
                try {
                    Thread.sleep(100);
                   } catch (InterruptedException e) {
                     e.printStackTrace();
                   }
                int resultat = action.apply(params);
                long executionTime = System.currentTimeMillis() - startTime;

                Metrics metrics = new Metrics("addActionAsync", this.getInvokerId(), executionTime, memoriaUsada);
                notifyObservers(metrics);

                futur.setResultat(resultat);
            }catch(Exception e){
                futur.setException(e);
            }
        });
        return futur;
    }

    /**
     * Añadir observador al invoker
     * @param observer
     */

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Borrar observador al invoker
     * @param observer
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Avisar observador y actualizar metricas
     * @param observer
     */
    public void notifyObservers(Metrics metrics) {
        for (Observer observer : observers) {
            observer.update(metrics);
        }
    }

    /**
     * Obtener id del invoker
     * @return ID del invoker
     */

    public String getInvokerId() {
        return "Invoker" + this.hashCode();
    }

    /**
     * Libera memoria del invoker
     * @param memoryToRelease
     */

    public void liberarMemoria(int memorialiberar) {
        this.memoriaUsada -= memorialiberar;
        if (this.memoriaUsada < 0) {
            this.memoriaUsada = 0;
        }
    }

    /**
     * Obtener memoria en uso
     * @return memoria usada
     */

    public int getUsedMemory() {
        return this.memoriaUsada;
    }

    /**
     * Obtener si cabe la memoria que queremos introducir al invoker con un booleano
     * @param memoriaRequerida
     * @return
     */

    public boolean estaDisponible(int memoriaRequerida) {
        return this.memoriaUsada + memoriaRequerida <= this.memoriaTotal;
    }

    /**
     * Obtenir memoria actual disponible
     * @return memoria disponible
     */

    public int getAvailableMemory() {
        return memoriaTotal - memoriaUsada;
    }


}
