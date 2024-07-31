import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

public class Solution {

    /*
     * Complete the beautifulQuadruples function below.
     */
    static long beautifulQuadruples(int a, int b, int c, int d) {
        /*
         * Write your code here.
         */
        List<Integer> limits = Arrays.asList(a, b, c, d);
        Collections.sort(limits);
        int n = 1 << 12;
        long[][] dp = null, dp2 = new long[n][n];
        for(int i = 0; i < n; i++) dp2[0][i] = 1;
        
        for(int limit : limits) {
            dp = dp2;
            dp2 = new long[n][n];
            for(int u = 1; u <= limit; u++) {
                for(int v = 0; v < n; v++) dp2[u^v][u] += dp[v][u];
            }
            for(int u = 0; u < n; u++) {
                for(int i = 1; i < n; i++) dp2[u][i] += dp2[u][i-1];
            }
        }
        long result = 0;
        for(int u = 1; u < n; u++) result += dp2[u][n-1];

        return result;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] abcd = scanner.nextLine().split(" ");

        int a = Integer.parseInt(abcd[0].trim());

        int b = Integer.parseInt(abcd[1].trim());

        int c = Integer.parseInt(abcd[2].trim());

        int d = Integer.parseInt(abcd[3].trim());

        long result = beautifulQuadruples(a, b, c, d);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}

