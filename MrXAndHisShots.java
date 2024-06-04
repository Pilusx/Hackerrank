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

class Event implements Comparable<Event> {
    enum Type {
        SHOT_START(false),
        SHOT_END(true),
        PLAYER_START(false),
        PLAYER_END(true);
        
        Type(boolean order) {
            this.order = order;
        }
        boolean order;
    };
    Type type;
    int time;
    
    Event(Type type, int time) {
        this.type = type;
        this.time = time;
    }
    
    int getTime() {
        return time;
    }
    
    @Override
    public int compareTo(Event other) {
        int cmp = Integer.compare(this.time, other.time);
        if(cmp != 0) return cmp;
        return Boolean.compare(this.type.order, other.type.order);
    }
    
    @Override
    public String toString() {
        return type + "(" + time + ")";
    }
}

class Result {

    /*
     * Complete the 'solve' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY shots
     *  2. 2D_INTEGER_ARRAY players
     */

    public static long solve(List<List<Integer>> shots, List<List<Integer>> players) {
    // Write your code here
        PriorityQueue<Event> queue = new PriorityQueue<>();
        for(List<Integer> shot : shots) {
            queue.add(new Event(Event.Type.SHOT_START, shot.get(0)));
            queue.add(new Event(Event.Type.SHOT_END, shot.get(1)));
        }
        for(List<Integer> player : players) {
            queue.add(new Event(Event.Type.PLAYER_START, player.get(0)));
            queue.add(new Event(Event.Type.PLAYER_END, player.get(1)));
        }
        
        int numPlayers = 0, numShots = 0;
        long sum = 0;
        while(!queue.isEmpty()) {
            Event ev = queue.poll();
            switch(ev.type) {
                case PLAYER_START: 
                    numPlayers++;
                    sum += numShots;
                    break;
                case PLAYER_END: 
                    numPlayers--; 
                    break;
                case SHOT_START: 
                    numShots++;
                    sum += numPlayers; 
                    break;
                case SHOT_END: 
                    numShots--; 
                    break;
            }
        }
        
        return sum;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> shots = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                shots.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<List<Integer>> players = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                players.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        long result = Result.solve(shots, players);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

