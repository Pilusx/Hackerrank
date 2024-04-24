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

    public static long dfs(int i, int cost, List<List<Integer>> s, Set<Integer> set) {
        if(i == 9) {
            Set<Integer> sums = new HashSet<>();
            for(int k = 0; k < 3; k++) {
                sums.add(s.get(k).get(0) + s.get(k).get(1) + s.get(k).get(2));
                sums.add(s.get(0).get(k) + s.get(1).get(k) + s.get(2).get(k));
            }
            sums.add(s.get(0).get(0) + s.get(1).get(1) + s.get(2).get(2));
            sums.add(s.get(0).get(2) + s.get(1).get(1) + s.get(2).get(0));
            // if(sums.size() == 1) System.out.println(Arrays.toString(s.toArray()) + " " + cost);
            if(sums.size() == 1) return cost;
            else return Integer.MAX_VALUE;
        }
        long min_cost = Integer.MAX_VALUE;
        Set<Integer> nset = new HashSet<>();
        for(int u : set) nset.add(u);
        for(int u : nset) {
            int x = i % 3;
            int y = i / 3;
            int temp = s.get(x).get(y);
            s.get(x).set(y, u);
            set.remove(u);
            min_cost = Math.min(min_cost, dfs(i+1, cost + Math.abs(u - temp), s, set));
            set.add(u);
            s.get(x).set(y, temp);
        }
        return min_cost;
    }

    /*
     * Complete the 'formingMagicSquare' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY s as parameter.
     */

    public static int formingMagicSquare(List<List<Integer>> s) {
        Set<Integer> set = new HashSet<>();
        for(int i = 1; i <= 9; i++) set.add(i);
        return (int)dfs(0, 0, s, set);

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        List<List<Integer>> s = new ArrayList<>();

        IntStream.range(0, 3).forEach(i -> {
            try {
                s.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.formingMagicSquare(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

