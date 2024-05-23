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

class Result {

    /*
     * Complete the 'shortPalindrome' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int shortPalindrome(String s) {
        final int P = 1_000_000_007;
        int[] dp = new int[26];
        int[][] dp2 = new int[26][26];
        int[][] dp3 = new int[26][26];
        int dp4 = 0;
    
        for(char x : s.toCharArray()) {
            int z = x - 'a';
            for(char c = 0; c < 26; c++) {
                dp4 += dp3[z][c];
                dp4 %= P;
                
                dp3[c][z] += dp2[c][z];
                dp3[c][z] %= P;
                
                dp2[c][z] += dp[c];
                dp2[c][z] %= P; 
            }
            dp[z]++;
            dp[z] %= P;
        }
        return dp4;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        int result = Result.shortPalindrome(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

