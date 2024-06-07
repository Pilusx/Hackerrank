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
    static int[] parent, size;
    
    static int find(int u) {
        if(parent[u] == u) return u;
        parent[u] = find(parent[u]);
        return parent[u];
    }
    
    static void union(int u, int v) {
        int pu = find(u);
        int pv = find(v);
        if(size[pu] > size[pv]) {
            parent[pv] = pu;
            size[pu] += size[pv];
        }
        else {
            parent[pu] = pv;
            size[pv] += size[pu];
        }
    }

    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY tree as parameter.
     */

    public static int solve(List<List<Integer>> tree) {
        int n = tree.size() + 1;
        List<List<Integer>> graph = new ArrayList<>();
        for(int i = 0; i <= n; i++) graph.add(new ArrayList<>());
        for(List<Integer> edge : tree) {
            graph.get(edge.get(0)).add(edge.get(1));
            graph.get(edge.get(1)).add(edge.get(0));
        }
        
        int result = n;
        parent = new int[n+1];
        size = new int[n+1];
        for(int i = 1; i <= n; i++) {
            Arrays.fill(size, 1);
            for(int j = 1; j <= n; j++) parent[j] = j;
            for(int j = i + 1; j <= n; j++) {
                for(int x : graph.get(j)) {
                    if(i <= x && x <= j) union(j, x);
                }
                if(size[find(i)] == j - i + 1) result++;
            }
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> tree = new ArrayList<>();

        IntStream.range(0, n - 1).forEach(i -> {
            try {
                tree.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.solve(tree);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

