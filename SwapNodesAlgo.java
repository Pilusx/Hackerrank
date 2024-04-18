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
    int id = 0;
    int depth = 0;
    Node left = null, right = null;
}


class Result {

    /*
     * Complete the 'swapNodes' function below.
     *
     * The function is expected to return a 2D_INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY indexes
     *  2. INTEGER_ARRAY queries
     */
     
    public static void inorder(Node node, List<Integer> array) {
        if(node == null) return;
        inorder(node.left, array);
        array.add(node.id);
        inorder(node.right, array);
    }
    
    public static void swap(Node node, int k) {
        if(node == null) return;
        if(node.depth % k == 0) {
            Node temp = node.left;
            node.left = node.right;
            node.right = temp;
        }
        swap(node.left, k);
        swap(node.right, k);
    }
    
    public static void calculateDepths(Node[] nodes) {
        Queue<Node> queue = new LinkedList<>();
        nodes[1].depth = 1;
        queue.add(nodes[1]);
        
        while(!queue.isEmpty()) {
            Node u = queue.poll();
            if(u.left != null) {
                u.left.depth = u.depth + 1;
                queue.add(u.left);
            }
            if(u.right != null) {
                u.right.depth = u.depth + 1;
                queue.add(u.right);
            }
        }
    }

    public static List<List<Integer>> swapNodes(List<List<Integer>> indexes, List<Integer> queries) {
        List<List<Integer>> result = new ArrayList<>();
        Node[] nodes = new Node[indexes.size()+1];
        for(int i = 1; i <= indexes.size(); i++) {
            nodes[i] = new Node();
        }
        for(int i = 1; i <= indexes.size(); i++) {
            List<Integer> index = indexes.get(i-1);
            nodes[i].id = i;
            nodes[i].left = index.get(0) != -1 ? nodes[index.get(0)] : null;
            nodes[i].right = index.get(1) != -1 ? nodes[index.get(1)] : null;
        }
        
        calculateDepths(nodes);
        for(Integer k : queries) {
            swap(nodes[1], k);
            List<Integer> partial_result = new ArrayList<>();
            inorder(nodes[1], partial_result);
            result.add(partial_result);
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> indexes = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                indexes.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int queriesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> queries = IntStream.range(0, queriesCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        List<List<Integer>> result = Result.swapNodes(indexes, queries);

        result.stream()
            .map(
                r -> r.stream()
                    .map(Object::toString)
                    .collect(joining(" "))
            )
            .map(r -> r + "\n")
            .collect(toList())
            .forEach(e -> {
                try {
                    bufferedWriter.write(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

