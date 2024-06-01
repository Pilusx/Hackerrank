import java.io.*;
import java.util.*;

public class Solution {
    static int P = 1_000_000_007;
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
    
    static int subtract(int a, int b) {
        return (a - b + P) % P;
    }
    
    static int multiply(int... values) {
        long result = 1;
        for(int i = 0; i < values.length; i++) {
            result *= values[i];
            result %= P;
        }
        return (int) result;
    }
    
    static int power(int n, int p) {
        int res = 1;
        int temp = n;
        while(p > 0) {
            if(p % 2 == 1)
                res = multiply(res, temp);
            
            p /= 2;
            temp = multiply(temp, temp);
        }
        return res;
    }
    
    static int inverse(int n) {
        return power(n, P-2);
    }
    
    static int binom3(int n) {
        return multiply(n, n-1, n-2, inverse(6));
    }
    
    static int binom2(int n) {
        return multiply(n, n-1, inverse(2));
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        parent = new int[n+1];
        size = new int[n+1];
        for(int i = 0; i <= n; i++)
            parent[i] = i;
        Arrays.fill(size, 1);
        for(int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            char c = sc.nextLine().charAt(1);
            if(c != 'r')
                union(u, v);
        }
        
        int result = binom3(n);
        for(int i = 1; i <= n; i++) {
            if(find(i) == i) {
                result = subtract(result, binom3(size[i]));
                result = subtract(result, multiply(binom2(size[i]), n - size[i]));
            }
        }
        System.out.println(result);
    }
}

