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

class FreqTable {
    private int P = 1_000_000_007,
                K = 100_005;
    private int[][] freq;
    private int[] factorial = new int[K], 
                  inverseFactorial = new int[K];
    
    FreqTable(String s) {
       freq = new int[s.length()+1][26];
       for(int i = 0; i < s.length(); i++) {
           freq[i+1] = Arrays.copyOf(freq[i], 26);
           freq[i+1][s.charAt(i) -'a']++;
       }
       factorial[0] = 1;
       inverseFactorial[0] = 1;
       for(int i = 1; i < K; i++) {
           factorial[i] = (int)((long)factorial[i-1] * i % P);
           inverseFactorial[i] = power(factorial[i], P-2);
       }
    }
    
    private int power(int x, int p) {
        long result = 1;
        long temp = x;
        while(p > 0) {
            if(p % 2 == 1) result = (result * temp) % P;
            p /= 2;
            temp = (temp * temp) % P;
        }
        return (int)result;
    }
    
    private int multinomialCoefficient(int[] xs) {
        int n = IntStream.of(xs).sum();
        long result = factorial[n];
        for(int x : xs) result = (result * inverseFactorial[x]) % P;
        return (int)result;
    }
    
    int query(int l, int r) {
        int[] diff = new int[26];
        long result = 0;
        for(int i = 0; i < 26; i++) {
            diff[i] = freq[r][i] - freq[l-1][i];
            if(diff[i] % 2 == 1) {
                result++;
                diff[i]--;
            }
            diff[i]/=2;
        }
        result = (Math.max(result, 1L) * multinomialCoefficient(diff)) % P;
        return (int) result;
    }
    
}

class Result {
    private static FreqTable table;
    /*
     * Complete the 'initialize' function below.
     *
     * The function accepts STRING s as parameter.
     */

    public static void initialize(String s) {
    // This function is called once before all queries.
        table = new FreqTable(s);
    }

    /*
     * Complete the 'answerQuery' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER l
     *  2. INTEGER r
     */

    public static int answerQuery(int l, int r) {
    // Return the answer for this query modulo 1000000007.
        return table.query(l, r);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        Result.initialize(s);

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int l = Integer.parseInt(firstMultipleInput[0]);

                int r = Integer.parseInt(firstMultipleInput[1]);

                int result = Result.answerQuery(l, r);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

