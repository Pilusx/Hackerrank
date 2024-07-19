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
    static boolean isPalindrome(String s) {
        int n = s.length();
        for(int i = 0; 2 * i < n ; i++) {
            if(s.charAt(i) != s.charAt(n-i-1)) return false;
        }
        return true;
    }


    /*
     * Complete the 'palindromeIndex' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int palindromeIndex(String s) {
        int n = s.length();
        for(int i = 0; 2 * i < n; i++) {
            if(s.charAt(i) != s.charAt(n-i-1)) {
                if(isPalindrome(s.substring(0, i) + s.substring(i+1)))
                    return i;
                else if (isPalindrome(s.substring(0, n-i-1) + s.substring(n-i)))
                    return n-i-1;
                else
                    return -1;
            }
        }
        return -1;
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

                int result = Result.palindromeIndex(s);

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

