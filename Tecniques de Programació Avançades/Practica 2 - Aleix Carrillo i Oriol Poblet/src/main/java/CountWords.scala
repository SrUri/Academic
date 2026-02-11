import java.nio.file.{Files, Paths}
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import java.util.StringTokenizer

object CountWords {
    def main(args: Array[String]): Unit = {
        val controller = new Controller[String, Map[String, Int]](4, 1024, 10, new RoundRobin)
        val controller2 = new Controller[Map[String, Int], Int](4, 1024, 10, new RoundRobin)

        val textFiles = obtenerTextosDesdeGutenberg()
        val Titulos = obtenerNombresArchivosDesdeGutenberg()

        // Fase de map: invocación grupal
        val mapFunction: Function[String, Map[String, Int]] = CountWords.map
        controller.registerAction("mapAction", mapFunction, 256)
        val mappedResults = controller.invoke("mapAction", textFiles, 256)

        // Fase de reduce: invocación individual
        val reduce: Function[Map[String, Int], Int] = (wordCountMap: Map[String, Int]) =>
            wordCountMap.values.sum
        controller2.registerAction("reduceAction", reduce, 256)

        for (i <- 0 until mappedResults.size) {
            val resultMap = mappedResults(i)
            val result = controller2.invokeS("reduceAction", resultMap, 256)
            println(s"Palabras para ${Titulos(i)}: $result")
        }
    }

    def map(input: String): Map[String, Int] = {
        val wordCounts = mutable.Map[String, Int]()  // Corregido: Declaración correcta de Map
        val tokenizer = new StringTokenizer(input)

        while (tokenizer.hasMoreTokens) {
            val word = tokenizer.nextToken().toLowerCase
            wordCounts.updateWith(word) { currentCount => Some(currentCount.fold(1)(_ + 1)) } // Corregido: Uso correcto de updateWith
        }
        wordCounts.toMap
    }

    def obtenerTextosDesdeGutenberg(): List[String] = {
        val directory = Paths.get(System.getProperty("user.dir"))
        Files.walk(directory)
                .iterator()
                .asScala
                .filter(Files.isRegularFile(_))
                .filter(_.toString.endsWith(".txt"))
                .map(file => new String(Files.readAllBytes(file)))
                .toList
    }

    def obtenerNombresArchivosDesdeGutenberg(): List[String] = {
        val directory = Paths.get(System.getProperty("user.dir"))
        Files.walk(directory)
                .iterator()
                .asScala
                .filter(Files.isRegularFile(_))
                .filter(_.toString.endsWith(".txt"))
                .map(_.getFileName.toString)
                .toList
    }
}