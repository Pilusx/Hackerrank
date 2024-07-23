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
     * Complete the 'isValid' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String isValid(String s) {
        int[] freq = new int[26];
        for(char c : s.toCharArray()) freq[c-'a']++;
        
        Map<Integer, Integer> map = new TreeMap<>();
        for(int x : freq) 
            if(x > 0) map.merge(x, 1, Integer::sum);
        
        if(map.size() < 2) return "YES";
        else if (map.size() > 2) return "NO";
        else {
            List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(map.entrySet()); 
            if((entries.get(0).getKey() == 1 && entries.get(0).getValue() == 1)
            || (entries.get(1).getKey() == entries.get(0).getKey() + 1 && entries.get(1).getValue() == 1))
                return "YES";
            else return "NO";
        }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        String result = Result.isValid(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

