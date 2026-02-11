import java.util.concurrent.Executors
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.language.postfixOps
import scala.util.control.Breaks.break

class Controller[U, V](numInvokers: Int, totalMemory: Int, maxActions: Int, policyManager: PolicyManager[U,V]) {
  private var invokers: List[Invoker[U, V]] = List()
  private val executorService = Executors.newFixedThreadPool(numInvokers)
  for (_ <- 1 to numInvokers) {
    val invoker = new Invoker[U,V](totalMemory, executorService)
    invokers = invokers :+ invoker
  }
  private val actions: mutable.Map[String, Function[U, V]] = mutable.Map()
  private val maxActionsCount: Int = maxActions

  /**
   * Registra una acciÃ³n en el controlador
   *
   * @param name
   * @param action
   * @param memoriaRequerida
   */
  def registerAction(name: String, action: U => V, memoriaRequerida: Int): Unit = {
    if (actions.size == maxActionsCount) {
      throw new IllegalStateException("Max actions reached")
    }
    if (memoriaRequerida <= 0) {
      throw new IllegalArgumentException("La cantidad de memoria requerida debe ser positiva")
    }
    actions += (name -> action)
  }

  def invoke(name: String, params: List[U], memoriaRequerida: Int): ListBuffer[V] = {
    if (!actions.contains(name)) {
      throw new IllegalArgumentException("Action not found")
    }
    val action = actions(name)
    val resultats: ListBuffer[V] = new ListBuffer[V]

    for (parametre <- params) {
      val invoker = policyManager.valorsInvoker(obtenerInvocadores(), memoriaRequerida)
      try {
        Thread.sleep(100)
      } catch {
        case e: InterruptedException =>
          e.printStackTrace()
      }

      if (invoker != null) {
        val resultat = invoker.executeAction(action, parametre, memoriaRequerida)
        resultats += resultat
      } else {
        println("No hi ha invokers")
        break
      }
    }
    resultats
  }

  /**
   * Invocador individual
   *
   * @param name
   * @param params
   * @param memoriaRequerida
   * @return
   */
  def invokeS(name: String, params: U, memoriaRequerida: Int): Int = {
    if (!actions.contains(name)) {
      throw new IllegalArgumentException("Action not found")
    }
    val action: Function[U, V] = actions(name)
    var resultat: Int = 0
    val invoker: Invoker[U, V] = policyManager.valorsInvoker(obtenerInvocadores(), memoriaRequerida)
    if (invoker != null) {
      resultat = invoker.executeAction(action, params, memoriaRequerida).asInstanceOf[Int]
    } else println("No hi ha invokers")
    resultat
  }

  private def obtenerInvocadores(): List[Invoker[U, V]] = invokers
}