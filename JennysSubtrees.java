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

class Tree implements Comparable<Tree> {
    List<Tree> children = new ArrayList<>();
    boolean sorted = false;
    boolean hashed = false;
    int hash = 0;
    final static int N = 1_000_000_007;
    final static int P = 31;
    
    public void sort() {
        if(!sorted) {
            for(Tree t : children) t.sort();
            Collections.sort(children);
            sorted = true;
        }
    }

    public int hashCode() {
        sort();
        if(!hashed) {
            hash = children.size();
            for(int i = 0; i < children.size(); i++) {
                hash = (P * hash + children.get(i).hashCode()) % N;
            }
            hashed = true;
        }
        return hash;
    }
    
    public int compareTo(Tree other) {
        if(children.size() > other.children.size()) return 1;
        else if (children.size() < other.children.size()) return -1;
        for(int i = 0; i < children.size(); i++) {
            int temp = children.get(i).compareTo(other.children.get(i));
            if(temp != 0) return temp;
        }
        return 0;
    }
    
    public String toString() {
        return Arrays.toString(children.stream().map(Tree::toString).toArray());
    }
}


class Result {
    
    public static Tree search(int x, int n, int r, List<List<Integer>> edges) {
        List<List<Integer>> subgraph = new ArrayList<>();
        for(int i = 0; i <= n; i++) subgraph.add(new ArrayList<>());
        
        int[] depth = new int[n+1];
        Arrays.fill(depth, -1);
        depth[x] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(x);

        while(!queue.isEmpty()) {
            int u = queue.poll();
            if(depth[u] < r) {
                for(int v : edges.get(u)) {
                    if(depth[v] == -1) {
                        depth[v] = depth[u] + 1;
                        queue.add(v);
                        subgraph.get(u).add(v);
                        subgraph.get(v).add(u);
                    }
                } 
            }
        }
        int firstEnd = 0, secondEnd = 0; // Ends of diameter
        for(int i = 1; i < depth.length; i++) {
            if(depth[i] > depth[firstEnd]) firstEnd = i;
        }
        
        int[] parent = new int[n+1];
        parent[firstEnd] = firstEnd;
        depth[firstEnd] = 0;
        Arrays.fill(depth, -1);
        queue.add(firstEnd);
        
        while(!queue.isEmpty()) {
            int u = queue.poll();
            secondEnd = u;
            for(int v : subgraph.get(u)) {
                if(depth[v] == -1) {
                    depth[v] = depth[u] + 1;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }
        
        // Find the centers of the tree
        List<Integer> centers = new ArrayList<>();
        int current = secondEnd;
        int d = (depth[secondEnd] + 1)/2;
        while(depth[current] != d) current = parent[current];
        centers.add(parent[current]);
        if (depth[secondEnd] % 2 == 0) {
            centers.add(current);
        }
        
        // Build the tree
        Tree[] trees = new Tree[n+1];
        for(int i = 0; i <= n; i++) trees[i] = new Tree();
        Arrays.fill(depth, -1);
        
        for(int center : centers) {
            trees[0].children.add(trees[center]);
            queue.add(center);
            depth[center] = 0;
        }
        
        while(!queue.isEmpty()) {
            int u = queue.poll();
            for(int v : subgraph.get(u)) {
                if(depth[v] == -1) {
                    queue.add(v);
                    depth[v] = depth[u] + 1;
                    trees[u].children.add(trees[v]);
                }
            }
        }
        trees[0].hashCode(); // Sort and evaluate hash.
        return trees[0];
    }
    /*
     * Complete the 'jennysSubtrees' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER r
     *  3. 2D_INTEGER_ARRAY edges
     */

    public static int jennysSubtrees(int n, int r, List<List<Integer>> edges) {
    // Write your code here
        List<List<Integer>> edges2 = new ArrayList<>();
        for(int i = 0; i <= n; i++) edges2.add(new ArrayList<>());
        for(List<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            edges2.get(u).add(v);
            edges2.get(v).add(u);
        }
        
        Set<Tree> differentTrees = new TreeSet<>();
        for(int i = 1; i <= n; i++) {
            Tree tree = search(i, n, r, edges2);
            // System.out.println(String.format("%3d", i) + " " + tree + " " + tree.hashCode());
            differentTrees.add(tree);
        }
        return differentTrees.size();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int r = Integer.parseInt(firstMultipleInput[1]);

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

        int result = Result.jennysSubtrees(n, r, edges);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

