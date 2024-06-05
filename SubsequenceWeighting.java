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

class MaxTree {
    int K = 1 << 30;
    HashMap<Integer, Long> max = new HashMap<>();
    
    void update(int x, long w) {
        x += K;
        max.merge(x, w, Long::max);
        
        while(x > 0) {
            max.merge(x, Long.max(
                max.getOrDefault(2*x, 0L),
                max.getOrDefault(2*x+1, 0L)
            ), Long::max);
            x /= 2;
        }
    }
    
    long max(int a, int b) {
        a += K;
        b += K;
        
        long result = Long.max(max.getOrDefault(a, 0L),
                               max.getOrDefault(b, 0L));
        a /= 2;
        b /= 2;
        while(a + 1 < b) {
            if(a % 2 == 0)
                result = Long.max(result, max.getOrDefault(a+1, 0L));
            if(b % 2 == 1)
                result = Long.max(result, max.getOrDefault(b-1, 0L));

            a /= 2;
            b /= 2;
        } 
        return result;        
    }
    
}

class Result {

    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY a
     *  2. INTEGER_ARRAY w
     */

    public static long solve(List<Integer> a, List<Integer> w) {
    // Write your code here
        MaxTree tree = new MaxTree();
        for(int i = 0; i < a.size(); i++) {
            long currentWeight = tree.max(0, a.get(i) - 1);
            tree.update(a.get(i), currentWeight + w.get(i));
        }
        return tree.max(0, 1_000_000_000);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                List<Integer> w = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                long result = Result.solve(a, w);

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

