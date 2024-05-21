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

class MedianQueue {
    private PriorityQueue<Integer> minQueue = new PriorityQueue<>();
    private PriorityQueue<Integer> maxQueue = new PriorityQueue<>((x, y) -> y.compareTo(x));

    private void balance() {
        while(maxQueue.size() > minQueue.size()) {
            minQueue.add(maxQueue.poll());
        }
        while(minQueue.size() > maxQueue.size() + 1) {
            maxQueue.add(minQueue.poll());
        }
    }

    void add(int x) {
        if(minQueue.isEmpty() || x < minQueue.peek()) maxQueue.add(x);
        else minQueue.add(x);
        balance();
    }
    
    void remove(int x) {
        if (maxQueue.contains(x)) maxQueue.remove(x);
        else if(minQueue.contains(x)) minQueue.remove(x);
        balance();
    }
    
    double median() {
        if(minQueue.size() == maxQueue.size()) {
            double sum = minQueue.peek() + maxQueue.peek();
            return sum/2;
        } else
            return minQueue.peek();
    }
}



class Result {

    /*
     * Complete the 'runningMedian' function below.
     *
     * The function is expected to return a DOUBLE_ARRAY.
     * The function accepts INTEGER_ARRAY a as parameter.
     */

    public static List<Double> runningMedian(List<Integer> a) {
    // Write your code here
        MedianQueue mq = new MedianQueue();
        List<Double> result = new ArrayList<>();
        for(int x : a) {
            mq.add(x);
            result.add(mq.median());
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int aCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = IntStream.range(0, aCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        List<Double> result = Result.runningMedian(a);

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

