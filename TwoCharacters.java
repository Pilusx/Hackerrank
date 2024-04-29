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
     * Complete the 'alternate' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING s as parameter.
     */

    public static int alternate(String s) {
        int best = 0;
        for(int u = 'a'; u + 1 <= 'z'; u++) {
            for(int v = u + 1; v <= 'z'; v++) {
                int len = 0;
                char lastChar = ' ';
                for(char c : s.toCharArray()) {
                    if(c == u || c == v) {
                        if(c != lastChar) {
                            len++;
                            lastChar = c;
                        } else {
                            len = 0;
                            break;
                        }
                    }
                }
                if(len > 1) best = Math.max(best, len);
            }
        }
        return best;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int l = Integer.parseInt(bufferedReader.readLine().trim());

        String s = bufferedReader.readLine();

        int result = Result.alternate(s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

