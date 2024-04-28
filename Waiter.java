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

class PrimesSieve {
    final static int K = 50_000;
    boolean[] sieve = new boolean[K];
    List<Integer> primes = new ArrayList<>();
    PrimesSieve() {
        sieve[0] = false;
        sieve[1] = false;
        Arrays.fill(sieve, true);
        for(int i = 2; i < K; i++) {
            if(sieve[i]) {
                primes.add(i);
                for(int j = 2; j * i < K; j++) {
                    sieve[i*j] = false;
                }
            }
        }
    }
}

class Result {

    /*
     * Complete the 'waiter' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY number
     *  2. INTEGER q
     */

    public static List<Integer> waiter(List<Integer> number, int q) {
        PrimesSieve ps = new PrimesSieve();
        Map<Integer, List<Integer>> map = new TreeMap<>();
        List<Integer> other = new ArrayList<>();
        List<Integer> result = new ArrayList<>();

loop:   for(int n : number) {
            for(int i = 0; i < q; i++) {
                if(n % ps.primes.get(i) == 0) {
                    map.putIfAbsent(i, new ArrayList<>());
                    map.get(i).add(n);
                    continue loop;
                }
            }
            other.add(n);
        }
        for(int i = 0; i < q; i++) {
            if(map.containsKey(i) && i % 2 == 1) Collections.reverse(map.get(i));
            for(int n : map.getOrDefault(i, new ArrayList<>())) {
                result.add(n);
            }
        }
        if(q % 2 == 0) Collections.reverse(other);
        for(int n : other) result.add(n);
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int q = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> number = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> result = Result.waiter(number, q);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

