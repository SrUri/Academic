public class Metrics {
    private String name;
    private String invokerID;
    private long timepoEjec;
    private int memoryUsed;

    /**
     * Constructor de la clase Metrics
     * @param name
     * @param invokerID
     * @param executionTime
     * @param memoryUsed
     */

    public Metrics(String name, String invokerID, long executionTime, int memoryUsed) {
        this.name = name;
        this.invokerID = invokerID;
        this.timepoEjec = executionTime;
        this.memoryUsed = memoryUsed;
    }

    /**
     * Obtener el nombre
     * @return
     */

    public String getName() {
        return name;
    }

    /**
     * Obtener el ID del invocador
     * @return
     */

    public String getInvokerID() {
        return invokerID;
    }

    /**
     * Obtener el tiempo de ejecución
     * @return
     */

    public long getExecutionTime() {
        return timepoEjec;
    }

    /**
     * Obtener la memoria usada
     * @return
     */
    
    public int getMemoryUsed() {
        return memoryUsed;
    }
}