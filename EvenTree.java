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

public class Solution {
    static int n;
    static List<Map.Entry<Integer, Integer>>[] neighboursWithSizes;

    static int dfs(int u, int parent) {
        int size = 1;
        for(Map.Entry<Integer, Integer> e : neighboursWithSizes[u]) {
            if(e.getKey() != parent) {
                e.setValue(dfs(e.getKey(), u));
                size += e.getValue();
            }
        }
        
        for(Map.Entry<Integer, Integer> e : neighboursWithSizes[u]) {
            if(e.getKey() == parent) e.setValue(n - size);
        }
        return size;
    }


    // Complete the evenForest function below.
    static int evenForest(int t_nodes, int t_edges, List<Integer> t_from, List<Integer> t_to) {
        n = t_nodes;
        neighboursWithSizes = new List[n+1];
        for(int i = 1; i <= n; i++) neighboursWithSizes[i] = new ArrayList<>();
        for(int i = 0; i < t_edges; i++) {
            int u = t_from.get(i), v = t_to.get(i);
            neighboursWithSizes[u].add(new AbstractMap.SimpleEntry<>(v, 0));
            neighboursWithSizes[v].add(new AbstractMap.SimpleEntry<>(u, 0));
        }
        dfs(1, 0);
        
        int count = 0;
        for(int i = 1; i <= n; i++) {
            for(Map.Entry<Integer, Integer> edge : neighboursWithSizes[i]) {
                if(edge.getValue() % 2 == 0) count++;
            }
        }
        return count / 2;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] tNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int tNodes = Integer.parseInt(tNodesEdges[0]);
        int tEdges = Integer.parseInt(tNodesEdges[1]);

        List<Integer> tFrom = new ArrayList<>();
        List<Integer> tTo = new ArrayList<>();

        IntStream.range(0, tEdges).forEach(i -> {
            try {
                String[] tFromTo = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                tFrom.add(Integer.parseInt(tFromTo[0]));
                tTo.add(Integer.parseInt(tFromTo[1]));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int res = evenForest(tNodes, tEdges, tFrom, tTo);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

