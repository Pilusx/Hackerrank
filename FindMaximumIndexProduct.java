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
        Stack<Integer> decreasing = new Stack<>();
        int[] left = new int[arr.size()], right = new int[arr.size()];
        decreasing.add(0);
        for(int i = 1; i < arr.size();i++) {
            while(!decreasing.isEmpty() && arr.get(i) >= arr.get(decreasing.peek()))
                decreasing.pop();
            if(!decreasing.isEmpty()) left[i] = decreasing.peek()+1;
            decreasing.add(i);
        }
        
        decreasing.clear();
        decreasing.add(arr.size()-1);
        for(int i = arr.size() - 2; i >= 0; i--) {
            while(!decreasing.isEmpty() && arr.get(i) >= arr.get(decreasing.peek()))
                decreasing.pop();
            if(!decreasing.isEmpty()) right[i] = decreasing.peek()+1;
            decreasing.add(i);
        }
        long max = 0;
        for(int i = 0; i < arr.size(); i++)
            max = Math.max(max, (long) left[i] * right[i]);
        return max;
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

