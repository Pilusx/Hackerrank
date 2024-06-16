import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

class FenwickTree {
    int[] data;
	
	FenwickTree(int n) {
		data = new int[n];
	}
	
    void add(int i, int x) {
        while(i < data.length) {
            data[i] += x;
            i += i & -i;
        }
    }

    int sum(int i) {
        int res = 0;
        while(i > 0) {
            res += data[i];
            i -= i & -i;
        }
        return res;
    }
}

public class SelfDrivingBus {
    final static int logN = 20,
                     N = 1 << logN;
    static int n;
    
    static int[] depth = new int[N],
                 father = new int[N],
                 leftFirstBad = new int[N],
                 rightLastGood = new int[N];
    
    static int[][] lca = new int[N][logN],
                   minNode = new int[N][logN],
                   maxNode = new int[N][logN];
    
    static List<Integer>[] g = new List[N];
    
    static void dfs(int u, int dep, int par) {
        father[u] = par;
        depth[u] = dep;
        for(int v : g[u]) {
            if(v != par) {
                dfs(v, dep + 1, u);
            }
        }
    }

    static void lcaInit(int n) {
        for(int i = 0; i < n; i++) {
            Arrays.fill(lca[i], -1);
            Arrays.fill(maxNode[i], -1);
            Arrays.fill(minNode[i], Integer.MAX_VALUE);
        }
        for(int i = 0; i < n; i++) {
            lca[i][0] = father[i];
            minNode[i][0] = i;
            maxNode[i][0] = i;
        }
        for(int j = 1; (1 << j) <= n; j++)
            for(int i = 0; i < n; i++) {
                int x = lca[i][j - 1];
                if(x == -1) continue;
                lca[i][j] = lca[x][j - 1];
                minNode[i][j] = Math.min(minNode[i][j - 1], minNode[x][j - 1]);
                maxNode[i][j] = Math.max(maxNode[i][j - 1], maxNode[x][j - 1]);
            }
    }

    static int lcaUp(int u, int steps) {
        for(int i = 0; i < logN; i++) 
            if((steps & (1 << i)) != 0)
                u = lca[u][i];
        return u;
    }

    static int lca(int a, int b) {
        if(depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }
        a = lcaUp(a, depth[a] - depth[b]);
        if(a == b) return a;
        for(int i = logN-1; i >= 0; i--) 
            if(lca[a][i] != lca[b][i]) {
                a = lca[a][i]; 
                b = lca[b][i];
            }
        return lca[a][0];
    }

    static int getMin(int a, int b) {
        assert(depth[a] >= depth[b]);
        int steps = depth[a] - depth[b] + 1;
        for(int i = logN - 1; i >= 0; i--) {
            if(steps >> i > 0) {
                if(steps == (1 << i)) return minNode[a][i];
                int up = steps - (1 << i);
                int c = lcaUp(a, up);
                return Math.min(minNode[a][i], minNode[c][i]);
            }
        }
        return 0;
    }

    static int getMax(int a, int b) {
        assert(depth[a] >= depth[b]);
        int steps = depth[a] - depth[b] + 1;
        for(int i = logN - 1; i >= 0; i--) {
            if(steps >> i > 0) {
                if(steps == (1 << i)) return maxNode[a][i];
                int up = steps - (1 << i);
                int c = lcaUp(a, up);
                return Math.max(maxNode[a][i], maxNode[c][i]);
            }
        }
        return 0;
    }

    static int minPath(int a, int b) {
        int x = lca(a, b);
        return Math.min(getMin(a, x), getMin(b, x));
    }

    static int maxPath(int a, int b) {
        int x = lca(a, b);
        return Math.max(getMax(a, x), getMax(b, x));
    }

    static long solve() {
        dfs(0, 0, -1);
        for(int i = 1; i < n; i++) assert(father[i] != -1);

        lcaInit(n);

        for(int i = 0; i < n; i++) {
            leftFirstBad[i] = n;
            rightLastGood[i] = 0;
        }

        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < n; i++) {
            while(!stack.empty() && minPath(i, stack.peek()) < stack.peek()) {
                leftFirstBad[stack.peek()] = i;
                stack.pop();
            }
            stack.add(i);
        }

        stack.clear();
        for(int i = n - 1; i >= 0; i--) {
            while(stack.size() > 0 && maxPath(i, stack.peek()) > stack.peek()) {
                rightLastGood[stack.peek()] = i + 1;
                stack.pop();
            }
            stack.add(i);
        }

        List<Integer>[] g = new List[N];
        for(int i = 0; i < N; i++) g[i] = new ArrayList<>();
        for(int i = 0; i < n; i++) g[rightLastGood[i]].add(i);

        FenwickTree tree = new FenwickTree(N);
        long res = 0;
        for(int i = 0; i < n; i++) {
            for(int j : g[i]) tree.add(j + 1, 1);
            res += tree.sum(leftFirstBad[i]) - i;
        }
        return res;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        
        for(int i = 0; i < n; i++) g[i] = new ArrayList<>();

        for(int i = 0; i < n - 1; i++) {
            int a = scanner.nextInt() - 1;
            int b = scanner.nextInt() - 1;
            g[a].add(b);
            g[b].add(a);
        }

        System.out.println(solve());
        scanner.close();
    }
}
