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

enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1);

    public int dx, dy;
    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

class Cross {
    int x, y, size;
    Cross(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }
}

class Result {

    /*
     * Complete the 'twoPluses' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING_ARRAY grid as parameter.
     */

    public static int twoPluses(List<String> grid) {
        int n = grid.size(), m = grid.get(0).length();
        List<Cross> crosses = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                int k = 0;
loop:           for(; k < Math.min(n, m); k++) {
                    for(Direction dir : Direction.values()) {
                        int x = i + k * dir.dx;
                        int y = j + k * dir.dy;
                        if(x < 0 || x >= n 
                            || y < 0 || y >= m
                            || grid.get(x).charAt(y) == 'B') break loop;
                        if(k == 0) continue loop;
                    }
                }
                if(k > 0) crosses.add(new Cross(i, j, k-1));
            }
        }
        
        int result = 0;
        for(Cross c1 : crosses) {
            Set<Map.Entry<Integer, Integer>> points = new HashSet<>();
            for(int k = 0; k <= c1.size; k++) {
                for(Direction dir : Direction.values()) {
                    points.add(new AbstractMap.SimpleEntry<>(c1.x + k * dir.dx, c1.y + k * dir.dy));
                }
                
                for(Cross c2 : crosses) {   
                    int size = 0;
loop2:              for(; size <= c2.size; size++) {
                        for(Direction dir : Direction.values()) {
                            if(points.contains(new AbstractMap.SimpleEntry<>(c2.x + size * dir.dx, c2.y + size * dir.dy))) {
                                break loop2;                                              
                            }
                        }
                    }
                    size--;
                    result = Math.max(result, (4 * k + 1) * (4 * size + 1));
                }   
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

        List<String> grid = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        int result = Result.twoPluses(grid);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

