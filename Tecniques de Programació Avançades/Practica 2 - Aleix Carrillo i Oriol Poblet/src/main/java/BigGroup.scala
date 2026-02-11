import scala.collection.JavaConverters._
import scala.collection.convert.ImplicitConversions.`seq AsJavaList`

class BigGroup[U, V](groupSize: Int) extends PolicyManager[U, V] {
  /**
   * Método que devuelve un invoker que cumple con los requisitos de memoria
   *
   * @param invokers       Lista de invokers
   * @param requiredMemory Memoria requerida para la ejecución
   * @return Invoker que cumple con los requisitos de memoria
   */
  override def valorsInvoker(invokers: List[Invoker[U, V]], requiredMemory: Int): Invoker[U, V] = {
    var remainingMemory = requiredMemory
    var j = 0

    while (remainingMemory > 0) {
      val currentGroupSize = Math.min(groupSize, remainingMemory)
      val currentGroup = new java.util.ArrayList[Invoker[U, V]]

      for (i <- 0 until currentGroupSize) {
        if (j >= invokers.size) {
          j = 0
        }

        val invoker = invokers.get(j)
        currentGroup.add(invoker)
        j += 1
      }

      for (invoker <- currentGroup.asScala) {
        if (invoker.estaDisponible(requiredMemory)) {
          return invoker // Devuelve el primer invoker disponible
        }
      }
      remainingMemory -= currentGroupSize
    }
    throw new NoSuchElementException("No hay Invokers disponibles")
  }
}