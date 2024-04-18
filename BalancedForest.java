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

class Edge {
    int u, v;
    
    Edge(int u, int v) {
        this.u = Math.min(u, v);
        this.v = Math.max(u, v);
    }
}

class Result {
    static int[] parent;
    static long[] sum;
    
    static int find(int u) {
        if(parent[u] == u) return u;
        parent[u] = find(parent[u]);
        return parent[u];
    }
    
    static void union(Edge edge) {
        int pu = find(edge.u);
        int pv = find(edge.v);
        if(pu != pv) {
            if(sum[pu] > sum[pv]) {
                sum[pu] += sum[pv];
                parent[pv] = pu;
            } else {
                sum[pv] += sum[pu];
                parent[pu] = pv;
            }
        }
    }
    
    static List<Long> forest() {
        List<Long> result = new ArrayList<>();
        for(int i = 1; i < parent.length; i++) {
            if(parent[i] == i) result.add(sum[i]);
        }
        while(result.size() < 3) result.add(0L);
        return result;
    }

    /*
     * Complete the 'balancedForest' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY c
     *  2. 2D_INTEGER_ARRAY edges
     */

    public static int balancedForest(List<Integer> c, List<List<Integer>> edges) {
    // Write your code here
        List<Edge> edges2 = edges.stream().map(v -> new Edge(v.get(0), v.get(1))).collect(toList());
        
        long minimum = Long.MAX_VALUE;
        for(int i = 0; i + 1 < edges2.size(); i++) {
            for(int j = i; j < edges2.size(); j++) {
                parent = new int[c.size() + 1];
                sum = new long[c.size() + 1];
                for(int k = 1; k <= c.size(); k++) {
                    parent[k] = k;
                    sum[k] = c.get(k-1);
                }
                for(int k = 0; k < edges2.size(); k++) {
                    if(k != i && k != j) {
                        union(edges2.get(k));
                    }
                }
                
                List<Long> sums = forest();
                Collections.sort(sums);
                if(sums.get(1).equals(sums.get(2))) {
                    minimum = Math.min(minimum, sums.get(1) - sums.get(0));
                }
            }
        }
        return minimum != Long.MAX_VALUE ? (int)minimum : -1;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> c = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
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

                int result = Result.balancedForest(c, edges);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

