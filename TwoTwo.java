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

class Node {
    char lastChar = ' ';
    int parent = 0;
    int current = 0;
    int fail = 0;
    int depth = 0;
    int[] g = new int[10];
    boolean end = false;

    int getDepth() { return depth; }
}

class AhoCorasick {
    List<Node> nodes = new ArrayList<>();
    int genesCount = 0;
    
    AhoCorasick() {
        Node root = new Node();
        nodes.add(root);
        
        BigInteger two = BigInteger.valueOf(2);
        BigInteger power = BigInteger.ONE;
        addString(power.toString());
        for(int i = 1; i <= 800; i++) {
            power = power.multiply(two);
            addString(power.toString());
        }
        
        evalFail();
    }
    
    private void addString(String s) {
        int u = 0;
        
        for(int i = 0; i < s.length(); i++) {
            Node node = nodes.get(u);
            int next = node.g[s.charAt(i)-'0'];
            if (next == 0) {
                Node temp = new Node();
                temp.lastChar = s.charAt(i);
                temp.parent = u;
                temp.current = nodes.size();
                temp.depth = node.depth + 1;
                nodes.add(temp);
                node.g[s.charAt(i)-'0'] = temp.current;
                u = temp.current;
            } else u = next;
        }
        
        Node node = nodes.get(u);
        node.end = true;
    }
    
    private void evalFail() {
        List<Node> order = new ArrayList<>();
        order.addAll(nodes);
        Collections.sort(order, Comparator.comparingInt(Node::getDepth));

        for(Node n : order) {
            if(n.depth < 2) continue;
            int fail = nodes.get(n.parent).fail;
            boolean done = false;
            while(fail != 0) {
                int u = nodes.get(fail).g[n.lastChar-'0'];
                if(u == 0) fail = nodes.get(fail).fail;
                else {
                    n.fail = u;
                    done = true;
                    break;
                }
            }
            if(!done)
                n.fail = nodes.get(0).g[n.lastChar-'0'];
        }
    }
    
    int findPowers(String s) {
        int count = 0;
        int u = 0;
        for(int i = 0; i < s.length(); i++) {
            int next = nodes.get(u).g[s.charAt(i)-'0'];
            if(next != 0) {
                u = next;
            } else {
                int fail = nodes.get(u).fail;
                boolean done = false;
                while(fail != 0) {
                    int v = nodes.get(fail).g[s.charAt(i)-'0'];
                    if(v == 0) fail = nodes.get(fail).fail;
                    else {
                        u = v;
                        done = true;
                        break;
                    }
                }
                if(!done)
                    u = nodes.get(0).g[s.charAt(i)-'0'];
            }
            
            int match = u;
            while(match != 0) {
                Node node = nodes.get(match);
                if(node.end) count++;
                match = node.fail;  
            }
            
        }
        return count;
    }
}

class Result {
    private static AhoCorasick aho = new AhoCorasick();

    /*
     * Complete the 'twoTwo' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts STRING a as parameter.
     */

    public static int twoTwo(String a) {
        return aho.findPowers(a);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String a = bufferedReader.readLine();

                int result = Result.twoTwo(a);

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

