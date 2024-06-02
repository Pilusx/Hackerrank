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

class Customer implements Comparable<Customer> {
    private int t, l;
    Customer(List<Integer> customer) {
        assert(customer.size() == 2);
        t = customer.get(0);
        l = customer.get(1);
    }
    
    int getTime() {
        return t;
    }
    
    int getCookingTime() {
        return l;
    }

    @Override
    public int compareTo(Customer other) {
        int cmp = Integer.compare(t, other.t);
        if(cmp != 0) return cmp;
        return Integer.compare(l, other.l);
    } 
}

class Result {

    /*
     * Complete the 'minimumAverage' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY customers as parameter.
     */

    public static long minimumAverage(List<List<Integer>> customers) {
        List<Customer> cs = 
            customers.stream().map(Customer::new).sorted().collect(Collectors.toList());

        PriorityQueue<Customer> queue =
            new PriorityQueue<>(Comparator.comparingInt(Customer::getCookingTime));
        
        long waitingTime = 0;
        long time = 0;
        
        int i = 0;
        int processed = 0;
        while(processed < cs.size()) {
            if(i < cs.size() && (time >= cs.get(i).getTime() || i == processed)) {
                queue.add(cs.get(i));
                i++;
            } else {
                Customer u = queue.poll();
                time = Math.max(time, u.getTime());
                /*
                System.out.println(time 
                    + " Customer(" + u.getTime() + "," + u.getCookingTime() + ")"
                    + " waits " + (time - u.getTime()));
                */
                time += u.getCookingTime();
                waitingTime += time - u.getTime();
                processed++;
            }
        }

        return waitingTime / cs.size();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> customers = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                customers.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        long result = Result.minimumAverage(customers);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

