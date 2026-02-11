import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;

public class CountWords {

    /**
     * Contador de palabras de todos los textos
     * @param args
     */
    public static void main(String[] args) {
        Controller<String, Map<String, Integer>> controller = new Controller<>(4, 1024, 10, new RoundRobin());
        Controller<Map<String, Integer>, Integer> controller2 = new Controller<>(4, 1024, 10, new RoundRobin());

        List<String> texto = obtenerTextosDesdeGutenberg();
        List<String> Titulos = obtenerNombresArchivosDesdeGutenberg();

        // Fase de map: invocación grupal
        Function<String, Map<String, Integer>> funcionMap = CountWords::map;
        controller.registerAction("map", funcionMap, 256);
        List<Map<String, Integer>> resultados = controller.invoke("map", texto, 256);

        // Fase de reduce: invocación individual
        Function<Map<String, Integer>, Integer> funcionReduce = wordCountMap -> 
        wordCountMap.values().stream().mapToInt(Integer::intValue).sum();
        controller2.registerAction("reduce", funcionReduce, 256);

        for (int i = 0; i < resultados.size(); i++) {
            Map<String, Integer> result = resultados.get(i);
            Integer resultat = controller2.invokeS("reduce", result, 256);
            System.out.println("Palabras para " + Titulos.get(i) + ": " + resultat);
        }
    }

    /**
     * Cuenta las palabras de un texto
     * @param input
     * @return palabras contadas
     */

    public static Map<String, Integer> map(String input) {
        Map<String, Integer> wordCounts = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(input);

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }
        return wordCounts;
    }

    /**
     * Obtiene los textos desde el directorio de Gutenberg
     * @throws Exception
     * @return textos
     */

    private static List<String> obtenerTextosDesdeGutenberg() {
        try {
            Path directory = Paths.get(System.getProperty("user.dir"));

            return Files.walk(directory)
                        .filter(Files::isRegularFile)
                        .filter(file -> file.toString().endsWith(".txt"))
                        .map(file -> {
                            try {
                                return new String(Files.readAllBytes(file));
                            } catch (Exception e) {
                                e.printStackTrace();
                                return "";
                            }
                        })
                        .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtiene los nombres de los archivos desde el directorio de Gutenberg
     * @throws Exception
     * @return nombres de los archivos
     */
    private static List<String> obtenerNombresArchivosDesdeGutenberg() {
        try {
            Path directory = Paths.get(System.getProperty("user.dir"));
    
            return Files.walk(directory)
                        .filter(Files::isRegularFile)
                        .filter(file -> file.toString().endsWith(".txt"))
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .toList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
