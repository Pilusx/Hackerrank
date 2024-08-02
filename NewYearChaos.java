import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {
    // https://www.geeksforgeeks.org/inversion-count-in-array-using-merge-sort/
    public static int mergeAndCount(int[] array, int start, int mid, int end) {
        int[] left = Arrays.copyOfRange(array, start, mid+1);
        int[] right = Arrays.copyOfRange(array, mid+1, end+1);
        
        int i = 0, j = 0, k = start, swaps = 0;
        while(i < left.length && j < right.length) {
            if(left[i] < right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
                swaps += (mid + 1) - (start + i);
            }
        }
        
        while(i < left.length) array[k++] = left[i++];
        while(j < right.length) array[k++] = right[j++];
        return swaps;
    }
    
    
    public static int mergeSortAndCount(int[] array, int start, int end) {
        int count = 0;
        if(start < end) {
            int mid = (start + end) / 2;
            
            count += mergeSortAndCount(array, start, mid);
            count += mergeSortAndCount(array, mid+1, end);
            count += mergeAndCount(array, start, mid, end);
        }
        return count;
    }

    /*
     * Complete the 'minimumBribes' function below.
     *
     * The function accepts INTEGER_ARRAY q as parameter.
     */

    public static void minimumBribes(List<Integer> q) {
    // Write your code here
        int[] array = new int[q.size()];
        int i = 0;
        for(int x : q){
            array[i++] = x;
        }
        for(i = 0; i < array.length; i++) {
            if(array[i] > i + 1) {
                if(array[i] - i - 1 > 2) {
                    System.out.println("Too chaotic");
                    return;
                }
            }
        }
        System.out.println(mergeSortAndCount(array, 0, array.length - 1));
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        for (int tItr = 0; tItr < t; tItr++) {
            int n = Integer.parseInt(bufferedReader.readLine().trim());

            String[] qTemp = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> q = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                int qItem = Integer.parseInt(qTemp[i]);
                q.add(qItem);
            }

            Result.minimumBribes(q);
        }

        bufferedReader.close();
    }
}

