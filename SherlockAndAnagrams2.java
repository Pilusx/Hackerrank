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
    Map<Character, Integer> map = new HashMap<>();
    void add(Character c) {
        if(map.getOrDefault(c, 0) == -1) map.remove(c);
        else map.put(c, map.getOrDefault(c, 0) + 1);
    }
    void remove(Character c) {
        if(map.getOrDefault(c, 0) == 1) map.remove(c);
        else map.put(c, map.getOrDefault(c, 0) - 1);
    }
    boolean isAnagram() {
        return map.isEmpty();
    }
}

class Result {

    /*
     * Complete the 'sherlockAndAnagrams' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int sherlockAndAnagrams(String s) {
        int counter = 0;
        for(int i = 0; i < s.length(); i++) {
            for(int j = i; j < s.length(); j++) {
                for(int k = i + 1; k < s.length(); k++) {
                    Counter c = new Counter();
                    for(int l = i; l <= j; l++) {
                        c.add(s.charAt(l));
                    }
                    for(int l = k; l < s.length(); l++) {
                        c.remove(s.charAt(l));
                        if(c.isAnagram()) {
                            counter++;
                        }
                    }
                }
            }
        }
        return counter;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String s = bufferedReader.readLine();

                int result = Result.sherlockAndAnagrams(s);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

