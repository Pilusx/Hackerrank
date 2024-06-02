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
    static int op(int a, int b) {
        return ((a & b) ^ (a | b)) & (a ^ b);
    }

    /*
     * Complete the 'andXorOr' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY a as parameter.
     */

    public static int andXorOr(List<Integer> a) {
        int best = 0;
        Stack<Integer> stack = new Stack<>();
        for(int x : a) {
            while(!stack.isEmpty() && x <= stack.peek())
                best = Math.max(best, op(x, stack.pop()));
            if(!stack.isEmpty())
                best = Math.max(best, op(x, stack.peek()));
            stack.add(x);
        }
        return best;
    }
}


public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int aCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.andXorOr(a);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

