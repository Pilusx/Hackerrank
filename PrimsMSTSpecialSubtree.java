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
     * Complete the 'prims' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. 2D_INTEGER_ARRAY edges
     *  3. INTEGER start
     */

    public static int prims(int n, List<List<Integer>> edges, int start) {
        int result = 0;
        visited = new boolean[n+1];
        
        List<List<List<Integer>>> graph = new ArrayList<>();
        for(int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        
        for(List<Integer> edge : edges) {
            graph.get(edge.get(0)).add(Arrays.asList(edge.get(1), edge.get(2)));
            graph.get(edge.get(1)).add(Arrays.asList(edge.get(0), edge.get(2)));
        }
        
        PriorityQueue<List<Integer>> q = new PriorityQueue<>((x, y) -> x.get(1).compareTo(y.get(1)));
        q.add(Arrays.asList(start, 0));
        
        while(!q.isEmpty()) {
            List<Integer> edge = q.poll();
            int u = edge.get(0);
            int weight = edge.get(1);
            if(!visited[u]) {
                result += weight;
                visited[u] = true;
                q.addAll(graph.get(u));
            }
        }

        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> edges = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                edges.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int start = Integer.parseInt(bufferedReader.readLine().trim());

        int result = Result.prims(n, edges, start);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

