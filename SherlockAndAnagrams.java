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

class Counter<T> {
    Map<T, Integer> map = new HashMap<>();
    void add(T c) {
        if(map.getOrDefault(c, 0) == -1) map.remove(c);
        else map.put(c, map.getOrDefault(c, 0) + 1);
    }
    void remove(T c) {
        if(map.getOrDefault(c, 0) == 1) map.remove(c);
        else map.put(c, map.getOrDefault(c, 0) - 1);
    }
}

class Signature {
    int[] sig = new int[26];
    Signature(String s) {
        for(char c : s.toCharArray()) {
            sig[c - 'a']++;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if(!(o instanceof Signature)) return false;
    
        Signature other = (Signature)o;
        for(int i = 0; i < sig.length; i++) {
            if(sig[i] != other.sig[i]) return false;
        }
        return true;
    }

    public int hashCode() {
        int result = 0;
        for(int i = 0; i < sig.length; i++) {
            result = 31 * result + sig[i];
        }
        return result;
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
        Counter<Signature> c = new Counter<>();
        for(int i = 0; i < s.length(); i++) {
            for(int j = i; j < s.length(); j++) {
                Signature sig = new Signature(s.substring(i, j+1));
                c.add(sig);
            }
        }
        for(Map.Entry<Signature, Integer> entry : c.map.entrySet()) {
            int n = entry.getValue();
            counter += n * (n-1) / 2;
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

