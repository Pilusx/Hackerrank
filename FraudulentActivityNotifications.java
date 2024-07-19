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
    
    int doubleMedian() {
        if(minQueue.size() == maxQueue.size()) {
            return minQueue.peek() + maxQueue.peek();
        } else
            return 2 * minQueue.peek();
    }
}

class Result {

    /*
     * Complete the 'activityNotifications' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY expenditure
     *  2. INTEGER d
     */

    public static int activityNotifications(List<Integer> expenditure, int d) {
        MedianQueue queue = new MedianQueue();
        int result = 0;
        for(int i = 0; i < d; i++) queue.add(expenditure.get(i));
        for(int i = d; i < expenditure.size(); i++) {
            if(queue.doubleMedian() <= expenditure.get(i)) result++;
            queue.add(expenditure.get(i));
            queue.remove(expenditure.get(i-d));
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

        int d = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> expenditure = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int result = Result.activityNotifications(expenditure, d);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

