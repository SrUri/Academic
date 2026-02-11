import java.util.concurrent.{Callable, ExecutorService, Semaphore}

class Invoker[U, V](var memoriaTotal: Int, executorService: ExecutorService) {
  private var memoriaUsada: Int = 0
  private val memorySemaphore: Semaphore = new Semaphore(1)

  def executeAction(action: U => V, params: U, requiredMemory: Int): V = {
    if (requiredMemory <= 0) {
      throw new IllegalArgumentException("La cantidad de memoria requerida debe ser positiva")
    }
    this.memoriaUsada += requiredMemory
    val startTime = System.currentTimeMillis()
    val result = action(params)
    val executionTime = System.currentTimeMillis() - startTime
    result
  }

  def executeActionAsync(requiredMemory: Int, params: Map[String, Int], action: Function[Map[String, Int], Int]): FuturResultat = {
    if (requiredMemory <= 0) {
      throw new IllegalArgumentException("La cantidad de memoria requerida debe ser positiva")
    }
    val futur = new FuturResultat()
    executorService.submit(new Callable[Unit] {
      override def call(): Unit = {
        try {
          memorySemaphore.acquire()
          memoriaUsada += requiredMemory
          memorySemaphore.release()
          val startTime = System.currentTimeMillis()
          val resultat = action.apply(params)
          val executionTime = System.currentTimeMillis() - startTime
          futur.setResultat(resultat)
        } catch {
          case e: Exception => futur.setException(e)
        }
      }
    })
    futur
  }

  def getInvokerId: String = "Invoker" + this.hashCode()

  def liberarMemoria(memorialiberar: Int): Unit = {
    this.memoriaUsada -= memorialiberar
    if (this.memoriaUsada < 0) {
      this.memoriaUsada = 0
    }
  }

  def estaDisponible(memoriaRequerida: Int): Boolean = this.memoriaUsada + memoriaRequerida <= this.memoriaTotal

  def getAvailableMemory: Int = memoriaTotal - memoriaUsada
}
