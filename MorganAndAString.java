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
    private static List<String> split(String s) {
        List<String> result = new ArrayList<>();
        
        for(int i = 0; i < s.length(); i++) {
            char c = 'Z' + 1;
            int u = i;
            while(u < s.length() && c >= s.charAt(u))
                c = s.charAt(u++);
            result.add(s.substring(i, u));
            i = u-1;
        }
        return result;
    }
    
    private static int compare(String u, String v) {
        for(int i = 0; i < Math.min(u.length(), v.length()); i++) {
            int cmp = Character.compare(u.charAt(i), v.charAt(i));
            if(cmp != 0) return cmp;
        }
        return Integer.compare(v.length(), u.length());
    }
    
    private static int compare(List<String> A, List<String> B) {
        for(int i = 0; i < Math.min(A.size(), B.size()); i++) {
            int cmp = compare(A.get(i), B.get(i));
            if(cmp != 0) return cmp;
        }
        return Integer.compare(B.size(), A.size());
    }
    
    /*
     * Complete the 'morganAndString' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING a
     *  2. STRING b
     */

    public static String morganAndString(String a, String b) {
        List<String> A = split(a), B = split(b);
    
        int i = 0, j = 0;
        StringBuilder result = new StringBuilder();
        
        while(i < A.size() && j < B.size()) {
            int cmp = compare(A.subList(i, A.size()), B.subList(j, B.size()));
            if(cmp <= 0) result.append(A.get(i++));
            else result.append(B.get(j++));
        }
        
        while(i < A.size()) result.append(A.get(i++));
        while(j < B.size()) result.append(B.get(j++));
            
        return result.toString();
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String a = bufferedReader.readLine();

                String b = bufferedReader.readLine();

                String result = Result.morganAndString(a, b);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

