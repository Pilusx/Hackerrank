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

class Entry implements Cloneable {
    static final int p = 31;
    static final int P = 100_000_007;
    int[] stacks;
    int hash = -1;
    
    Entry(int[] stacks) {
        assert(stacks.length == 4);
        this.stacks = stacks.clone();
    }
    
    Entry(List<Integer> list) {
        stacks = new int[4];
        for(int i = list.size() - 1; i >= 0; i--) 
            stacks[list.get(i) - 1] += (1 << i);
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Entry)) return false;
        if(hashCode() != obj.hashCode()) return false;
        Entry o = (Entry) obj;
        for(int i = 0; i < 4; i++) {
            if(!(stacks[i] == o.stacks[i])) 
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if(hash != -1) return hash;
        long result = 0;
        long temp = 1;
        for(int i = 0; i < 4; i++) {
            result += temp * stacks[i];
            result %= P;
            temp = (temp * p) % P;
        }
        return hash = (int) result;
    }
    
    @Override
    public Object clone() {
        return new Entry(stacks);
    }
    
    @Override
    public String toString() {
        return Stream.of(stacks).map(Object::toString).collect(Collectors.joining(" "));
    }
    
    List<Entry> neighbours() {
        List<Entry> result = new ArrayList<Entry>();
        
        for(int i = 0; i < 4; i++) {
            if(stacks[i] == 0) continue;
            for(int j = 0; j < 4; j++) {
                if(i == j) continue;
                int x = Integer.lowestOneBit(stacks[i]);
                if(stacks[j] == 0 || x < Integer.lowestOneBit(stacks[j])) {
                    Entry e = (Entry)clone();
                    e.stacks[i] -= x;
                    e.stacks[j] += x;
                    result.add(e);
                }
            }
        }

        return result;  
    }
}

class Result {

    /*
     * Complete the 'hanoi' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY posts as parameter.
     */

    public static int hanoi(List<Integer> posts) {
        Map<Entry, Integer> memory = new HashMap<>();
        Queue<Entry> visits = new LinkedList<>();
        
        Entry e = new Entry(posts);
        Entry dest = new Entry(Collections.nCopies(posts.size(), 1));
        memory.put(e, 0);
        visits.add(e); 
        
        while(!visits.isEmpty()) {
            e = visits.poll();
            int depth = memory.get(e);
            if(e.equals(dest)) return depth;
            for(Entry e1 : e.neighbours()) {
                if(!memory.containsKey(e1)) {
                    memory.put(e1, depth + 1);
                    visits.add(e1);
                }
            }
        }
    
        return -1;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> loc = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        int res = Result.hanoi(loc);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}

