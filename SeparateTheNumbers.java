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
     * Complete the 'separateNumbers' function below.
     *
     * The function accepts STRING s as parameter.
     */

    public static void separateNumbers(String s) {
    // Write your code here
        for(int i = 1; i < s.length(); i++) {
            long x = 0;
            try {
                x = Long.parseLong(s.substring(0, i));
            } catch(Exception ex) {
                continue;
            }
            long temp = x;
            StringBuilder result = new StringBuilder();
            result.append(x);
            while(result.length() < s.length()) {
                result.append(++x);
            }
            if(result.toString().equals(s)) {
                System.out.println("YES " + temp);
                return;
            }
        }
        System.out.println("NO");
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String s = bufferedReader.readLine();

                Result.separateNumbers(s);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
    }
}

