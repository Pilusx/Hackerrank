import java.io.*;
import java.util.*;
import java.math.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        List<List<BigInteger>> result = new ArrayList<>();
        BigInteger low = scanner.nextBigInteger().subtract(BigInteger.ONE);
        BigInteger high = scanner.nextBigInteger();
        Stack<BigInteger> multipliers = new Stack();
        boolean stop = false;
        multipliers.push(BigInteger.ONE);
        while(!multipliers.empty()) {
            BigInteger q = multipliers.peek();
            BigInteger q2 = q.compareTo(BigInteger.ONE) == 0 ? BigInteger.valueOf(10) : q.multiply(q);
            BigInteger distance = low.add(q2).divide(q2).multiply(q2).subtract(low).min(high.subtract(low));
            BigInteger step = distance.mod(q2).divide(q);
            low = low.add(step.multiply(q));
            if(step.compareTo(BigInteger.ZERO) > 0) {
                List<BigInteger> l = new ArrayList<>();
                l.add(BigInteger.valueOf(multipliers.size() -1));
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
        for(List<BigInteger> r : result) {
            System.out.println(r.get(0) + " " + r.get(1));
        }
    }
}
