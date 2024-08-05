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
    UL(-2, -1, "UL"),
    UR(-2, 1, "UR"),
    R(0, 2, "R"),
    LR(2, 1, "LR"),
    LL(2, -1, "LL"),
    L(0, -2, "L");
    
    int dx, dy;
    String name;
    
    private Direction(int dx, int dy, String name) {
        this.dx = dx;
        this.dy = dy;
        this.name = name;
    }
}

class Point {
    int x, y, depth;
    Point last = null;
    Direction lastMove = null;

    public Point(int x, int y, int depth, Point last, Direction move) {
        this.x = x;
        this.y = y;
        this.depth = depth;
        this.last = last;
        this.lastMove = move;
    }
}


class Result {

    /*
     * Complete the 'printShortestPath' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER i_start
     *  3. INTEGER j_start
     *  4. INTEGER i_end
     *  5. INTEGER j_end
     */

    public static void printShortestPath(int n, int i_start, int j_start, int i_end, int j_end) {
    // Print the distance along with the sequence of moves.
        Point[][] points = new Point[n][n];
        
        Queue<Point> queue = new LinkedList<>();
        points[i_start][j_start] = new Point(i_start, j_start, 0, null, null);
        queue.add(points[i_start][j_start]);
        
        while(!queue.isEmpty()) {
            Point p = queue.poll();
            for(Direction dir : Direction.values()) {
                int x = p.x + dir.dx;
                int y = p.y + dir.dy;
                if(0 <= x && x < n && 0 <= y && y < n && points[x][y] == null) {
                    points[x][y] = new Point(x, y, p.depth + 1, p, dir);
                    queue.add(points[x][y]);
                }
            }
        }
        
        if(points[i_end][j_end] == null) 
            System.out.println("Impossible");
        else {
            List<Direction> directions = new ArrayList<>();
            Point p = points[i_end][j_end];
            while(p.lastMove != null) {
                directions.add(p.lastMove);
                p = p.last;
            }
            System.out.println(directions.size());
            Collections.reverse(directions);
            directions.forEach(d -> System.out.printf(d.name + " "));
        }
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int i_start = Integer.parseInt(firstMultipleInput[0]);

        int j_start = Integer.parseInt(firstMultipleInput[1]);

        int i_end = Integer.parseInt(firstMultipleInput[2]);

        int j_end = Integer.parseInt(firstMultipleInput[3]);

        Result.printShortestPath(n, i_start, j_start, i_end, j_end);

        bufferedReader.close();
    }
}

