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
     * Complete the 'chiefHopper' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */
     
    public static boolean simulate(List<Integer> arr, int energy) {
        BigInteger e = BigInteger.valueOf(energy);
        for(int height : arr) {
            e = e.multiply(BigInteger.valueOf(2))
                 .subtract(BigInteger.valueOf(height));
            if(e.compareTo(BigInteger.ZERO) < 0) return false;
        }
        return true;
    }
    
    public static int binarySearch(int start, int end, List<Integer> arr, 
        BiFunction<List<Integer>,Integer, Boolean> predicate) {
        while(start < end) {
            int mid = (start + end) / 2;
            boolean p = predicate.apply(arr, mid);
            if(!p) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    public static int chiefHopper(List<Integer> arr) {
        return binarySearch(1, 100_000, arr, Result::simulate);

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.chiefHopper(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

