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
    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

class Visit {
    public int x, y, move;
    public Direction dir;
    
    Visit(int x, int y, int move, Direction dir) {
        this.x = x;
        this.y = y;
        this.move = move;
        this.dir = dir;
    }
}

class Result {

    /*
     * Complete the 'minimumMoves' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING_ARRAY grid
     *  2. INTEGER startX
     *  3. INTEGER startY
     *  4. INTEGER goalX
     *  5. INTEGER goalY
     */

    public static int minimumMoves(List<String> grid, int startX, int startY, int goalX, int goalY) {
        final int n = grid.size();
        final int m = grid.get(0).length();
        int[][] distance = new int[n][m];
        for(int i = 0; i < n; i++) Arrays.fill(distance[i], Integer.MAX_VALUE);
        distance[startX][startY] = 0;
        Queue<Visit> visits = new LinkedList<>();
        
        for(Direction dir : Direction.values())
            visits.add(new Visit(startX, startY, 1, dir));   

        while(!visits.isEmpty()) {
            Visit v = visits.poll();
            v.x += v.dir.dx;
            v.y += v.dir.dy;
            List<Direction> dirs = new ArrayList<>(Arrays.asList(Direction.values()));
            dirs.remove(v.dir);
            while(v.x >= 0 && v.y >= 0 && v.x < n && v.y < m
                && grid.get(v.x).charAt(v.y) == '.'
                && v.move <= distance[v.x][v.y]) {
                
                distance[v.x][v.y] = v.move;
                for(Direction dir : dirs)
                    visits.add(new Visit(v.x, v.y, v.move + 1, dir));
                
                v.x += v.dir.dx;
                v.y += v.dir.dy;
            }
        }
        
        return distance[goalX][goalY];
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

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int startX = Integer.parseInt(firstMultipleInput[0]);

        int startY = Integer.parseInt(firstMultipleInput[1]);

        int goalX = Integer.parseInt(firstMultipleInput[2]);

        int goalY = Integer.parseInt(firstMultipleInput[3]);

        int result = Result.minimumMoves(grid, startX, startY, goalX, goalY);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

