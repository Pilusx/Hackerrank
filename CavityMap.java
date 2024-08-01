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
     * Complete the 'cavityMap' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts STRING_ARRAY grid as parameter.
     */

    public static List<String> cavityMap(List<String> grid) {
        int n = grid.size();
        List<String> result = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            if(i == 0 || i == n-1) result.add(grid.get(i));
            else {
                StringBuilder builder = new StringBuilder();
                builder.append(grid.get(i).charAt(0));
                for(int j = 1; j < n-1; j++) {
                    char c = grid.get(i).charAt(j);
                    if(c > grid.get(i).charAt(j-1)
                        && c > grid.get(i).charAt(j+1)
                        && c > grid.get(i-1).charAt(j)
                        && c > grid.get(i+1).charAt(j))
                        builder.append('X');
                    else builder.append(c);
                }
                if(n > 1) builder.append(grid.get(i).charAt(n-1));
                result.add(builder.toString());
            }
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> grid = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        List<String> result = Result.cavityMap(grid);

        bufferedWriter.write(
            result.stream()
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

