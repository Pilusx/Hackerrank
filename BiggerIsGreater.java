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
     * Complete the 'biggerIsGreater' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING w as parameter.
     */
    // Based on https://www.geeksforgeeks.org/implementing-next_permutation-in-java-with-examples/
    public static String biggerIsGreater(String w) {
        char[] tab = w.toCharArray();
        int pivot = tab.length - 1;
        while(pivot > 0) {
            if(tab[pivot-1] < tab[pivot]) break;
            pivot--;
        }
        if(pivot == 0) return "no answer";
        pivot--;
        int successor = tab.length - 1;
        while(pivot < successor) {
            if(tab[pivot] < tab[successor]) break;
            successor--;
        }
        char temp = tab[pivot];
        tab[pivot] = tab[successor];
        tab[successor] = temp;
        for(int i = pivot + 1, j = tab.length - 1; i < j; i++, j--) {
            temp = tab[i];
            tab[i] = tab[j];
            tab[j] = temp;
        }
        return new String(tab);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int T = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, T).forEach(TItr -> {
            try {
                String w = bufferedReader.readLine();

                String result = Result.biggerIsGreater(w);

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

