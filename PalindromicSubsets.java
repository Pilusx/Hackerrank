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

class SubsetTree {
    final static int P = 1_000_000_007;
    final static int K = 1 << 17;
    final static int L = 26;
    int[][] freq = new int[2 * K][L];
    int[] shift = new int[2 * K];
    int length = 0;
    
    SubsetTree(String s) {
        length = s.length();
        for(int i = 0; i < s.length(); i++)
            freq[K+i][s.charAt(i)-'a'] = 1;
        for(int i = K - 1; i > 0; i--)
            update(i);
    }
    
    private void update(int x) {
        if(x >= K) return;
        int[] arr = freq[x];
        Arrays.fill(arr, 0);
        addShiftLeft(arr, freq[2*x], shift[2*x]);
        addShiftLeft(arr, freq[2*x+1], shift[2*x+1]);
    }
    
    void shift(int i, int j, int t) {
        i += K;
        j += K;
        t %= L;
        if(t == 0) return;
        
        shift[i] = (shift[i] + t) % L;
        if(i != j) shift[j] = (shift[j] + t) % L;
        
        while(i + 1 < j) {
            update(i);
            update(j);
            if(i % 2 == 0)
                shift[i+1] = (shift[i+1] + t) % L;
            if(j % 2 == 1)
                shift[j-1] = (shift[j-1] + t) % L;
            i /= 2;
            j /= 2;
        }
        while(i > 1) {
            update(i);
            if(i != j) update(j);
            i /= 2;
            j /= 2;
        }
    }
    
    // From: https://www.baeldung.com/java-rotate-arrays
    private void shiftArrayInplace(int[] arr, int t) {
        t %= L;
        if(t == 0) return;
        int count = 0;
        for (int start = 0; count < L; start++) {
            int current = start;
            int prev = arr[start];
            do {
                int next = (current + t) % L;
                int temp = arr[next];
                arr[next] = prev;
                prev = temp;
                current = next;
                count++;
            } while (start != current);
        }
    }
      
    private void addLeft(int[] f1, int[] f2) {
        for(int i = 0; i < L; i++)
            f1[i] += f2[i];
    }
    
    private void addShiftLeft(int[] f1, int[] f2, int t) {
        for(int i = 0; i < L; i++) {
            f1[(i + t) % L] += f2[i];
        }
    }
    
    int[] frequencies(int i, int j) {
        int[] left = new int[L],
              right = new int[L];
        i += K;
        j += K;

        addLeft(left, freq[i]);
        if(i != j) addLeft(right, freq[j]);
              
        while(i + 1 < j) {
            shiftArrayInplace(left, shift[i]);
            shiftArrayInplace(right, shift[j]);
            if(i % 2 == 0) addShiftLeft(left, freq[i+1], shift[i+1]);
            if(j % 2 == 1) addShiftLeft(right, freq[j-1], shift[j-1]);
            i /= 2;
            j /= 2;
        }

        while(i > 0) {
            shiftArrayInplace(left, shift[i]);
            shiftArrayInplace(right, shift[j]);
            i /= 2;
            j /= 2;
        }
        
        addLeft(left, right);
        return left;
    }
    
    int pow(int x, int p) {
        long result = 1;
        long temp = x;
        while(p > 0) {
            if(p % 2 == 1) result = (result * temp) % P;
            temp = (temp * temp) % P;
            p /= 2;
        }
        return (int) result;
    }
    
    int subsets(int i, int j) {
        int[] f = frequencies(i, j);
        long result, even = 1;
        
        for(int x : f) {
            if(x > 0) even = (even * pow(2, x-1)) % P;
        }
        result = even - 1;

        for(int x : f) {
            if(x > 0) {
                result += (((even * pow(2, P - x)) % P) * pow(2, x-1)) % P;
                result %= P;
            }
        }
        
        return (int)result;
    }

    char charAt(int i) {
        int c = 0;
        i += K;
        for(int j = 1; j < L; j++) {
            if(freq[i][j] == 1) c = j;
        }
        while(i > 0) {
            c = (c + shift[i]) % L;
            i /= 2;
        } 
        return (char) (c + 'a');
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < length; i++)
            b.append(charAt(i));
        return b.toString();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int q = Integer.parseInt(firstMultipleInput[1]);

        String s = bufferedReader.readLine();

        SubsetTree tree = new SubsetTree(s);

        for(int i = 0; i < q; i++) {
            int[] query = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .mapToInt(Integer::parseInt).toArray();
            
            if(query[0] == 1)
                tree.shift(query[1], query[2], query[3]);
            else
                System.out.println(tree.subsets(query[1], query[2]));
        }

        bufferedReader.close();
    }
}

