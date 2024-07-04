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

class MinTree {
    static int logK = 17;
    static int K = 1 << logK;
    static int[] data = new int[2*K];

    MinTree(List<Integer> width) {
        for(int i = 0; i < width.size(); i++) data[K + i] = width.get(i);
        for(int i = K-1; i > 0; i--) data[i] = Math.min(data[2*i], data[2*i+1]);
    }
    
    int query(List<Integer> cs) {
        int u = cs.get(0), v = cs.get(1);
        u += K;
        v += K;
        int result = Math.min(data[u], data[v]);
        while(u + 1 < v) {
            if(u % 2 == 0) result = Math.min(result, data[u+1]);
            if(v % 2 == 1) result = Math.min(result, data[v-1]);
            u /= 2;
            v /= 2;
        }
        return result;
    }
    
}


class Result {

    /*
     * Complete the 'serviceLane' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. 2D_INTEGER_ARRAY cases
     */

    public static List<Integer> serviceLane(List<Integer> width, List<List<Integer>> cases) {
        MinTree tree = new MinTree(width);    
        return cases.stream().map(tree::query).collect(Collectors.toList());
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int t = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> width = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<List<Integer>> cases = new ArrayList<>();

        IntStream.range(0, t).forEach(i -> {
            try {
                cases.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result.serviceLane(width, cases);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

