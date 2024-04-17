import java.util.*;
import java.io.*;

class Node {
    Node left;
    Node right;
    int data;
    
    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class Solution {

	/* 
    
    class Node 
    	int data;
    	Node left;
    	Node right;
	*/
	public static void topView(Node root) {
        Queue<Node> nodes = new LinkedList<>();
        Map<Integer, Integer> map = new TreeMap<>();
        Map<Node, Integer> width = new HashMap<>();
        
        width.put(root, 0);
        nodes.add(root);
        
        while(!nodes.isEmpty()) {
            Node x = nodes.poll();
            int w = width.get(x);
            if(!map.containsKey(w)) map.put(w, x.data);
            
            if(x.left != null) {
                nodes.add(x.left);
                width.put(x.left, w - 1);
            }
            if(x.right != null) {
                nodes.add(x.right);
                width.put(x.right, w + 1);
            }
        }
        for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.print(entry.getValue() + " ");
        }
    }

	public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        topView(root);
    }	
}
