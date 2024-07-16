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
    public static void swap3(int i, int j, int k, int[] a) {
        int temp = a[k];
        a[k] = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
    /*
     * Complete the 'larrysArray' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts INTEGER_ARRAY A as parameter.
     */

    public static String larrysArray(List<Integer> A) {
        int[] arr = A.stream().mapToInt(Integer::valueOf).toArray();
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == i + 1) continue;
            for(int j = i + 1; j < arr.length; j++) {
                if(arr[j] == i + 1) {
                    if(j - i > 1) {
                        swap3(i, i+1, j, arr);
                        swap3(i, i+1, j, arr);
                    }
                    else if(j + 1 == arr.length) return "NO";
                    else swap3(i, j, arr.length-1, arr);
                    break;
                }
            }
        }
        return "YES";
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

                List<Integer> A = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                String result = Result.larrysArray(A);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

