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
     * Complete the 'matrixRotation' function below.
     *
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY matrix
     *  2. INTEGER r
     */

    public static void matrixRotation(List<List<Integer>> matrix, int r) {
        int n = matrix.size(), m = matrix.get(0).size();
        int loops = Math.min(n, m) / 2;
        for(int i = 0; i < loops; i++) {
            List<Map.Entry<Integer, Integer>> start = new ArrayList<>();
            List<Integer> values = new ArrayList<>(), values2 = new ArrayList<>();
            for(int u = i; u <= n - i - 1; u++) {
                start.add(new AbstractMap.SimpleEntry<>(u, i));
                values.add(matrix.get(u).get(i));
            }
            for(int v = i + 1; v <= m - i - 1; v++) {
                start.add(new AbstractMap.SimpleEntry<>(n-i-1, v));
                values.add(matrix.get(n-i-1).get(v));
            }
            for(int u = n - i - 2; u >= i; u--) {
                start.add(new AbstractMap.SimpleEntry<>(u, m-i-1));
                values.add(matrix.get(u).get(m-i-1));
            }
            for(int v = m - i - 2; v >= i + 1; v--) {
                start.add(new AbstractMap.SimpleEntry<>(i, v));
                values.add(matrix.get(i).get(v));
            }
            
            int rotation = (values.size() - (r % values.size())) % values.size();
            values2.addAll(values.subList(rotation, values.size()));
            values2.addAll(values.subList(0, rotation));
            
            for(int j = 0; j < start.size(); j++)
                matrix.get(start.get(j).getKey()).set(start.get(j).getValue(), values2.get(j));
        }
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++)
                System.out.printf(matrix.get(i).get(j) + " ");
            System.out.println();
        }
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(firstMultipleInput[0]);

        int n = Integer.parseInt(firstMultipleInput[1]);

        int r = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> matrix = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                matrix.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Result.matrixRotation(matrix, r);

        bufferedReader.close();
    }
}

