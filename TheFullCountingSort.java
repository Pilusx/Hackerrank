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
     * Complete the 'countSort' function below.
     *
     * The function accepts 2D_STRING_ARRAY arr as parameter.
     */

    public static void countSort(List<List<String>> arr) {
        List<String>[] buckets = new List[100];
        for(int i = 0; i < 100; i++) buckets[i] = new ArrayList<>();
        
        int blanks = 0;
        for(List<String> strings : arr) {
            int position = Integer.parseInt(strings.get(0));
            buckets[position].add((blanks+=2) <= arr.size() ? "-" : strings.get(1));
        }
        
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 100; i++) {
            for(String x : buckets[i]) builder.append(x).append(" ");
        }
        System.out.println(builder.toString());
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> arr = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                arr.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Result.countSort(arr);

        bufferedReader.close();
    }
}

