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
     * Complete the 'poisonousPlants' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY p as parameter.
     */
    public static int poisonousPlants(List<Integer> p) {
        Stack<Integer> decreasing = new Stack<>();
        Stack<Integer> increasing = new Stack<>();
        int[] depth = new int[p.size()];
        Arrays.fill(depth, Integer.MAX_VALUE);
        
        for(int i = 0; i < p.size(); i++) {
            int x = p.get(i);
            if(decreasing.isEmpty() || x <= p.get(decreasing.peek())) {
                increasing.clear();
                decreasing.add(i);
                depth[i] = 0;
            } else {
                int time = 1;
                while(!increasing.isEmpty()) {
                    int u = increasing.peek();
                    if(x <= p.get(u) || depth[u] < time) {
                        time = Math.max(time, depth[u] + 1);
                        increasing.pop();
                    } else break;
                }
                depth[i] = time;
                increasing.add(i);
            }
        } 
        
        return IntStream.of(depth).max().orElse(0);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> p = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.poisonousPlants(p);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

