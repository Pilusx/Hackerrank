import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UniqueColors {
    final static int N = 1 << 18;
    
    static int[] color = new int[N],
                 nodesOfColor = new int[N];
    static boolean[] used = new boolean[N],
                     usedColor = new boolean[N];

    static List<Integer> preorder = new ArrayList<>();
    static List<Map.Entry<Integer, Integer>>[] edges = new List[N], 
                                               colorsWithSizes = new List[N];

    static long total; // Sum of nodesOfColor
    static long[] result = new long[N];

    static int dfsSize(int n, int parent, int size) {
        assert(!used[n]);
        preorder.add(n);
        usedColor[color[n]] = false;

        int ret = 1;
        for(Map.Entry<Integer, Integer> edge : edges[n]) {
            int key = edge.getKey();
            if(key != parent && !used[key]) {
                edge.setValue(dfsSize(key, n, size));
                ret += edge.getValue();
            }
        }

        for(Map.Entry<Integer, Integer> edge : edges[n])
            if(edge.getKey() == parent) edge.setValue(size - ret);

        return ret;
    }

    static int getSize(int n, int parent) {
    	return 1 + edges[n].stream()
                           .filter(e -> e.getKey() != parent)
                           .mapToInt(Map.Entry::getValue)
                           .sum();
    }
    
    static void dfs2(int n, int parent, int s) {
        boolean c = false;
        if(!usedColor[color[n]]) {
            c = true;
            usedColor[color[n]] = true;
        }

        edges[n].removeIf(e -> used[e.getKey()]);
        for(Map.Entry<Integer, Integer> edge : edges[n]) {
            int key = edge.getKey();
            if(key != parent) dfs2(key, n, s);
        }

        if(c) {
            colorsWithSizes[s].add(new AbstractMap.SimpleEntry<>(color[n], getSize(n, parent)));
            usedColor[color[n]] = false;
        }
    }

    static void add(int n) {
        for(Map.Entry<Integer, Integer> entry : colorsWithSizes[n]) {
            nodesOfColor[entry.getKey()] += entry.getValue();
            total += entry.getValue();
        }
    }

    static void erase(int n) {
        for(Map.Entry<Integer, Integer> entry : colorsWithSizes[n]) {
            nodesOfColor[entry.getKey()] -= entry.getValue();
            total -= entry.getValue();
        }
    }

    static void dfs3(int n, int parent, int size) {
        boolean c = false;
        if(!usedColor[color[n]]) {
            c = true;
            usedColor[color[n]] = true;
            total += size - nodesOfColor[color[n]];
        }

        result[n] += total;
        for(Map.Entry<Integer, Integer> edge : edges[n]) {
            int key = edge.getKey();
            if(key != parent) dfs3(key, n, size);
        }
        if(c) {
            usedColor[color[n]] = false;
            total -= size - nodesOfColor[color[n]];
        }
    }
    
    static int findCentroid(int C, int size) {
        for(int u : preorder) {
            boolean c = false;
            for(Map.Entry<Integer, Integer> edge : edges[u])
                if(edge.getValue() > size / 2 ) { 
                    c = true; 
                    break; 
                }
            if(!c) return u;
        }
        return C;
    }

    static void solve(int C, int parent, int size) {
        preorder.clear();

        dfsSize(C, parent, size);
        C = findCentroid(C, size);

        used[C] = true;
        usedColor[color[C]] = true;
        nodesOfColor[color[C]] = 1;
        total = 1L;
        edges[C].removeIf(e -> used[e.getKey()]);

        for(int i=0;i<edges[C].size();i++) {
            int key = edges[C].get(i).getKey();
            colorsWithSizes[i].clear();
            dfs2(key, C, i);
            colorsWithSizes[i].add(new AbstractMap.SimpleEntry<>(color[C], getSize(key, C)));
        }

        for(int i=0;i<edges[C].size();i++) add(i);    

        result[C] += total;
        for(int i=0;i<edges[C].size();i++) {
        	erase(i);
            dfs3(edges[C].get(i).getKey(), C, size - edges[C].get(i).getValue());
            add(i);
    	}

        for(int i=0;i<edges[C].size();i++) erase(i);

        usedColor[color[C]] = false;
        nodesOfColor[color[C]] = 0;
        for(Map.Entry<Integer, Integer> edge : edges[C])
            solve(edge.getKey(), C, edge.getValue());

    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();

        for(int i = 0; i < N; i++) {
            colorsWithSizes[i] = new ArrayList<>();
            edges[i] = new ArrayList<>();
        }
        
        for(int i = 1; i <= a; i++) color[i] = scanner.nextInt();

        for(int i = 1; i < a; i++) {
            int j = scanner.nextInt();
            int k = scanner.nextInt();
            edges[j].add(new AbstractMap.SimpleEntry<Integer, Integer>(k, 0));
            edges[k].add(new AbstractMap.SimpleEntry<Integer, Integer>(j, 0));
        }

        solve(1, 0, a);

        for(int i=1; i<=a; i++)
            System.out.println(result[i]);
        scanner.close();
    }

}
