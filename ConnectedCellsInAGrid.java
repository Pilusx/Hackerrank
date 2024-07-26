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
    static int n, m;
    static int[] parent, size;
    
    static int index(int i, int j) {
        return i*m+j;
    }
    
    static List<List<Integer>> neighbours(int i, int j) {
        List<List<Integer>> result = new ArrayList<>();
        if(i+1 < n) {
            if(j-1 >= 0) result.add(Arrays.asList(i+1, j-1));
            result.add(Arrays.asList(i+1, j));
            if(j+1 < m) result.add(Arrays.asList(i+1, j+1));
        }
        if(j+1 < m) result.add(Arrays.asList(i, j+1));
        
        return result;
    }
    
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
     * Complete the 'connectedCell' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY matrix as parameter.
     */

    public static int connectedCell(List<List<Integer>> matrix) {
        n = matrix.size();
        m = matrix.get(0).size();
        parent = new int[n*m];
        size = new int[n*m];
        for(int i = 0; i < parent.length; i++)
            parent[i] = i;
        Arrays.fill(size, 1);
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(matrix.get(i).get(j) != 1) continue;
                int ind = index(i, j);
                for(List<Integer> point : neighbours(i, j)) {
                    if(matrix.get(point.get(0)).get(point.get(1)) == 1)
                        union(ind, index(point.get(0), point.get(1)));
                }
            }
        }
        
        int result = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(matrix.get(i).get(j) == 1)
                    result = Math.max(result, size[find(index(i, j))]);
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

        int m = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> matrix = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                matrix.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.connectedCell(matrix);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

