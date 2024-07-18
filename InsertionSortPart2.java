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
    private static void printList(List<Integer> arr) {
        for(int x : arr) System.out.print(x + " ");
        System.out.println();
    }

    /*
     * Complete the 'insertionSort2' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY arr
     */

    public static void insertionSort2(int n, List<Integer> arr) {
        for(int i = 1; i < n; i++) {
            int temp = arr.get(i);
            int j = i - 1;
            for(; j >= - 1; j--) {
                if(j >= 0 && arr.get(j) > temp) {
                    arr.set(j+1, arr.get(j));
                } else {
                    arr.set(j+1, temp);
                    break;
                }
            }    
            printList(arr);
        }

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        Result.insertionSort2(n, arr);

        bufferedReader.close();
    }
}

