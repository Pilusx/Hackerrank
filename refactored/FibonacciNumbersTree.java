import java.io.*;
import java.util.*;
import java.util.function.Consumer;

class MyNode {
    int Sum, Lazy0, Lazy1, F1, F2;
    
    int sum() {
        return SegmentTree.add(
                SegmentTree.multiply(Lazy0, F1),
                SegmentTree.multiply(Lazy1, F2));
    }
};

class SegmentTree {
    static final int logK = 17;
    static final int K = 1 << logK;
    static final int P = 1_000_000_007;
    static final int constant = 99_999;

    int n, m;
    List<Integer>[] children = new List[K];
    int[] size = new int[K],
          chainHead = new int[K], 
          chainInd = new int[K], 
          posInBase = new int[K], 
          base = new int[K], 
          depth = new int[K],
          F = new int[K];
    int[][] LCA = new int[K][logK+1];
    MyNode[] nodes = new MyNode[2*K];
    
    SegmentTree() {
        for(int i = 0; i < children.length; i++) children[i] = new ArrayList<>();
        for(int i = 0; i < nodes.length; i++) nodes[i] = new MyNode();
    }

    static int add(int ...a) {
        int result = 0;
        for(int x : a) {
            result = (result + x) % P;
        }
        return result;
    }
    
    static int multiply(int ...a) {
        long result = 1;
        for(int x : a) {
            result = (result * x) % P;
        }
        return (int)result;
    }

    void HLD() {
        Stack<Integer> stack = new Stack<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[K];
        int[] heavy = new int[K];
        
        queue.add(1);
        while(!queue.isEmpty()) {
            int u = queue.poll();
            stack.push(u);
            queue.addAll(children[u]);
        }

        for(int i = 1; i <= n; i++) heavy[i] = i;

        while(!stack.empty()) {
            int x = stack.peek(); 
            stack.pop();
            int p = LCA[x][0];
            if(p == -1) continue;
            if(heavy[p] == p || size[heavy[p]] < size[x]) heavy[p] = x;
        }
      
        int ptr = 0, chainNo = 1;
        stack.push(1);
        chainInd[1] = 1;
        chainHead[1] = 1;

        while(!stack.empty()) {
            int u = stack.peek();
            if(!visited[u]) {
                ptr++;
                base[ptr] = u;
                visited[u] = true;
                if(heavy[u] != u) {
                    stack.push(heavy[u]);
                    chainInd[heavy[u]] = chainInd[u];
                }
            } else {
                stack.pop();
                for(int v : children[u]) {
                    if(v != heavy[u]) {
                        chainNo++;
                        chainInd[v] = chainNo;
                        chainHead[chainNo] = v;
                        stack.push(v);
                    }
                }
            }
        }

        for(int i = 1; i <= n; i++) posInBase[base[i]] = i;
    }

    void build(){
        for(int i = 1; i <= n; i++) {
            nodes[K+i].F1 = F[depth[base[i]]];
            nodes[K+i].F2 = F[depth[base[i]]+1];
        }
        for(int i = K-1; i > 0; i--) {
            nodes[i].F1 = add(nodes[2*i].F1, nodes[2*i+1].F1);
            nodes[i].F2 = add(nodes[2*i].F2, nodes[2*i+1].F2);
        }
    }

    void update(int l, int r, int f0, int f1){
        l += K;
        r += K;
        
        Consumer<Integer> update = x -> {
            nodes[x].Lazy0 = add(nodes[x].Lazy0, f0);
            nodes[x].Lazy1 = add(nodes[x].Lazy1, f1);
        };
        
        Consumer<Integer> sum = x -> {
            nodes[x].Sum = nodes[x].sum();
            if(x < K) nodes[x].Sum = add(nodes[x].Sum, nodes[2*x].Sum, nodes[2*x+1].Sum);
        };
        
        update.accept(l);
        if(l != r) update.accept(r);
        while(l > 0) {
            sum.accept(l);
            if(l != r) sum.accept(r);
            if(l + 1 < r) {
                if(l % 2 == 0) {
                    update.accept(l+1);
                    sum.accept(l+1);
                }
                if(r % 2 == 1) {
                    update.accept(r-1);
                    sum.accept(r-1);
                }
            }
            l /= 2;
            r /= 2;
        }
    }

    int query(int l, int r){
        l += K;
        r += K;
        
        int sum = 0;
        int lf1 = 0, lf2 = 0, rf1 = 0, rf2 = 0;
        
        sum = add(sum, nodes[l].Sum);
        lf1 = add(lf1, nodes[l].F1); lf2 = add(lf2, nodes[l].F2);
        if(l != r) {
            sum = add(sum, nodes[r].Sum);
            rf1 = add(rf1, nodes[r].F1); rf2 = add(rf2, nodes[r].F2);
        }
        while(l > 1) {
            if(l + 1 < r) {
                if(l % 2 == 0) {
                    lf1 = add(lf1, nodes[l+1].F1); lf2 = add(lf2, nodes[l+1].F2);
                    sum = add(sum, nodes[l+1].Sum);
                }
                if(r % 2 == 1) {
                    sum = add(sum, nodes[r-1].Sum);
                    rf1 = add(rf1, nodes[r-1].F1); rf2 = add(rf2, nodes[r-1].F2);
                }
            }
            l /= 2;
            r /= 2;
            sum = add(sum, multiply(lf1, nodes[l].Lazy0), multiply(lf2, nodes[l].Lazy1),
                           multiply(rf1, nodes[r].Lazy0), multiply(rf2, nodes[r].Lazy1));
        }
        return sum;
    }

    int queryUp(int u, int v) {
        int uchain, vchain = chainInd[v];
        int ans = 0; 
        while(true) {
            uchain = chainInd[u];
            if(uchain == vchain) {
                ans = add(ans, query(posInBase[v], posInBase[u]));
                break;
            }
            ans = add(ans, query(posInBase[chainHead[uchain]], posInBase[u]));
            u = chainHead[uchain]; 
            u = LCA[u][0];
        }
        return ans;
    }
    
    void dfs() {
        Queue<Integer> queue = new LinkedList<>();
        Stack<Integer> stack = new Stack<>();

        depth[1] = 1;
        queue.add(1);
        while(!queue.isEmpty()) {
            int u = queue.poll();
            stack.push(u);
            for(int v : children[u]) {
                LCA[v][0] = u;
                depth[v] = depth[u] + 1;
                queue.add(v);
            }
        }

        Arrays.fill(size, 1);
        while(!stack.empty()) {
            int u = stack.peek(); 
            stack.pop();
            for(int v : children[u]) {
                size[u] += size[v];
            }
        }
    }
    
    void constructLCA() {
        for(int i = 0; i < n; i++)
            Arrays.fill(LCA[i], -1);

        dfs();
        for(int i = 1; i <= logK; i++) {
            for(int j = 1; j <= n; j++) {
                if(LCA[j][i-1] != -1) {
                    LCA[j][i] = LCA[LCA[j][i-1]][i-1];
                }
            }
        }
    }
    
    int getLCA(int x, int y) {
        if(depth[x] < depth[y]) {
            int temp = x;
            x = y;
            y = temp;
        }
        for(int i = logK; i >= 0; i--) {
            if( LCA[x][i] != -1 && depth[LCA[x][i]] >= depth[y] )
                x = LCA[x][i];
        }
        if( x == y )
            return x;

        for(int i = logK; i >= 0; i--) {
            if( LCA[x][i] != -1 && LCA[x][i] != LCA[y][i] ) {
                x = LCA[x][i];
                y = LCA[y][i];
            }
        }
        return LCA[x][0];
    }
    
    void preprocessFibonacci() {
        F[1] = 1;
        for(int i = 2; i < K; i++)
            F[i] = add(F[i-1], F[i-2]);
    }
};


public class FibonacciNumbersTree {
    static final int fib0 = 88564505;
    static final int fib1 = 56182730;

    static void multiply(int[][] A, int[][] B) {
        int[][] temp = new int[2][2];
        for(int i = 0; i <= 1; i++) {
            for(int j = 0; j <= 1; j++) {
                for(int k = 0; k <= 1; k++) {
                    temp[i][j] = SegmentTree.add(temp[i][j], 
                                                  SegmentTree.multiply(A[i][k], B[k][j]));
                }
            }
        }
        for(int i = 0; i <= 1; i++) {
            for(int j = 0; j <= 1; j++) {
                A[i][j] = temp[i][j];
            }
        }
    }

    static int[][] fibonacci(long x) {
        int[][] I = new int[][]{{1, 0}, {0, 1}},
                T = new int[][]{{0, 1}, {1, 1}};
        
        while(x > 0) {
            if(x % 2 == 1) 
                multiply(I, T);
            x /= 2;
            multiply(T, T);
        }
        return I;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SegmentTree tree = new SegmentTree();
        final int P = SegmentTree.P;

        tree.n = scanner.nextInt();
        tree.m = scanner.nextInt();

        for(int i = 2; i <= tree.n; i++) {
            int p = scanner.nextInt(); 
            tree.children[p].add(i);
        }

        tree.constructLCA();
        tree.HLD();
        tree.preprocessFibonacci();
        tree.build();
        
        scanner.nextLine();
        for(int i = 0; i < tree.m; i++) {
            String[] values = scanner.nextLine().split(" ");
            char type = values[0].charAt(0);
            if( type == 'U' ) {
                int u = Integer.parseInt(values[1]);
                long k = Long.parseLong(values[2]) - tree.depth[u] + SegmentTree.constant;
                int[][] I = fibonacci(k);
                int f0 = SegmentTree.add(SegmentTree.multiply(I[0][0], fib0), 
                                          SegmentTree.multiply(I[0][1], fib1)), 
                    f1 = SegmentTree.add(SegmentTree.multiply(I[1][0], fib0), 
                                          SegmentTree.multiply(I[1][1], fib1)); 
                tree.update(tree.posInBase[u], 
                             tree.posInBase[u] + tree.size[u] - 1, 
                             f0, f1);
            } else {
                int u = Integer.parseInt(values[1]);
                int v = Integer.parseInt(values[2]);
                int lca = tree.getLCA(u, v);
                int ans = SegmentTree.add( 
                            tree.queryUp(u, lca), 
                            tree.queryUp(v, lca),
                            P - tree.query(tree.posInBase[lca], tree.posInBase[lca]));
                System.out.println(ans);
            }
        }
        scanner.close();
    }
}
