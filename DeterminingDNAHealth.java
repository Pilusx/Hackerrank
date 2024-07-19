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
    int[] g = new int[26];
    TreeMap<Integer, Long> health = new TreeMap<>();

    int getDepth() { return depth; }
    
    void preprocessHealth() {
        long sum = 0;
        health.put(0, sum);
        for(Map.Entry<Integer, Long> entry : health.entrySet()) {
            sum += entry.getValue();
            entry.setValue(sum);
        }
        health.put(Integer.MAX_VALUE, sum);
    } 
    
    long getHealth(int start, int end) {
        return health.floorEntry(end).getValue() - health.lowerEntry(start).getValue();
    }
}

class AhoCorasick {
    List<Node> nodes = new ArrayList<>();
    int genesCount = 0;
    
    AhoCorasick() {
        Node root = new Node();
        nodes.add(root);
    }
    
    void addString(String s, int health) {
        int u = 0;
        
        for(int i = 0; i < s.length(); i++) {
            Node node = nodes.get(u);
            int next = node.g[s.charAt(i)-'a'];
            if (next == 0) {
                Node temp = new Node();
                temp.lastChar = s.charAt(i);
                temp.parent = u;
                temp.current = nodes.size();
                temp.depth = node.depth + 1;
                nodes.add(temp);
                node.g[s.charAt(i)-'a'] = temp.current;
                u = temp.current;
            } else u = next;
        }
        
        Node node = nodes.get(u);
        node.health.put(++genesCount, (long)health);
    }
    
    void evalFail() {
        List<Node> order = new ArrayList<>();
        order.addAll(nodes);
        Collections.sort(order, Comparator.comparingInt(Node::getDepth));

        for(Node n : order) {
            if(n.depth < 2) continue;
            int fail = nodes.get(n.parent).fail;
            boolean done = false;
            while(fail != 0) {
                int u = nodes.get(fail).g[n.lastChar-'a'];
                if(u == 0) fail = nodes.get(fail).fail;
                else {
                    n.fail = u;
                    done = true;
                    break;
                }
            }
            if(!done) {
                int u = nodes.get(0).g[n.lastChar-'a'];
                if(u == 0) n.fail = 0;
                else n.fail = u;
            }
        }
        
        for(Node n : nodes) n.preprocessHealth();
    }
    
    long findGenes(int start, int end, String s) {
        long sum = 0;
        int u = 0;
        for(int i = 0; i < s.length(); i++) {
            int next = nodes.get(u).g[s.charAt(i)-'a'];
            if(next != 0) {
                u = next;
            } else {
                int fail = nodes.get(u).fail;
                boolean done = false;
                while(fail != 0) {
                    int v = nodes.get(fail).g[s.charAt(i)-'a'];
                    if(v == 0) fail = nodes.get(fail).fail;
                    else {
                        u = v;
                        done = true;
                        break;
                    }
                }
                if(!done) {
                    int v = nodes.get(0).g[s.charAt(i)-'a'];
                    if(v == 0) u = 0;
                    else u = v;
                }
            }
            
            int match = u;
            while(match != 0) {
                Node node = nodes.get(match);
                sum += node.getHealth(start+1, end+1);
                match = node.fail;  
            }
            
        }
        // System.out.println(start + " " + end + " " + s + "->" + sum);
        return sum;
    }
}



public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        AhoCorasick aho = new AhoCorasick();

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> genes = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .collect(toList());

        List<Integer> health = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        for(int i = 0; i < n; i++)
            aho.addString(genes.get(i), health.get(i));
        aho.evalFail();

        int s = Integer.parseInt(bufferedReader.readLine().trim());

        long best = 0, worst = Integer.MAX_VALUE;

        for(int i = 0; i < s; i++) {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int first = Integer.parseInt(firstMultipleInput[0]);

                int last = Integer.parseInt(firstMultipleInput[1]);

                String d = firstMultipleInput[2];
                
                long h = aho.findGenes(first, last, d);
                best = Math.max(best, h);
                worst = Math.min(worst, h);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        };

        System.out.println(worst + " " + best);

        bufferedReader.close();
    }
}

