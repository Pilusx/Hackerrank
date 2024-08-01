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
    static boolean isKaprekar(int i) {
        int power = 1;
        while(i / power > 0) power *= 10;
        long square = (long)i * i;
        return (square / power) + (square % power) == i;
    }


    /*
     * Complete the 'kaprekarNumbers' function below.
     *
     * The function accepts following parameters:
     *  1. INTEGER p
     *  2. INTEGER q
     */

    public static void kaprekarNumbers(int p, int q) {
        List<Integer> numbers = new ArrayList<>();
        for(int i = p; i <= q; i++) {
            if(isKaprekar(i)) numbers.add(i);
        }
        if(numbers.isEmpty()) System.out.println("INVALID RANGE");
        else for(int x : numbers) System.out.printf(x + " ");

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int p = Integer.parseInt(bufferedReader.readLine().trim());

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        Result.kaprekarNumbers(p, q);

        bufferedReader.close();
    }
}

