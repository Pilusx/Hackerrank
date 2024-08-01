import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        
        int q = scanner.nextInt();
        for(int i = 0; i < q; i++) {
            int a = scanner.nextInt();
            int v = 0;
            if(a != 3) v = scanner.nextInt();
            switch(a) {
                case 1: queue.add(v); break;
                case 2: queue.remove(v); break;
                case 3: System.out.println(queue.peek()); break;
            }
        }
    }
}

