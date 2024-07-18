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
     * Complete the 'insertionSort1' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER_ARRAY arr
     */

    public static void insertionSort1(int n, List<Integer> arr) {
loop:   while(true) {
            int i = n-2;
            int temp = arr.get(n-1);
            for(; i >= -1; i--) {
                if(i >= 0 && arr.get(i) > temp) {
                    arr.set(i+1, arr.get(i));
                    printList(arr);
                } else if(i == n-2) {
                    break loop;
                } else {
                    arr.set(i+1, temp);
                    printList(arr);
                    break;
                }
            }
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

        Result.insertionSort1(n, arr);

        bufferedReader.close();
    }
}

