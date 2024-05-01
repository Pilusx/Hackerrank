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
    private static int[] parent, size;

    /*
     * Complete the 'lilysHomework' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */
     
    private static int find(int u) {
        if(u != parent[u]) parent[u] = find(parent[u]);
        return parent[u];
    }
     
    private static void union(int u, int v) {
        int pu = find(u);
        int pv = find(v);
        if(pu != pv) {
            if(size[pu] > size[pv]) {
                size[pu] += size[pv];
                parent[pv] = pu; 
            } else {
                size[pv] += size[pu];
                parent[pu] = pv;
            }
        }
    }
    
    private static int calculateSwaps(List<Integer> arr, 
            List<Map.Entry<Integer, Integer>> entries) {
        parent = new int[arr.size()];
        size = new int[arr.size()];
        Arrays.fill(size, 1);
        for(int i = 0; i < parent.length; i++) parent[i] = i;
    
        for(int i = 0; i < arr.size(); i++) {
            if(entries.get(i).getKey() != arr.get(i)) 
                union(i, entries.get(i).getValue());
        }

        int swaps = 0;
        for(int i = 0; i < size.length; i++) {
            if(find(i) == i) swaps += size[i] - 1;
        }
        return swaps;
    }

    public static int lilysHomework(List<Integer> arr) {
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>();
        for(int i = 0; i < arr.size(); i++) {
            entries.add(new AbstractMap.SimpleEntry<Integer, Integer>(arr.get(i), i));
        }
        Collections.sort(entries, (x, y) -> x.getKey().compareTo(y.getKey()));
        int swaps1 = calculateSwaps(arr, entries);
        Collections.reverse(entries);
        int swaps2 = calculateSwaps(arr, entries);
        return Math.min(swaps1, swaps2);

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.lilysHomework(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

