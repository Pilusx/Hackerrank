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
        this.u = u;
        this.v = v;
    }
    
    public int hashCode() {
        return Objects.hash(u, v);
    }
    
    public boolean equals(Object other) {
        if(other == null || !(other instanceof Edge)) return false;
        Edge o = (Edge) other;
        return u == o.u && v == o.v;
    }
}

class Result {
    static int parent[];
    static List<Integer>[] neighbours;
    static Set<Edge> guesses;

    static int gcd(int a, int b) {
        if(b == 0) return a;
        return gcd(b, a%b);
    }

    static void dfs(int u, int parent) {
        for(int v : neighbours[u]) {
            if(v != parent) {
                Result.parent[v] = u;
                dfs(v, u);
            }
        }
    }
    
    static int dfs2(int u, int parent, int count, int k) {
        int good = 0;
        count += (guesses.contains(new Edge(u, parent)) ? 1 : 0) 
               + (guesses.contains(new Edge(parent, u)) ? -1 : 0);
        
        if(count >= k) good++;
        for(int v : neighbours[u]) {
            if(v != parent) good += dfs2(v, u, count, k);
        }
        return good;
    }

    /*
     * Complete the 'storyOfATree' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. 2D_INTEGER_ARRAY edges
     *  3. INTEGER k
     *  4. 2D_INTEGER_ARRAY guesses
     */

    public static String storyOfATree(int n, List<Edge> edges, int k, List<Edge> guesses_) {
    // Write your code here
        parent = new int[n+1];
        neighbours = new List[n+1];
        guesses = new HashSet<>();
        for(int i = 1; i <= n; i++) neighbours[i] = new ArrayList<>();
        for(Edge edge : edges) {
            neighbours[edge.u].add(edge.v);
            neighbours[edge.v].add(edge.u);
        }
        
        dfs(1, 0);
        
        int count = 0;
        guesses.addAll(guesses_);
        for(Edge guess : guesses_) {
            if(parent[guess.v] == guess.u) count += 1;
        }
        
        int good = dfs2(1, 0, count, k);
        int g = gcd(good, n);
        return (good / g) + "/" + (n / g);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        
        for(int i = 0; i < q; i++) {
            int n = scanner.nextInt();

            List<Edge> edges = new ArrayList<>();

            for(int j = 0; j < n - 1; j++)
                edges.add(new Edge(scanner.nextInt(), scanner.nextInt()));

            int g = scanner.nextInt();
            int k = scanner.nextInt();

            List<Edge> guesses = new ArrayList<>();

            for(int j = 0; j < g; j++)
                guesses.add(new Edge(scanner.nextInt(), scanner.nextInt()));

            String result = Result.storyOfATree(n, edges, k, guesses);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        };

        scanner.close();
        bufferedWriter.close();
    }
}

