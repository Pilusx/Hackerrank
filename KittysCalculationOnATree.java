import java.io.*;
import java.util.*;

public class Kitty {
    final static long N = 1_000_000_007;
    final static int K = 200_001;
    static long[] depth = new long[K];
    static long[] sums = new long[K];
    static List<Integer> reversed_inorder = new ArrayList<>();

    public static long add(long... arr) {
        long result = 0;
        for(long x : arr) { result = (result + x + N) % N; }
        return result;
    }
    
    public static long mul(long... arr) {
        long result = 1;
        for(long x : arr) { result = (result * x) % N; }
        return result;
    }

    public static long solve(List<List<Integer>> edges, Set<Integer> set) {
        if(set.size() < 2) return 0;
        long result = 0;
        long sum = 0;
        for(int x : set) sum = add(sum, x);
        for(int x : set) result = add(result, mul(x, add(sum, -x), depth[x]));

        // x * (sum - x) d[x] = u * v * (d[u] + d[v])

        for(int u : reversed_inorder) {
            for(int v : edges.get(u)) {
                if(depth[v] == depth[u] + 1) {
                    sums[u] = add(sums[u], sums[v]);
                }
            }
            for(int v : edges.get(u)) {
                if(depth[v] == depth[u] + 1) {
                    result = add(result, -mul(sums[v], add(sums[u], -sums[v]), depth[u]));
                }
            }
            if(set.contains(u)) {
                for(int v : edges.get(u)) {
                    if(depth[v] == depth[u] + 1) {
                        result = add(result, -mul(2, u, sums[v], depth[u]));
                    }
                }
                sums[u] = add(sums[u], u);
            }
            
        }
        return result;
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int q = scanner.nextInt();
        
        List<List<Integer>> edges = new ArrayList<>();
        for(int i = 0; i < n + 1; i++) {
            edges.add(new ArrayList<>());
        }
        
        for(int i = 0; i < n - 1; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            edges.get(a).add(b);
            edges.get(b).add(a);
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(1);
        Arrays.fill(depth, -1);
        depth[1] = 0;
        while(!queue.isEmpty()) {
            int u = queue.poll();
            reversed_inorder.add(u);
            for(int v : edges.get(u)) {
                if(depth[v] == -1) {
                    depth[v] = depth[u] + 1;
                    queue.add(v);
                }
            }
        }
        Collections.reverse(reversed_inorder);
        
        for(int i = 0; i < q; i++) {
            int k = scanner.nextInt();
            Set<Integer> set = new HashSet<>();
            for(int j = 0; j < k; j++) {
                int ki = scanner.nextInt();
                set.add(ki);
            }
            long result = solve(edges, set);
            System.out.println(result);
        }
    }
}
