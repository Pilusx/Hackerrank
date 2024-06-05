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

class City implements Comparable<City> {
    int position;
    int population;
    
    City(int position, int population) {
        this.position = position;
        this.population = population;
    }
    
    @Override
    public int compareTo(City other) {
        int cmp = Integer.compare(population, other.population);
        if(cmp != 0) return cmp;
        return Integer.compare(position, other.position);
    }
}

class DistanceTree {
    static final int P = 1_000_000_007;
    static final int K = 1 << 30;
    HashMap<Integer, Integer> sum = new HashMap<>(),
                              count = new HashMap<>(); 
    
    static int addModulo(int... arr) {
        long result = 0;
        for(int x : arr) {
            result += x;
            result %= P;
        }
        return (int) result;
    }
    
    static int multiplyModulo(int... arr) {
        long result = 1;
        for(int x : arr) {
            result *= x;
            result %= P;
        }
        return (int) result;
    }
        
    void add(int x) {
        x += K;
        count.merge(x, 1, DistanceTree::addModulo);
        sum.merge(x, x - K, DistanceTree::addModulo);
        x /= 2;

        while(x > 0) {
            count.put(x, addModulo(
                count.getOrDefault(2*x, 0),
                count.getOrDefault(2*x+1, 0)));
            
            sum.put(x, addModulo(
                sum.getOrDefault(2*x, 0),
                sum.getOrDefault(2*x+1, 0)));

            x /= 2;
        }
    }
    
    int distance(int x) {
        int sum = 0;
        int position = x;
        x += K;
        
        while(x > 1) {
            if(x % 2 == 0) {
                sum = addModulo(sum, 
                    this.sum.getOrDefault(x+1, 0),
                    P - multiplyModulo(count.getOrDefault(x+1, 0), position));
            } else {
                sum = addModulo(sum, 
                    multiplyModulo(count.getOrDefault(x-1, 0), position),
                    P - this.sum.getOrDefault(x-1, 0));
           }
            x /= 2;
        }
        return sum;
    }
}

class Result {
    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static int solve(List<Integer> coordinates, List<Integer> population) {
    // Write your code here
        PriorityQueue<City> queue = new PriorityQueue<>();
        DistanceTree dtree = new DistanceTree();
        for(int i = 0; i < coordinates.size(); i++)
            queue.add(new City(coordinates.get(i), population.get(i)));
        
        int sum = 0;
        while(!queue.isEmpty()) {
            City city = queue.poll();
            sum = DistanceTree.addModulo(sum,
                DistanceTree.multiplyModulo(city.population, dtree.distance(city.position)));
            dtree.add(city.position);
        }
        return sum;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int arrCount = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> coordinates = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                List<Integer> population = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                int result = Result.solve(coordinates, population);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

