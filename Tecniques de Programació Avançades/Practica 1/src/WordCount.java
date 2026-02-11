import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Function;

public class WordCount {

    public static void main(String[] args) {
        Controller<String, Map<String, Integer>> mapController = new Controller<>(4, 1024, 10, new RoundRobin());
        Controller<Map<String, Integer>, Integer> reduceController = new Controller<>(4, 1024, 10, new RoundRobin());

        List<String> textFiles = obtenerTextosDesdeGutenberg();

        // Fase de map: invocación grupal
        Function<String, Map<String, Integer>> funcionMap = WordCount::map;
        mapController.registerAction("map", funcionMap, 256);
        List<Map<String, Integer>> resultados = mapController.invoke("map", textFiles, 256);

        // Fase de reduce: invocación individual
        Function<Map<String, Integer>, Integer> funcionReduce = wordCountMap -> {
            Map<String, Integer> contar = new HashMap<>();

            wordCountMap.forEach((word, count) -> {
                String palabralimpia = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

                if (contar.containsKey(palabralimpia)) {
                    contar.put(palabralimpia, contar.get(palabralimpia) + count);
                } else {
                    contar.put(palabralimpia, count);
                }
            });

            contar.forEach((word, count) -> {
                System.out.println("Veces repetida " + word + ": " + count);
            });

            return wordCountMap.values().stream().mapToInt(Integer::intValue).sum();
        };

        reduceController.registerAction("reduce", funcionReduce, 256);

        // Invocar la fase de reduce con los resultados de la fase de map
        for (int i = 0; i < resultados.size(); i++) {
            Integer result = reduceController.invokeS("reduce", resultados.get(i), 256);
            System.out.println("Resultado para el archivo " + i + ": " + result);
        }
    }

    public static Map<String, Integer> map(String input) {
        Map<String, Integer> wordCounts = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(input, " \t\n\r\f,.:;?![]'\"()");

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();
            if (wordCounts.containsKey(word)) {
                wordCounts.put(word, wordCounts.get(word) + 1);
            } else {
                wordCounts.put(word, 1);
            }
        }

        return wordCounts;
    }

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
}
