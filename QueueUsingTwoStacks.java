import java.io.*;
import java.util.*;


public class Solution {
    static class Queue<T> {
        private Stack<T> stack1;
        private Stack<T> stack2;
        public Queue() {
            stack1 = new Stack<T>();
            stack2 = new Stack<T>();
        }
        public void enqueue(T x) {
            stack1.push(x);
        }
        private void move() {
            if(stack2.empty()) {
                while(!stack1.empty()) {
                    stack2.push(stack1.peek());
                    stack1.pop();
                }
            }
        }
        public void dequeue() {
            move();
            if(!stack2.empty()) stack2.pop();
        }
        public T peek() {
            move();
            return stack2.peek();
        }
    }


    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        int q = scanner.nextInt();
        Queue<Integer> queue = new Queue<Integer>();
        for(int i = 0; i < q; i++) {
            int type = scanner.nextInt();
            int x;
            switch(type) {
                case 1:
                    x = scanner.nextInt();
                    queue.enqueue(x);
                    break;
                case 2:
                    queue.dequeue();
                    break;
                case 3:
                    x = queue.peek();
                    System.out.println(x);
                    break;
                default:
                    break;
            }
        }
    }
}

