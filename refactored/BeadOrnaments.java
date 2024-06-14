/* Based on the editorial. */

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
    private static int N = 1_000_000_007;

    private static int sum(int ... arr) {
        int result = 0;
        for(int x : arr) result = (result + x) % N;
        return result;
    }
    
    private static int multiply(int... arr) {
        long result = 1;
        for(int x : arr) result = (result * x) % N;
        return (int)result;
    }
    
    private static int pow(int x, int n) {
        int temp = x;
        int result = 1;
        while(n > 0) {
            if(n % 2 == 1) result = multiply(result, temp);
            temp = multiply(temp, temp);
            n /= 2;
        }
        return result;
    }
    
    private static int cayleys(int n) {
        if(n <= 2) return 1;
        else return pow(n, n-2);
    }

    /*
     * Complete the 'beadOrnaments' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY b as parameter.
     */

    public static int beadOrnaments(List<Integer> b) {
        int n = b.size();
        if(n == 1) {
            return cayleys(b.get(0));
        } else {
            int result = pow(b.stream().reduce(0, Result::sum), n-2);
            for(int x : b) result = multiply(result, x, cayleys(x));
            return result;
        }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int bCount = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> b = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                int result = Result.beadOrnaments(b);

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

