import scala.collection.convert.ImplicitConversions.`seq AsJavaList`

class GreedyGroup[U, V] extends PolicyManager[U, V] {
  private var j: Int = 0
  /**
   * Método que devuelve un invoker que cumple con los requisitos de memoria
   *
   * @param invokers       Lista de invokers
   * @param requiredMemory Memoria requerida
   * @return Invoker que cumple con los requisitos de memoria
   */
  override def valorsInvoker(invokers: List[Invoker[U, V]], requiredMemory: Int): Invoker[U, V] = {
    for (i <- 0 until invokers.size) {
      val invoker = invokers.get(i)
      if (invoker.estaDisponible(requiredMemory)) {
        j = (j + 1) % invokers.size
        return invoker
      }
    }
    throw new NoSuchElementException("No hay Invokers disponibles")
  }
}