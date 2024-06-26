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
     * Complete the 'largestRectangle' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts INTEGER_ARRAY h as parameter.
     */

    public static long largestRectangle(List<Integer> h) {
        Stack<Map.Entry<Integer, Integer>> stack = new Stack<>();
        int pos = 0;
        long result = 0;
        for(int n : h) {
            int lastPos = pos;
            while(!stack.isEmpty() && n < stack.peek().getKey()) {
                Map.Entry<Integer,Integer> entry = stack.pop();
                lastPos = entry.getValue();
                result = Math.max(result, (long)entry.getKey() * (pos - entry.getValue()));
            }
            if(stack.isEmpty() || n > stack.peek().getKey()) 
                stack.add(new AbstractMap.SimpleEntry<>(n, lastPos));
            pos++;
        }
        while(!stack.isEmpty()) {
            Map.Entry<Integer,Integer> entry = stack.pop();
            result = Math.max(result, (long)entry.getKey() * (pos - entry.getValue()));
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> h = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result.largestRectangle(h);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

