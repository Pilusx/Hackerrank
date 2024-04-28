	/* Class node is defined as :
    class Node 
    	int val;	//Value
    	int ht;		//Height
    	Node left;	//Left child
    	Node right;	//Right child

	*/

    static int height(Node root) {
        return root == null ? -1 : root.ht;
    }
    
    static void updateHeight(Node root) {
        if(root == null) return;
        root.ht = Math.max(height(root.left), height(root.right)) + 1;        
    }

    static int balance(Node root) {
        if(root == null) return 0;
        return height(root.left) - height(root.right);
    }

    static Node right_rotate(Node root) {
        Node temp = root.left;
        root.left = temp.right;
        temp.right= root;
        updateHeight(root);
        updateHeight(temp);
        return temp;
    }

    static Node left_rotate(Node root) {
        Node temp = root.right;
        root.right = temp.left;
        temp.left = root;
        updateHeight(root);
        updateHeight(temp);
        return temp;
    }

    static Node insert(Node root,int val) {
        Node node;
        if(root == null) {
            node = new Node();
            node.val = val;
            updateHeight(node);
            return node;
        }
        
        if(val < root.val) {
            root.left = insert(root.left, val);
        } else if(val > root.val) {
            root.right = insert(root.right, val);
        }
        updateHeight(root);
     
        int balance = balance(root);
        if(val == root.val) return root;
        else if (balance > 1) {
            if(balance(root.left) < 0) root.left = left_rotate(root.left);
            return right_rotate(root);
        } else if (balance < -1) {
            if(balance(root.right) > 0)  root.right = right_rotate(root.right);
            return left_rotate(root);
        }
        
        return root;
}
