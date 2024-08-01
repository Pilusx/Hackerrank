/* Hidden stub code will pass a root argument to the function below. Complete the function to solve the challenge. Hint: you may want to write one or more helper functions.  

The Node class is defined as follows:
    class Node {
        int data;
        Node left;
        Node right;
     }
*/
    boolean checkBST2(Node root, int min, int max) {
        if(root == null) return true;
        if(min < root.data && root.data < max) {
            return checkBST2(root.left, min, root.data) && checkBST2(root.right, root.data, max);
        } else {
            return false;
        }
    }

    boolean checkBST(Node root) {
        return checkBST2(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
