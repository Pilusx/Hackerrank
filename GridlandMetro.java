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

class Track implements Comparable<Track> {
    int row, start, end;
    
    Track(List<Integer> values) {
        row = values.get(0);
        start = Math.min(values.get(1), values.get(2));
        end = Math.max(values.get(1), values.get(2));
    }
    
    public int compareTo(Track other) {
        int cmp = Integer.compare(row, other.row);
        if(cmp != 0) return cmp;
        cmp = Integer.compare(start, other.start);
        if(cmp != 0) return cmp;
        return Integer.compare(end, other.end);
    }
}

class Result {

    /*
     * Complete the 'gridlandMetro' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER m
     *  3. INTEGER k
     *  4. 2D_INTEGER_ARRAY track
     */

    public static long gridlandMetro(int n, int m, int k, List<List<Integer>> track) {
        List<Track> tracks = track.stream().map(Track::new).sorted().collect(toList());
        tracks.add(new Track(Arrays.asList(n+1, 1, m)));
        long result = 0;
        int current_row = 1;
        int current_column = 0;
        for(Track t : tracks) {
            if(t.row > current_row) {
                result += m - current_column;
                current_row++;
                current_column = 0;
                result += (long)m * (t.row - current_row);
                current_row = t.row;
            }
            if(t.start > current_column) {
                result += t.start - current_column - 1;
                current_column = t.end;
            } else {
                current_column = Math.max(current_column, t.end);
            }
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

        int m = Integer.parseInt(firstMultipleInput[1]);

        int k = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> track = new ArrayList<>();

        IntStream.range(0, k).forEach(i -> {
            try {
                track.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        long result = Result.gridlandMetro(n, m, k, track);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

