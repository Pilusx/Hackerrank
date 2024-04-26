import java.io.*;
import java.util.*;
import java.math.*;

// Only positive integers are supported.
class BigInt implements Comparable<BigInt> {
    String value;
    
    final static BigInt ONE = new BigInt("1");
    final static BigInt ZERO = new BigInt("0");
    
    BigInt(String s) {
        value = s;
    }
    
    static <T> BigInt valueOf(T value) {
        return new BigInt(String.valueOf(value));
    }
    
    BigInt add(BigInt other) {
        StringBuilder builder = new StringBuilder("");
        int carry = 0;
        int len1 = value.length();
        int len2 = other.value.length();
        for(int i = 0; i < Math.max(len1, len2); i++) {
            carry += i < len1 ? value.charAt(len1 - i - 1) - '0' : 0;
            carry += i < len2 ? other.value.charAt(len2 - i - 1) - '0' : 0;
            builder.append(carry % 10);
            carry /= 10;
        }
        if(carry > 0) builder.append(carry);
        builder.reverse();
        return new BigInt(builder.toString()).shorten();
    }
    
    // Only this >= other
    BigInt subtract(BigInt other) {
        StringBuilder builder = new StringBuilder("");
        int len1 = value.length();
        int len2 = other.value.length();
        assert(len1 >= len2);
        int borrow = 0;
        for(int i = 0; i < len1; i++) {
            int result = value.charAt(len1 - i - 1) - '0';
            result -= i < len2 ? other.value.charAt(len2 - i - 1) - '0' : 0;
            result -= borrow % 10;
            if(result < 0) {
                result += 10;
                borrow += 10;
            }
            borrow /= 10;
            builder.append(result);
        }
        builder.reverse();
        return new BigInt(builder.toString()).shorten();
    }
    
    // Only multiplication by 10^k is supported
    BigInt multiply(BigInt other) {
        return new BigInt(value + other.value.substring(1)).shorten();
    }
    
    // Only division by 10^k is supported
    BigInt divide(BigInt other) {
        int k = other.value.length() - 1;
        if(value.length() <= k) return BigInt.ZERO;
        return new BigInt(value.substring(0, value.length() - k));
    }
    
    // Only modulo 10^k is supported
    BigInt mod(BigInt other) {
        int k = other.value.length() - 1;
        if(value.length() <= k) return new BigInt(value);
        return new BigInt(value.substring(value.length() - k)).shorten();
    }
 
    BigInt shorten() {
        int k = 0;
        for(; k < value.length() - 1; k++) {
            if(value.charAt(k) != '0') 
                break;
        }
        return new BigInt(value.substring(k));
    }
    
    BigInt min(BigInt other) {
        return compareTo(other) <= 0 ? new BigInt(value) : new BigInt(other.value);
    }
    
    public int compareTo(BigInt other) {
        if(value.length() < other.value.length()) return -1;
        else if (value.length() > other.value.length()) return 1;
        
        for(int i = 0; i < value.length(); i++) {
            if(value.charAt(i) < other.value.charAt(i)) return -1;
            else if (value.charAt(i) > other.value.charAt(i)) return 1;
        }
        return 0;
    }

    public String toString() {
        return value;
    }
}

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        List<List<BigInt>> result = new ArrayList<>();
        BigInt low = new BigInt(scanner.next()).subtract(BigInt.ONE);
        BigInt high = new BigInt(scanner.next());
        Stack<BigInt> multipliers = new Stack();
        boolean stop = false;
        multipliers.push(BigInt.ONE);
        while(!multipliers.empty()) {
            BigInt q = multipliers.peek();
            BigInt q2 = q.compareTo(BigInt.ONE) == 0 ? BigInt.valueOf(10) : q.multiply(q);
            BigInt distance = low.add(q2).divide(q2).multiply(q2).min(high).subtract(low);
            BigInt step = distance.mod(q2).divide(q);
            low = low.add(step.multiply(q));
            if(step.compareTo(BigInt.ZERO) > 0) {
                List<BigInt> l = new ArrayList<>();
                l.add(BigInt.valueOf(multipliers.size() -1));
                l.add(step);
                result.add(l);
            }
            if(stop || low.add(q).compareTo(high)>= 0) {
                multipliers.pop();
                stop = true;
            }
            else multipliers.push(q2);
        }
        
        System.out.println(result.size());
        for(List<BigInt> r : result) {
            System.out.println(r.get(0) + " " + r.get(1));
        }
    }
}

