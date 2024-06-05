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
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static long solve(List<Integer> arr) {
        Stack<Map.Entry<Integer, Integer>> decreasing = new Stack<>();
        long result = 0;
        for(int x : arr) {
            while(!decreasing.isEmpty() && decreasing.peek().getKey() < x)
                decreasing.pop();
            if(!decreasing.isEmpty() && decreasing.peek().getKey() == x) {
                int y = decreasing.peek().getValue();
                result += y;
                decreasing.peek().setValue(y + 1); 
            } else
                decreasing.add(new AbstractMap.SimpleEntry<Integer, Integer>(x, 1));
        }
        return 2*result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result.solve(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

