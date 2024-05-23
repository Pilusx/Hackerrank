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

class Counter {
    private static final int P = 1_000_000_007;
    TreeMap<Integer, Integer>[] positions = new TreeMap[26];
    
    Counter(String s) {
        for(int i = 0; i < 26; i++) {
            positions[i] = new TreeMap<>();
        }
        for(int i = 0; i < s.length(); i++) {
            TreeMap<Integer, Integer> t = positions[s.charAt(i) - 'a'];
            t.put(i, t.size() + 1);
        }
        
    }
    
    private int query(int a, int b, int c) {
        TreeMap<Integer, Integer> t = positions[c];
        Map.Entry<Integer, Integer> u = t.higherEntry(a);
        Integer u_ = u == null ? t.size() : u.getValue();
        Map.Entry<Integer, Integer> v = t.lowerEntry(b);
        Integer v_ = v == null ? 0 : v.getValue();
        return v_ - u_ + 1;
    }
    
    int query() {
        long result = 0;
        for(int c = 0; c < 26; c++) {
            TreeMap<Integer, Integer> t = positions[c];
            for(int i : t.keySet()) {
                for(int j : t.keySet()) {
                    if (i >= j) continue;
                    for(int c2 = 0; c2 < 26; c2++) {
                        long z = query(i, j, c2);
                        result += z * (z-1) / 2;
                        result %= P;
                    }
                }
            }
        }
        return (int)result;
    }
}

class Result {

    /*
     * Complete the 'shortPalindrome' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int shortPalindrome(String s) {
        return new Counter(s).query();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        int result = Result.shortPalindrome(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

