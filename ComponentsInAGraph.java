import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    static boolean[] visited;

    /*
     * Complete the 'componentsInGraph' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts 2D_INTEGER_ARRAY gb as parameter.
     */

    public static List<Integer> componentsInGraph(List<List<Integer>> gb) {
        List<List<Integer>> graph = new ArrayList<>();
        int min_result = Integer.MAX_VALUE;
        int max_result = 0;
        int result;
        visited = new boolean[30_001];
        
        for(int i = 0; i <= 30_000; i++) graph.add(new ArrayList<>());
        for(List<Integer> edge : gb) {
            graph.get(edge.get(0)).add(edge.get(1));
            graph.get(edge.get(1)).add(edge.get(0));
        }
        for(int i = 1; i <= 30_000; i++) {
            if(!visited[i]) {
                result = 1;
                visited[i] = true;
                Queue<Integer> q = new LinkedList<>();
                q.addAll(graph.get(i));
                while(!q.isEmpty()) {
                    int u = q.poll();
                    if(!visited[u]) {
                        visited[u] = true;
                        result++;
                        q.addAll(graph.get(u));
                    }
                }
                if(result > 1) {
                    min_result = Math.min(min_result, result);
                    max_result = Math.max(max_result, result);
                }
           } 
        }
        return Arrays.asList(min_result, max_result);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> gb = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                gb.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result.componentsInGraph(gb);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining(" "))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

