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

class MaxTree<T extends Comparable<T>> {
    TreeMap<T, Integer> map = new TreeMap<>();
    void put(T x) {
        map.put(x, map.getOrDefault(x, 0) + 1);
    }
    void remove(T x) {
        int k = map.get(x);
        if(k == 1) map.remove(x);
        else map.put(x, k - 1);
    }
    T max() {
        return map.lastKey();
    }
}

class Result {

    /*
     * Complete the 'getMax' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts STRING_ARRAY operations as parameter.
     */

    public static List<Integer> getMax(List<String> operations) {
        List<Integer> result = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        MaxTree<Integer> tree = new MaxTree<>();
        
        for(String op : operations) {
            String[] strings = op.split(" ");
            int type = Integer.parseInt(strings[0]);
            switch(type) {
                case 1: int x = Integer.parseInt(strings[1]); tree.put(x); stack.push(x); break;
                case 2: tree.remove(stack.peek()); stack.pop();  break;
                case 3: result.add(tree.max()); break;
            }
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> ops = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        List<Integer> res = Result.getMax(ops);

        bufferedWriter.write(
            res.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

