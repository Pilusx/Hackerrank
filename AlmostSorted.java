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
    /*
     * Complete the 'almostSorted' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static void almostSorted(List<Integer> arr) {
        List<Integer> a = new ArrayList<>(), changed = new ArrayList<>();
        a.addAll(arr);
        Collections.sort(a);
        for(int i = 0; i < arr.size(); i++) {
            if(a.get(i) != arr.get(i)) changed.add(i);
        }
        if(changed.size() == 2) {
            System.out.println("yes");
            System.out.println("swap " + (changed.get(0) + 1) + " " + (changed.get(1) + 1));
            return;
        }
        
        int start = -1, end = - 1;
        for(int i = 0; i + 1 < arr.size(); i++) {
            if(arr.get(i) > arr.get(i+1)) {
                if(start == -1) {
                    start = i;
                    end = i + 1;
                }
                if(end == i) end = i + 1;
            }
        }
        
        Collections.reverse(arr.subList(start, end+1));
        for(int i = 0; i < arr.size(); i++) {
            if(a.get(i) != arr.get(i)) {
                System.out.println("no");
                return;
            }
        }
        
        System.out.println("yes");
        System.out.println("reverse " + (start + 1) + " " + (end + 1));
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        Result.almostSorted(arr);

        bufferedReader.close();
    }
}

