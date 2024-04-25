import java.io.*;
import java.util.*;

class Treap<T> {
    T value;
    int size;
    int height;
    Treap<T> left, right;

    public Treap(T value) {
        this.value = value;
        updateSize();
    }

    T first() {
        if(left == null) return value;
        else return left.first();
    }
    
    T last() {
        if(right == null) return value;
        else return right.last();
    }
    
    static<T> int height(Treap<T> t) {
        return t == null ? 0 : t.height;
    }
    
    static<T> int size(Treap<T> t) {
        return t == null ? 0 : t.size;
    }
    
    void updateSize() {
        height = Math.max(height(left), height(right)) + 1;
        size = size(left) + size(right) + 1;    
    }
    
    Treap<T> add(int pos, T x) {
        List<Treap<T>> trees = split2(this, pos);
        assert(trees.size() == 2);
        Treap<T> new_node = new Treap<>(x);
        trees.add(1, new_node);
        return trees.stream().reduce(null, Treap::join);
    }
    
    static <T> List<Treap<T>> split2(Treap<T> t, int pos) {
        List<Treap<T>> result = new ArrayList<>();
        if(t == null) {
            result.add(null);
            result.add(null);
            return result;
        } else if(t.size <= pos) {
            result.add(t);
            result.add(null);
            return result;
        } else if(pos <= 0) {
            result.add(null);
            result.add(t);
            return result;
        }
        
        int lsize = size(t.left);
        if(pos <= lsize) {
            Treap<T> temp = t.left;
            t.left = null;
            result = split2(temp, pos);
            result.set(1, join(result.get(1), t));
        } else {
            Treap<T> temp = t.right;
            t.right = null;
            result = split2(temp, pos - lsize - 1);
            result.set(0, join(t, result.get(0)));
        }
        if(result.get(0) != null) result.get(0).updateSize();
        if(result.get(1) != null) result.get(1).updateSize();
        return result;
    }
    
    static <T> List<Treap<T>> split3(Treap<T> t, int i, int j) {
        List<Treap<T>> trees = split2(t, i);
        Treap<T> temp = trees.get(1);
        trees.remove(1);
        trees.addAll(split2(temp, j - i));
        return trees;
    }
    
    static <T> Treap<T> right_rotate(Treap<T> t) {
        Treap<T> temp = t.left;
        t.left = temp.right;
        t.updateSize();
        temp.right = t;
        temp.updateSize();
        return temp;
    }
    
    static <T> Treap<T> left_rotate(Treap<T> t) {
        Treap<T> temp = t.right;
        t.right = temp.left;
        t.updateSize();
        temp.left = t;
        temp.updateSize();
        return temp;
    }

    static <T> Treap<T> join(Treap<T> left, Treap<T> right) {
        if(left == null) return right;
        else if(right == null) return left;
        
        Treap<T> temp;
        if(left.height < right.height) {
            temp = right;
            right.left = join(left, right.left);
        } else {
            temp = left;
            left.right = join(left.right, right);
        }
        temp.updateSize();

        int diff = height(temp.left) - height(temp.right);
        if(diff > 1) {
            temp = right_rotate(temp);
        } else if (diff < -1) {
            temp = left_rotate(temp);
        }
        return temp;
    }

    Treap<T> back(int i, int j) {
        List<Treap<T>> trees = split3(this, i, j);
        assert(trees.size() == 3);
        Treap<T> mid = trees.get(1);
        trees.remove(1);
        trees.add(mid);
        return trees.stream().reduce(null, Treap::join);
    }
    
    Treap<T> front(int i, int j) {
        List<Treap<T>> trees = split3(this, i, j);
        assert(trees.size() == 3);
        Treap<T> mid = trees.get(1);
        trees.remove(1);
        trees.add(0, mid);
        return trees.stream().reduce(null, Treap::join);
    }
    
    void printInorder() {
        if(left != null) left.printInorder();
        System.out.print(value + " ");
        if(right != null) right.printInorder();
    }
    
    public String toString() {
        return "[" + (left != null ? left.toString() : " ") 
                + "," + value 
                + "," + (right != null ? right.toString() : " ") + "]";
    }
}

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        
        Treap<Integer> treap = new Treap<>(scanner.nextInt());
        for(int i = 1; i < n; i++) {
            treap = treap.add(i, scanner.nextInt());
        }

        for(int k = 0; k < m; k++) {
            int type = scanner.nextInt();
            int i = scanner.nextInt() - 1;
            int j = scanner.nextInt();
            switch(type) {
                case 1: treap = treap.front(i, j); break;
                case 2: treap = treap.back(i, j); break;
            }
        }
        System.out.println(Math.abs(treap.first() - treap.last()));
        treap.printInorder();
        System.out.println();
    }
}

