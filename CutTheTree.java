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

class Graph {
    int n, sum;
    List<Integer> data;
    List<Map.Entry<Integer, Integer>>[] neighboursWithSums;
    
    Graph(List<Integer> data, List<List<Integer>> edges) {
        n = data.size();
        sum = data.stream().mapToInt(Integer::valueOf).sum();
        this.data = data;
        this.data.add(0, 0);
        neighboursWithSums = new List[n+1];
        for(int i = 0; i < neighboursWithSums.length; i++)
            neighboursWithSums[i] = new ArrayList<>();
        for(List<Integer> edge : edges) {
            int u = edge.get(0), v = edge.get(1);
            neighboursWithSums[u].add(new AbstractMap.SimpleEntry<>(v, 0));
            neighboursWithSums[v].add(new AbstractMap.SimpleEntry<>(u, 0));
        }
        dfs(1, 0);
    }
    
    int dfs(int u, int parent) {
        int childrenSum = data.get(u);
        for(Map.Entry<Integer, Integer> entry : neighboursWithSums[u]) {
            if(entry.getKey() != parent) {
                entry.setValue(dfs(entry.getKey(), u));
                childrenSum += entry.getValue();
            }
        }
        
        for(Map.Entry<Integer, Integer> entry : neighboursWithSums[u]) {
            if(entry.getKey() == parent) {
                entry.setValue(sum - childrenSum);
                break;
            }
        }
        return childrenSum;
    }
    
    int minimumSum() {
        int minSum = Integer.MAX_VALUE;
        for(List<Map.Entry<Integer, Integer>> sums : neighboursWithSums) {
            for(Map.Entry<Integer, Integer> entry : sums)
                minSum = Math.min(minSum, Math.abs(2 * entry.getValue() - sum));
        }
        return minSum;
    }
}

class Result {

    /*
     * Complete the 'cutTheTree' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY data
     *  2. 2D_INTEGER_ARRAY edges
     */

    public static int cutTheTree(List<Integer> data, List<List<Integer>> edges) {
    // Write your code here
        return new Graph(data, edges).minimumSum();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> data = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<List<Integer>> edges = new ArrayList<>();

        IntStream.range(0, n - 1).forEach(i -> {
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

        int result = Result.cutTheTree(data, edges);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

