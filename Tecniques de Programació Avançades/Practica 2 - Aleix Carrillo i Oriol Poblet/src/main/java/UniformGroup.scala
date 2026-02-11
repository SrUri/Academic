class UniformGroup[U, V](var groupSize: Int) extends PolicyManager[U, V] {
  /**
   * Método que devuelve un invoker que cumple con los requisitos de memoria
   *
   * @param invokers       Lista de invokers
   * @param requiredMemory Memoria necesaria para la ejecución
   * @return Invoker que cumple con los requisitos de memoria
   */
  override def valorsInvoker(invokers: List[Invoker[U, V]], requiredMemory: Int): Invoker[U, V] = {
    val totalInvokers = invokers.size
    var remainingMemory = requiredMemory

    for (i <- 0 until totalInvokers if remainingMemory > 0) {
      val invoker = invokers(i)
      val assignedMemory = Math.min(groupSize, remainingMemory)

      if (assignedMemory > 0) {
        remainingMemory -= assignedMemory
        invoker.liberarMemoria(assignedMemory)
        return invoker
      }
    }

    throw new NoSuchElementException("No hay Invokers disponibles")
  }
}