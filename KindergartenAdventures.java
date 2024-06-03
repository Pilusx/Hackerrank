import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

class IntervalTree {
    static final int K = 1<<17;
    int[] data = new int[2 * K];
    
    void update(int i, int j, int v) {
        assert(i < j);
        i = K + i;
        j = K + j;
        
        data[i] += v;
        data[j] += v;
        
        while(i > 1) {
            if(i + 1 == j) break;
            if(i % 2 == 0) data[i+1] += v;
            if(j % 2 == 1) data[j-1] += v;
            i /= 2;
            j /= 2;
        }
    }
    
    void downPropagate() {
        for(int i = 1; i < K; i++) {
            data[2*i] += data[i];
            data[2*i+1] += data[i];
            data[i] = 0;
        }
    }
    
    int query(int i) {
        return data[K+i];
    }
}


public class Solution {
    /*
     * Complete the solve function below.
     */
    static int solve(int[] t) {
        /*
         * Return the ID
         */
         IntervalTree it = new IntervalTree();
         int n = t.length;
         for(int i = 0; i < n; i++) {
             if(t[i] == n) continue;
             if(i >= t[i]) {
                 it.update(0, i - t[i], 1);
                 it.update(i + 1, n - 1, 1);
             } else {
                 it.update(i + 1, n + i - t[i], 1);
             }
         }
         
         it.downPropagate();
         
         int bestInd = 0;
         int best = 0;
         for(int i = 0; i < n; i++) {
             int x = it.query(i);
             if(x > best) {
                 bestInd = i;
                 best = x;
             }
         }
         return bestInd + 1;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int tCount = Integer.parseInt(scanner.nextLine().trim());

        int[] t = new int[tCount];

        String[] tItems = scanner.nextLine().split(" ");

        for (int tItr = 0; tItr < tCount; tItr++) {
            int tItem = Integer.parseInt(tItems[tItr].trim());
            t[tItr] = tItem;
        }

        int id = solve(t);

        bufferedWriter.write(String.valueOf(id));
        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}

