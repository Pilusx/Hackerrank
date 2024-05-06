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

class Graph {
    int[][] distance;
    int n;

    Graph(int n) {
        this.n = n;
        this.distance = new int[n][n];
        for(int i = 0; i < n; i++) {
            Arrays.fill(this.distance[i], Integer.MAX_VALUE);
            this.distance[i][i] = 0;
        }
    }
    
    void addEdge(int u, int v, int w) {
        distance[u][v] = w;
    }
    
    void floydWarshall() {
        for(int i = 0; i < n; i++) {
            for(int u = 0; u < n; u++) {
                for(int v = 0; v < n; v++) {
                    long d = (long)distance[u][i] + distance[i][v];
                    distance[u][v] = (int)Math.min((long)distance[u][v], d);
                }
            }
        }
        
    }
    
    int query(int u, int v) {
       return distance[u][v] != Integer.MAX_VALUE ? distance[u][v] : - 1; 
    }
    
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] roadNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int roadNodes = Integer.parseInt(roadNodesEdges[0]);
        int roadEdges = Integer.parseInt(roadNodesEdges[1]);

        Graph g = new Graph(roadNodes);

        List<Integer> roadFrom = new ArrayList<>();
        List<Integer> roadTo = new ArrayList<>();
        List<Integer> roadWeight = new ArrayList<>();

        IntStream.range(0, roadEdges).forEach(i -> {
            try {
                String[] roadFromToWeight = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                roadFrom.add(Integer.parseInt(roadFromToWeight[0]));
                roadTo.add(Integer.parseInt(roadFromToWeight[1]));
                roadWeight.add(Integer.parseInt(roadFromToWeight[2]));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        
        IntStream.range(0, roadEdges).forEach(i -> 
            g.addEdge(roadFrom.get(i)-1, roadTo.get(i)-1, roadWeight.get(i)));

        int q = Integer.parseInt(bufferedReader.readLine().trim());
        
        g.floydWarshall();

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int x = Integer.parseInt(firstMultipleInput[0]);

                int y = Integer.parseInt(firstMultipleInput[1]);
                
                System.out.println(g.query(x-1, y-1));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
    }
}


