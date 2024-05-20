import java.io.*;
import java.util.*;

public class Solution {
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


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int q = scanner.nextInt();
        scanner.nextLine();
        
        parent = new int[n+1];
        size = new int[n+1];
        Arrays.fill(size, 1);
        for(int i = 0; i <= n; i++) parent[i] = i;

        for(int i = 0; i < q; i++) {
            String[] line = scanner.nextLine().split(" ");
            int u = Integer.parseInt(line[1]), v;
            switch(line[0]) {
            case "M":
                v = Integer.parseInt(line[2]);
                union(u, v);
                break;
            default:
                System.out.println(size[find(u)]);
                break;
            }            
        }
        scanner.close();
    }
}
