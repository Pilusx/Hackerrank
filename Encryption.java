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
     * Complete the 'encryption' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String encryption(String s) {
        s = s.replace(" ", "");
        int n = (int) Math.floor(Math.sqrt(s.length()));
        int m = s.length() / n;
        if(s.length() > n*m) m++;
        if(n + 2 == m) {
            n++;
            m--;
        }
        char[][] tab = new char[m][n];
        assert(n * m >= s.length());
        
        for(int i = 0; i < s.length(); i++) tab[i%m][i/m] = s.charAt(i);
        for(int i = s.length(); i < m*n; i++) tab[i%m][i/m] = ' ';
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < m; i++) {
            if(i > 0) result.append(' ');
            int size;
            for(size = 0; size < n; size++) 
                if(tab[i][size] == ' ') break;
            result.append(new String(tab[i]).substring(0, size));
        }
        return result.toString();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        String result = Result.encryption(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

