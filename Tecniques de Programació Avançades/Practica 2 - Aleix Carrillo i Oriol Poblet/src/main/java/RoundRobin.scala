import scala.util.control.Breaks._

class RoundRobin[U,V] extends PolicyManager[U,V] {
  private var j = 0

  override def valorsInvoker(invokers: List[Invoker[U, V]], requiredMemory: Int): Invoker[U, V] = {
    breakable {
      for (i <- invokers.indices) {
        val invoker = invokers(i)
        if (invoker.estaDisponible(requiredMemory)) {
          j = (j + 1) % invokers.size
          return invoker
        }
      }
    }
    throw new NoSuchElementException("No hi ha Invokers")
  }
}