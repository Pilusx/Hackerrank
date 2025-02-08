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
     * Complete the 'luckBalance' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER k
     *  2. 2D_INTEGER_ARRAY contests
     */

    public static List<Integer> filterImportance(List<List<Integer>> contests, int important) {
        return contests
            .stream()
            .filter((x) -> x.get(1) == important)
            .map((x) -> x.get(0))
            .collect(toList());
    }

    public static int luckBalance(int k, List<List<Integer>> contests) {
        List<Integer> unimportant = filterImportance(contests, 0);
        List<Integer> important = filterImportance(contests, 1);
        Collections.sort(important, (x, y) -> y.compareTo(x));
        int result = unimportant.stream().mapToInt(Integer::valueOf).sum();
        for(int i = 0; i < important.size(); i++) {
            if(i < k) result += important.get(i);
            else result -= important.get(i);
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> contests = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                contests.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int result = Result.luckBalance(k, contests);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

