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

class Tree {
    int id;
    int c;
    int depth;
    Tree parent;
    Set<Tree> children = new HashSet<Tree>();
}

class ColoredMap extends HashMap<Integer, Set<Integer>> {
    static final long serialVersionUID = 1;
    void insert(Tree node) {
        if(!containsKey(node.c)) put(node.c, new HashSet<>());
        get(node.c).add(node.id);
    }
    
    long count(ColoredMap other) {
        long result = 0;
        for(int x : keySet()) {
            if(other.containsKey(x)) {
                Set<Integer> left = get(x);
                Set<Integer> right = other.get(x);
                for(int u : left) {
                    result += right.size();
                    if(right.contains(u)) result--;
                }
            }
        }
        return result;
    }
}

class Result {

    private static ColoredMap count(Tree[] nodes, int u, int v) {
        Tree nu = nodes[u];
        Tree nv = nodes[v];
        int du = nu.depth;
        int dv = nv.depth;
        ColoredMap map = new ColoredMap();
        
        while(du > dv) {
            map.insert(nu);
            nu = nu.parent;
            du--;
        }
        while(dv > du) {
            map.insert(nv);
            nv = nv.parent;
            dv--;
        }
        while(nu != nv) {
            map.insert(nu);
            map.insert(nv);
            nu = nu.parent;
            nv = nv.parent;
        }
        map.insert(nu);
        return map;
    }

    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY c
     *  2. 2D_INTEGER_ARRAY tree
     *  3. 2D_INTEGER_ARRAY queries
     */

    public static List<Long> solve(List<Integer> c, List<List<Integer>> tree, List<List<Integer>> queries) {
        int n = c.size();
        Tree[] nodes = new Tree[n+1];
        for(int i = 1; i <= n; i++) {
            nodes[i] = new Tree();
            nodes[i].id = i;
            nodes[i].c = c.get(i-1);
        }
        
        tree.stream().forEach(edge -> {
            nodes[edge.get(0)].children.add(nodes[edge.get(1)]);
            nodes[edge.get(1)].children.add(nodes[edge.get(0)]);
        });
        
        Queue<Tree> q = new LinkedList<>();
        boolean[] visited = new boolean[n+1];
        q.add(nodes[1]);
        visited[1] = true;
        while(!q.isEmpty()) {
            Tree u = q.poll();
            u.children.removeIf(x -> visited[x.id]);
            q.addAll(u.children);
            u.children.forEach(x -> {
                visited[x.id] = true;
                x.parent = u;
                x.depth = u.depth + 1;
            });
        }
        
        return queries.stream().map(query -> {
            int w = query.get(0);
            int x = query.get(1);
            int y = query.get(2);
            int z = query.get(3);
            
            ColoredMap left = count(nodes, w, x);
            ColoredMap right = count(nodes, y, z);
            return left.count(right);
        }).collect(Collectors.toList());
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int q = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> c = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

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

        List<Long> result = Result.solve(c, tree, queries);

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

