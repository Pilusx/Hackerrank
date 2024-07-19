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
    int value = 0, size = 1, height = 1;
    Node left = null, right = null;
    Node(int value) {
        this.value = value;
    }
    
    static int height(Node root) {
        if(root == null) return 0;
        else return root.height;
    }
    
    static int size(Node root) {
        if(root == null) return 0;
        else return root.size;
    }
    
    static void update(Node root) {
        if(root == null) return;
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        root.size = size(root.left) + 1 + size(root.right);
    }
    
    static Node right_rotate(Node root) {
        Node temp = root.left;
        root.left = temp.right;
        temp.right = root;
        update(root);
        update(temp);
        return temp;
    }

    static Node left_rotate(Node root) {
        Node temp = root.right;
        root.right = temp.left;
        temp.left = root;
        update(root);
        update(temp);
        return temp;
    }
    
    static int balance(Node root) {
        if(root == null) return 0;
        return height(root.left) - height(root.right);
    }
    
    static Node insert(Node root, int value) {
        if(root == null) return new Node(value);
        if(value < root.value) root.left = insert(root.left, value);
        else root.right = insert(root.right, value);
        update(root);
        
        int balance = balance(root);
        if (balance > 1) {
            if(balance(root.left) < 0) root.left = left_rotate(root.left);
            return right_rotate(root);
        } else if (balance < -1) {
            if(balance(root.right) > 0)  root.right = right_rotate(root.right);
            return left_rotate(root);
        }
        
        return root;
    }
    
    static int bigger(Node root, int value) {
        if(root == null) return 0;
        if(root.value <= value) return bigger(root.right, value);
        else return bigger(root.left, value) + 1 + size(root.right);
    }
}

class Result {

    /*
     * Complete the 'insertionSort' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static long insertionSort(List<Integer> arr) {
        Node root = new Node(arr.get(0));
        long sum = 0;
        for(int i = 1; i < arr.size(); i++) {
            int x = arr.get(i);
            sum += Node.bigger(root, x);
            root = Node.insert(root, x);
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
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                long result = Result.insertionSort(arr);

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

