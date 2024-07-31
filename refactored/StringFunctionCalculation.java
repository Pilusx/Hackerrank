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

class Triple implements Comparable<Triple> {
    int a, b, c;
    
    Triple() {}
    
    Triple(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public int compareTo(Triple other) {
        int cmp = Integer.compare(a, other.a);
        if(cmp != 0) return cmp;
        cmp = Integer.compare(b, other.b);
        if(cmp != 0) return cmp;
        return Integer.compare(c, other.c);
    }
}

class SuffixArray {
    int logL;
    int L;
    int[][] P;
    Triple[] M;
    int[] hist, a;
    
    SuffixArray(String s) {
        L = s.length();

        for(logL = 1; (1 << logL) < L; logL++);

        P = new int[logL+1][L];
        M = new Triple[L];
        a = new int[L];
        hist = new int[L-1];
          
        for (int i = 0; i < L; i++) P[0][i] = s.charAt(i);
        
        for (int skip = 1, level = 1; skip < L; skip *= 2, level++) {
            for (int i = 0; i < L; i++) {
                M[i] = new Triple(P[level-1][i], 
                                 i + skip < L ? P[level-1][i + skip] : Integer.MIN_VALUE, 
                                 i);
            }
            Arrays.sort(M);
            for(int i = 0; i < L; i++)
                P[level][M[i].c] = (i > 0 && M[i].a == M[i-1].a && M[i].b == M[i-1].b) 
                                 ? P[level][M[i-1].c] : i;
        }
            
        for(int i = 0; i < L; i++) a[P[P.length-1][i]] = i;
        for(int i = 0; i < L - 1; i++) hist[i] = longestCommonPrefix(a[i], a[i+1]);
    }
    
    private int longestCommonPrefix(int i, int j) {
        int len = 0;
        if (i == j) return L - i;
        for (int k = P.length - 1; k >= 0 && i < L && j < L; k--) {
            if (P[k][i] == P[k][j]) {
                i += 1 << k;
                j += 1 << k;
                len += 1 << k;
            }
        }
        return len;
    }

    int getMaxArea() {
        int n = hist.length;
        Stack<Integer> s = new Stack<>();
        int maxArea = 0;
        int i = 0;
        while (i < n) {
            if (s.empty() || hist[s.peek()] <= hist[i]) s.push(i++);
            else {
                int tp = s.peek();
                s.pop();
                int area = hist[tp] * (s.empty() ? i+1 : i - s.peek());
                maxArea = Math.max(maxArea, area);
            }
        }
        while(!s.empty()) {
            int tp = s.peek();
            s.pop();
            int area = hist[tp] * (s.empty() ? i+1 : i - s.peek());
            maxArea = Math.max(maxArea, area);
        }
        return maxArea;
    }
}

class Result {

    /*
     * Complete the 'maxValue' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING t as parameter.
     */

    public static int maxValue(String t) {
        int result = new SuffixArray(t).getMaxArea();
        return result < t.length() ? t.length() : result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String t = bufferedReader.readLine();

        int result = Result.maxValue(t);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

