import java.io.*;
import java.lang.RuntimeException;
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

abstract class CostNode {
    final static int A = 30;
    int value, accum;
    
    CostNode() {
        value = defaultValue();
        accum = defaultValue();
    }

    abstract String getName();    
    abstract int defaultValue();
    abstract int merge(int ... a);

    public String toString() {
        return String.format("[%s=%s(%s)]", getName(), value, accum);
    }
};

class MinNode extends CostNode {
    String getName() { return "min"; }
    int defaultValue() { return Integer.MAX_VALUE; }
    int merge(int ...a) { 
        int result = defaultValue();
        for(int x : a) result = Math.min(result, x);
        return result;
    }
}

class MaxNode extends CostNode {
    String getName() { return "max"; }
    int defaultValue() { return 0; }
    int merge(int ...a) { 
        int result = defaultValue();
        for(int x : a) result = Math.max(result, x);
        return result;
    }
}

class OrNode extends CostNode {
    String getName() { return "or"; }
    int defaultValue() { return 0; }
    int merge(int ...a) { 
        int result = defaultValue();
        for(int x : a) result |= x;
        return result;
    }
}

class AndNode extends CostNode {
    String getName() { return "and"; }
    int defaultValue() { return (1 << A) - 1;}
    int merge(int ...a) { 
        int result = defaultValue();
        for(int x : a) result &= x;
        return result;
    }
}


class Tree <Node extends CostNode> {
    static final int K = 1 << 17;
    CostNode[] nodes;
    CostNode op;
    
    Tree(Class<Node> c) {
        try {
            nodes = (CostNode[]) java.lang.reflect.Array.newInstance(c, 2 * K);
            for(int i = 0; i < 2 * K; i++)
                nodes[i] = c.newInstance();
            op = nodes[0];
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
    
    void update(int i) {
        if(i >= K) return;
        nodes[i].value = op.merge(nodes[2*i].value, nodes[2*i].accum,
                                  nodes[2*i+1].value, nodes[2*i+1].accum);
    }
    
    void put(int i, int w) {
        i += K;
        nodes[i].value = w;
        while(i > 0) {
            update(i);
            i /= 2;
        }
    }

    void put(int l, int r, int w) {
        l += K;
        r += K;
        nodes[l].value = op.merge(nodes[l].value, w);
        if(l != r) nodes[r].value = op.merge(nodes[r].value, w);
        
        while(l + 1 < r) {
            update(l);
            update(r);
            if(l % 2 == 0) nodes[l+1].accum = op.merge(nodes[l+1].accum, w);
            if(r % 2 == 1) nodes[r-1].accum = op.merge(nodes[r-1].accum, w);
            l /= 2;
            r /= 2;
        }
        
        while(l > 0) {
            update(l);
            if(l != r) update(r);
            l /= 2;
            r /= 2;
        }
    }
    
    int query(int l, int r) {
        l += K;
        r += K;
        int result = nodes[l].value;
        if(l != r) result = op.merge(result, nodes[r].value);
        
        while(l + 1 < r) {
            result = op.merge(result, nodes[l].accum);
            result = op.merge(result, nodes[r].accum);
            if(l % 2 == 0) result = op.merge(result, nodes[l+1].value, nodes[l+1].accum);
            if(r % 2 == 1) result = op.merge(result, nodes[r-1].value, nodes[r-1].accum);
            l /= 2;
            r /= 2;
        }
        
        while(l > 0) {
            result = op.merge(result, nodes[l].accum);
            if(l != r) result = op.merge(result, nodes[r].accum);
            l /= 2;
            r /= 2;
        }
        return result;
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < 2 * K; i++)
            builder.append(String.format("%d %s ", i, nodes[i]));
        return builder.toString();
    }
}

class CostTree {
    Tree<OrNode> or = new Tree<>(OrNode.class);
    Tree<AndNode> and = new Tree<>(AndNode.class);
    Tree<MinNode> min = new Tree<>(MinNode.class);
    Tree<MaxNode> max = new Tree<>(MaxNode.class);

    Tree<MaxNode> results = new Tree<>(MaxNode.class);
    
    private int search(int r, int k) {
        int l = 1;
        while(l < r) {
            int mid = (l + r)/2;
            int cost1 = cost(mid);
            if(cost1 >= k) r = mid;
            else if(cost1 > cost(mid+1)) r = mid;
            else l = mid + 1;
        }
        
        if(cost(l) >= k) return l;
        return -1;
    }
    
    void updateResults(int i, int k) {
        int l = search(i, k);
        if(l != -1)
            results.put(l, i, i - l + 1);
    }

    void put(int i, int w) {
        or.put(1, i, w);
        and.put(1, i, w);
        min.put(1, i, w);
        max.put(1, i, w);
    }
    
    int query(int i) {
        int result = results.query(i, i);
        return result == 0 ? -1 : result;
    }
    
    private int cost(int r) {
        return or.query(r, r) - and.query(r, r) 
             - max.query(r, r) + min.query(r, r);
    }
}



class Result {

    /*
     * Complete the 'costlyIntervals' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER k
     *  3. INTEGER_ARRAY A
     */

    public static List<Integer> costlyIntervals(int n, int k, List<Integer> A) {
        CostTree ctree = new CostTree();
        
        for(int i = 1; i <= n; i++) {
            ctree.put(i, A.get(i-1));
            ctree.updateResults(i, k);
        }
        
        return IntStream.range(1, n+1).mapToObj(ctree::query).collect(Collectors.toList());
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int k = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> A = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> result = Result.costlyIntervals(n, k, A);

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

