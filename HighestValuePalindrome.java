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
     * Complete the 'highestValuePalindrome' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. INTEGER n
     *  3. INTEGER k
     */

    public static String highestValuePalindrome(String s, int n, int k) {
        int[] tab = new int[s.length()];
        boolean[] changed = new boolean[s.length()];

        for(int i = 0 ; i < tab.length; i++) tab[i] = s.charAt(i) - '0';

        for(int i = 0; 2 * i < tab.length; i++) {
            if(tab[i] != tab[tab.length - 1 - i]) {
                if(k == 0) {
                    return "-1";
                } else {
                    k--;
                    int max = Math.max(tab[i], tab[tab.length - 1 - i]);
                    if(max != tab[i]) {
                        tab[i] = max;
                        changed[i] = true;
                    }
                    else {
                        tab[tab.length - 1 - i] = max;
                        changed[tab.length - 1 - i] = true;
                    }
                }
            }
        }
        
        for(int i = 0; 2 * i < tab.length; i++) {
            if(tab[i] != 9) {
                if(!changed[i] && !changed[tab.length - 1 - i] && k >= 2) {
                    k -= 2;
                    tab[i] = tab[tab.length - 1 - i] = 9;
                    changed[i] = changed[tab.length - 1 - i] = true;
                } else if(i != tab.length - i - 1 
                          &&(changed[i] || changed[tab.length - 1 - i]) 
                          && k >= 1) {
                    k--;
                    tab[i] = tab[tab.length -1 - i] = 9;
                    changed[i] = changed[tab.length - 1 - i] = true;
                } else if(i == tab.length - i - 1) {
                    if(changed[i]) {
                        tab[i] = 9;
                    } else if(k >= 1) {
                        tab[i] = 9;
                        changed[i] = true;
                    }
                }   
            }
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < tab.length; i++) builder.append(tab[i]);
        return builder.toString();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        String s = bufferedReader.readLine();

        String result = Result.highestValuePalindrome(s, n, k);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

