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
    static long gcd(long a, long b) {
        while(b != 0) {
            long c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    /*
     * Complete the 'maximumValue' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts INTEGER_ARRAY a as parameter.
     */

    public static long maximumValue(List<Integer> a) {
        long best = 0;
        for(int i = 0; i < a.size(); i++) {
            long gcd = Math.abs(a.get(i));
            long sum = a.get(i);
            long max = a.get(i);
            for(int j = i + 1; j < a.size(); j++) {
                gcd = gcd(gcd, (long) Math.abs(a.get(j)));
                sum += a.get(j);
                max = Math.max(max, a.get(j));
                best = Math.max(best, gcd * (sum - max));
            }
        }
        return best;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result.maximumValue(a);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

