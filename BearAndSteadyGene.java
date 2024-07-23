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
    private static boolean caterpillar(int[] current) { 
        for(int i = 0; i < 4; i++)
            if(current[i] < 0) return false;
        
        return true;
    }

    /*
     * Complete the 'steadyGene' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING gene as parameter.
     */

    public static int steadyGene(String gene) {
        int[][] sum = new int[gene.length()+1][4];
        int n = gene.length();
        int result = n;
        
        for(int i = 0; i < gene.length(); i++) {
            sum[i+1] = Arrays.copyOf(sum[i], 4);
            int pos = switch(gene.charAt(i)) {
                case 'A' -> 0;
                case 'C' -> 1;
                case 'G' -> 2;
                case 'T' -> 3;
                default -> 0;
            };
            sum[i+1][pos]++;
        }
        
        int[] diff = Arrays.copyOf(sum[n], 4);
        for(int i = 0; i < 4; i++) diff[i] -= n/4;
        if(caterpillar(diff)) return 0;
        
        int i = 1, j = 1;
        while(i <= n) {
            int[] current = Arrays.copyOf(sum[j], 4);
            for(int k = 0; k < 4; k++) current[k] -= sum[i-1][k] + diff[k];
            
            if(caterpillar(current)) {
                result = Math.min(result, j - i + 1);
                i++;
            } else if(j == n) i++;
            else j++;
        }
        
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        String gene = bufferedReader.readLine();

        int result = Result.steadyGene(gene);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

