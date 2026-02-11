import java.nio.file.{Files, Paths}
import java.util.StringTokenizer
import scala.collection.mutable.HashMap
import scala.jdk.CollectionConverters._

object WordCount {
    def main(args: Array[String]): Unit = {
        val controller = new Controller[Map[String, Int], Int](4, 1024, 10, new RoundRobin)

        val textFiles = obtenerTextosDesdeGutenberg()
        val input = textFiles.map(CountWords.map)

        val mapAction: Function[Map[String, Int], Int] = (wordCountMap: Map[String, Int]) => {
            wordCountMap.foreach { case (word, count) =>
                println(s"Veces repetida $word: $count")
            }
            wordCountMap.values.sum
        }

        controller.registerAction("mapAction", mapAction, 256)

        val result = controller.invoke("mapAction", input, 256)

        val Titulos = obtenerNombresArchivosDesdeGutenberg()

        (Titulos, result).zipped.foreach { (title, count) =>
            println(s"Palabras para $title: $count")
        }
    }

    def map(input: String): Map[String, Int] = {
        val wordCounts = new HashMap[String, Int]()
        val tokenizer = new StringTokenizer(input)

        while (tokenizer.hasMoreTokens) {
            val word = tokenizer.nextToken().toLowerCase
            wordCounts.update(word, wordCounts.getOrElse(word, 0) + 1)
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