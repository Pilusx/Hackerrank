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

class Point {
    int x, y;
    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Result {
    private static List<Point> neighbours(Point p, int n, int m) {
        List<Point> result = new ArrayList<>();
        if(p.x > 0) result.add(new Point(p.x-1, p.y));
        if(p.y > 0) result.add(new Point(p.x, p.y-1));
        if(p.x < n-1) result.add(new Point(p.x+1, p.y));
        if(p.y < m-1) result.add(new Point(p.x, p.y+1));
        return result;
    }


    /*
     * Complete the 'countLuck' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING_ARRAY matrix
     *  2. INTEGER k
     */

    public static String countLuck(List<String> matrix, int k) {
        int n = matrix.size();
        int m = matrix.get(0).length();
        
        boolean[][] decision = new boolean[n][m];
        int[][] depth = new int[n][m];
        Queue<Point> queue = new LinkedList<>();
        
        Point start = null, end = null;
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                char c = matrix.get(i).charAt(j);
                if(c == 'M') start = new Point(i, j);
                else if(c == '*') end = new Point(i, j);
            }
        }
        
        queue.add(start);
        depth[start.x][start.y] = 1;
        
        while(!queue.isEmpty()) {
            final Point p = queue.poll();
            List<Point> points = neighbours(p, n, m)
                    .stream()
                    .filter(r -> matrix.get(r.x).charAt(r.y) != 'X' && depth[r.x][r.y] == 0)
                    .collect(toList());
            if(points.size() > 1) decision[p.x][p.y] = true;
            points.forEach(r -> depth[r.x][r.y] = depth[p.x][p.y] + 1);
            queue.addAll(points);
        }
        
        
        int count = 0;
        while(depth[end.x][end.y] != 1) {
            final Point p = end;
            end = neighbours(p, n, m)
                    .stream()
                    .filter(r -> depth[r.x][r.y] == depth[p.x][p.y]-1)
                    .findFirst()
                    .orElseThrow(RuntimeException::new);
            if(decision[end.x][end.y]) count++;
        }
        
        if(count == k) return "Impressed";
        else return "Oops!";
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                List<String> matrix = IntStream.range(0, n).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                    .collect(toList());

                int k = Integer.parseInt(bufferedReader.readLine().trim());

                String result = Result.countLuck(matrix, k);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

