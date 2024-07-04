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
    static String[] numbers = {"zero",
        "one", "two", "three", "four", "five", 
        "six", "seven", "eight", "nine", "ten",
        "eleven", "twelve", "thirteen", "fourteen", "fifteen", 
        "sixteen", "seventeen", "eighteen","nineteen", "twenty"
    };

    /*
     * Complete the 'timeInWords' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. INTEGER h
     *  2. INTEGER m
     */

    public static String timeInWords(int h, int m) {
        if(m == 0) return numbers[h] + " o' clock";
        else if(m == 15) return "quarter past " + numbers[h];
        else if(m == 30) return "half past " + numbers[h];
        else if(m == 45) return "quarter to " + numbers[h+1];
        else {
            String suffix = (Math.min(m, 60 - m) == 1 ? "minute " : "minutes ")
                + (m < 30 ? "past " + numbers[h] : "to " + numbers[h%12+1]);
            String prefix;
            if(1 <= m && m <= 20)
                prefix = numbers[m];
            else if(21 <= m && m < 30) 
                prefix = "twenty " + numbers[m % 10];
            else if(31 <= m && m < 40)
                prefix = "twenty " + numbers[(40 - m) % 10];
            else
                prefix = numbers[60 - m];
            return prefix + " " + suffix;
        }
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int h = Integer.parseInt(bufferedReader.readLine().trim());

        int m = Integer.parseInt(bufferedReader.readLine().trim());

        String result = Result.timeInWords(h, m);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

