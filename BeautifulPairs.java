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
     * Complete the 'beautifulPairs' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY A
     *  2. INTEGER_ARRAY B
     */

    public static int beautifulPairs(List<Integer> A, List<Integer> B) {
    // Write your code here
        int[] freqA = new int[1001];
        for(int x : A) freqA[x]++;
        
        int paired = 0;
        int remaining = 0;
        for(int x : B) {
            if(freqA[x] > 0) {
                freqA[x]--;
                paired++;
            }
            else remaining = x;
        }
        // We used all values from B.
        if (remaining == 0) {
            // We search for a paired x such than there exists an unpaired value in A.
            for(int x : B) {
                if(A.size() - paired - freqA[x] > 0) return paired;
            }
            // We have to destroy one pair...
            return paired - 1;
        } else {
            // We can change the remaining value to some other value.
            if(A.size() > paired) return paired + 1;
            // All values in A are unfortunately paired...
            return paired;
        }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> A = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> B = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.beautifulPairs(A, B);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

