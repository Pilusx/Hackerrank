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
     * Complete the 'minimumNumber' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. STRING password
     */

    public static int minimumNumber(int n, String password) {
    // Return the minimum number of characters to make the password strong
        Set<Character> lowercase = new HashSet<>();
        Set<Character> uppercase = new HashSet<>();
        Set<Character> digits = new HashSet<>();
        Set<Character> special = new HashSet<>();
        
        for(char c : password.toCharArray()) {
            if('a' <= c && c <= 'z') lowercase.add(c);
            else if('A' <= c && c <= 'Z') uppercase.add(c);
            else if('0' <= c && c <= '9') digits.add(c);
            else special.add(c);
        }
        
        int extra = 0;
        if(lowercase.isEmpty()) extra++;
        if(uppercase.isEmpty()) extra++;
        if(digits.isEmpty()) extra++;
        if(special.isEmpty()) extra++;
        extra = Math.max(extra, 6 - password.length());
        return extra;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        String password = bufferedReader.readLine();

        int answer = Result.minimumNumber(n, password);

        bufferedWriter.write(String.valueOf(answer));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

