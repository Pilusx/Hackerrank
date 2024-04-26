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

class Score {
    long score;
    int[] array;
    Score() {}
}

class Result {

    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */
    public static Score mergeList(List<Integer> arr, int low, int high) {
        if(low >= high) {
            Score nscore = new Score();
            nscore.score = 0;
            nscore.array = new int[0];
            return nscore;
        }
        int minInd = low;
        int maxInd = low;
        for(int i = low + 1; i < high; i++) {
            if(arr.get(i) > arr.get(maxInd)) maxInd = i;
            else if(arr.get(i) == arr.get(maxInd) && Math.abs((high + low) - 2 * i) < Math.abs((high +low) - 2 *  maxInd)) maxInd = i;
            if(arr.get(i) < arr.get(minInd)) minInd = i;
        }
        int minValue = arr.get(minInd);
        int maxValue = arr.get(maxInd);
        if((long) minValue * minValue > maxValue) {
            Score nscore = new Score();
            nscore.score = 0;
            nscore.array = new int[high - low];
            for(int i = 0; low + i < high; i++) {
                nscore.array[i] = arr.get(low + i);
            }
            Arrays.sort(nscore.array);
            return nscore;
        }
        
        
        
        // System.out.println(low + " " + maxInd + " " + high);
        Score lscore = mergeList(arr, low, maxInd);
        Score rscore = mergeList(arr, maxInd + 1, high);
        long score = lscore.score + rscore.score;
        int[] left = lscore.array;
        int[] right = rscore.array;
    
        for(int i = 0; i < left.length; i++) {
            if(left[i] == 1) score++;
            else break;
        }
        for(int i = 0; i < right.length; i++) {
            if(right[i] == 1) score++;
            else break;
        }
        
        int j = 0;
        for(int i = 0; i < left.length; i++) {
            while(j < right.length && (long) left[i] * right[right.length - 1 - j] > maxValue) j++;
            int temp = right.length - j;
            score += temp;
        }
        
        // System.out.println(Arrays.toString(left) + " " + maxValue + " " + Arrays.toString(right) + " -> " + score);
        Score nscore = new Score();
        nscore.score = score;
        nscore.array = new int[high - low];
        
        int i =0, l = 0, r =0;
        for(; l < left.length && r < right.length;) {
            if(left[l] < right[r]) {
                nscore.array[i++] = left[l++];
            } else {
                nscore.array[i++] = right[r++];
            }
        }
        while(l < left.length) nscore.array[i++] = left[l++];
        while(r < right.length) nscore.array[i++] = right[r++];
        nscore.array[i] = maxValue;
        
        return nscore;
    }

    public static long solve(List<Integer> arr) {
    // Write your code here}
        return mergeList(arr, 0, arr.size()).score;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result.solve(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

