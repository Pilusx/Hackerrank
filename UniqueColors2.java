import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;

class Visit {
    int id;
    Set<Integer> colors;
}

public class Solution {

    // Complete the solve function below.
    static int[] solve(int[] c, int[][] tree) {
        List<List<Integer>> edges = new ArrayList<>();
        for(int i = 0; i < c.length; i++)
            edges.add(new ArrayList<>());
        
        for(int[] edge : tree) {
            edges.get(edge[0]-1).add(edge[1]-1);
            edges.get(edge[1]-1).add(edge[0]-1);
        }
        
        int[] result = new int [c.length];
        boolean[] visited = new boolean[c.length];
        Queue<Visit> q = new LinkedList<>();
        
        for(int i = 0; i < c.length; i++) {
            Arrays.fill(visited, false);
            q.clear();

            Visit v = new Visit();
            v.id = i;
            v.colors = new HashSet<>();
            v.colors.add(c[i]);
            visited[i] = true;
            q.add(v);
            
            while(!q.isEmpty()) {
                Visit u = q.poll();
                result[i] += u.colors.size();
                
                for(int j : edges.get(u.id)) {
                    if(!visited[j]) {
                        visited[j] = true;
                        Visit nu = new Visit();
                        nu.id = j;
                        if(u.colors.contains(c[j])) nu.colors = u.colors;
                        else {
                            nu.colors = new HashSet<>(u.colors);
                            nu.colors.add(c[j]);
                        }
                        q.add(nu);
                    }
                }
            }
        }

        return result;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int cCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] c = new int[cCount];

        String[] cItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int cItr = 0; cItr < cCount; cItr++) {
            int cItem = Integer.parseInt(cItems[cItr]);
            c[cItr] = cItem;
        }

        int n = cCount;
        int[][] tree = new int[n-1][2];

        for (int treeRowItr = 0; treeRowItr < n-1; treeRowItr++) {
            String[] treeRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int treeColumnItr = 0; treeColumnItr < 2; treeColumnItr++) {
                int treeItem = Integer.parseInt(treeRowItems[treeColumnItr]);
                tree[treeRowItr][treeColumnItr] = treeItem;
            }
        }

        int[] result = solve(c, tree);

        for (int resultItr = 0; resultItr < result.length; resultItr++) {
            bufferedWriter.write(String.valueOf(result[resultItr]));

            if (resultItr != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

