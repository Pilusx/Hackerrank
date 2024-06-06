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

class PolynomialTree {
    final static int P = 1_000_000_007;
    final static int K = 1 << 17;
    long[] data = new long[2 * K];
    int[] degree = new int[2 * K];
    long[] remainders = new long[K];

    PolynomialTree(int a, int b, List<Integer> c) {
        long z = (0L + P - (b % P)) * power(a % P, P-2) % P;
        remainders[0] = 1;
        data[K] = c.get(0);
        for(int i = 1; i < K; i++) {
            remainders[i] = z * remainders[i-1] % P;
            degree[K+i] = i;
            if(i < c.size()) data[K+i] = c.get(i);
        }
        for(int i = K - 1; i > 0; i--) {
            degree[i] = degree[2*i];
            update(i);
        }
    }
    
    private void update(int i) {
        if(i >= K) return;
        data[i] = (data[2*i]
                + (remainders[degree[2*i+1] - degree[2*i]] * data[2*i+1] % P)) % P;
    }
    
    void put(int i, int x) {
        i += K;
        data[i] = x % P;
        while(i > 0) {
            update(i);
            i /= 2;
        }
    }
    
    int query(int l, int r) {
        // System.out.println(toList(l, r));
        long result = 0;
        int base = l;
        l += K;
        r += K;
        result = (result + data[l]) % P;
        if(l != r) result = (result + remainders[degree[r]-base] * data[r]) % P; 
       
        while(l + 1 < r) {
            if(l % 2 == 0) result = (result + remainders[degree[l+1] - base] * data[l+1]) % P;
            if(r % 2 == 1) result = (result + remainders[degree[r-1] - base] * data[r-1]) % P;
            l /= 2;
            r /= 2;
        }
       
       return (int) result; 
    }

    private static int power(int a, int p) {
        long result = 1;
        long temp = a % P;
        while(p > 0) {
            if(p % 2 == 1) result = (result * temp) % P;
            temp = (temp * temp) % P;
            p /= 2;
        }
        return (int) result;
    }
    
    List<Long> toList(int l, int r) {
        List<Long> coeffs = new ArrayList<>();
        for(int i = l; i <= r; i++)
            coeffs.add(data[K+i]);
        return coeffs;
    }

}

class Result {

    /*
     * Complete the 'polynomialDivision' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER a
     *  2. INTEGER b
     *  3. 2D_INTEGER_ARRAY queries
     */

    public static List<String> polynomialDivision(int a, int b, List<Integer> c, List<List<Integer>> queries) {
        PolynomialTree ptree = new PolynomialTree(a, b, c);
        List<String> result = new ArrayList<>();
        for(List<Integer> query : queries) {
            if(query.get(0) == 1) ptree.put(query.get(1), query.get(2));
            else {
                if(ptree.query(query.get(1), query.get(2)) == 0) result.add("Yes");
                else result.add("No");
            }

        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int a = Integer.parseInt(firstMultipleInput[1]);

        int b = Integer.parseInt(firstMultipleInput[2]);

        int q = Integer.parseInt(firstMultipleInput[3]);

        List<Integer> c = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, q).forEach(i -> {
            try {
                queries.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<String> result = Result.polynomialDivision(a, b, c, queries);

        bufferedWriter.write(
            result.stream()
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}


