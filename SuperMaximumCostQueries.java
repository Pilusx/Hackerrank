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
    int u, v, w;
    
    Edge(List<Integer> edge) {
        assert(edge.size() == 3);
        u = edge.get(0);
        v = edge.get(1);
        w = edge.get(2);
    } 
    
    int getWeight() {
        return w;
    }
}

class Result {
    static int[] parent, size;

    static int find(int u) {
        if(parent[u] != u) parent[u] = find(parent[u]);
        return parent[u];
    }
    
    static void union(int u, int v) {
        int pu = find(u);
        int pv = find(v);
        if(pu == pv) return;
        if(size[pu] > size[pv]) {
            size[pu] += size[pv];
            parent[pv] = pu;
        } else {
            size[pv] += size[pu];
            parent[pu] = pv;
        }
    }
    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY tree
     *  2. 2D_INTEGER_ARRAY queries
     */

    public static List<Long> solve(List<List<Integer>> tree, List<List<Integer>> queries) {
        List<Edge> edges = tree.stream()
            .map(Edge::new)
            .sorted(Comparator.comparingInt(Edge::getWeight))
            .collect(Collectors.toList());
        
        parent = new int[tree.size() + 2];
        size = new int[tree.size() + 2];
        for(int i = 0; i < parent.length; i++) 
            parent[i] = i;
        Arrays.fill(size, 1);

        Map<Integer, Long> paths = new TreeMap<>();
        for(Edge e : edges) {
            paths.merge(e.w, (long)size[find(e.u)] * size[find(e.v)], Long::sum);
            union(e.u, e.v);
        }
        
        TreeMap<Integer, Long> sums = new TreeMap<>();
        long sum = 0;
        sums.put(0, 0L);
        for(Map.Entry<Integer, Long> entry : paths.entrySet()) {
            sum += entry.getValue();
            sums.put(entry.getKey(), sum);
        }
        
        List<Long> result = new ArrayList<>();
        for(List<Integer> query : queries) {
           int l = query.get(0);
           int r = query.get(1);
           result.add(sums.floorEntry(r).getValue() - sums.lowerEntry(l).getValue());
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

        int q = Integer.parseInt(firstMultipleInput[1]);

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

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, q).forEach(i -> {
            try {
                queries.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Long> result = Result.solve(tree, queries);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

