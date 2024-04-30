import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

class MedianQueue {
    private PriorityQueue<Integer> minQueue = new PriorityQueue<>();
    private PriorityQueue<Integer> maxQueue = new PriorityQueue<>((x, y) -> y.compareTo(x));
    private boolean lastOp = true;

    private void balance() {
        while(maxQueue.size() > minQueue.size()) {
            minQueue.add(maxQueue.poll());
        }
        while(minQueue.size() > maxQueue.size() + 1) {
            maxQueue.add(minQueue.poll());
        }
    }

    void add(int x) {
        lastOp = true;
        if(minQueue.isEmpty() || x < minQueue.peek()) maxQueue.add(x);
        else minQueue.add(x);
        balance();
    }
    
    void remove(int x) {
        lastOp = true;
        if (maxQueue.contains(x)) maxQueue.remove(x);
        else if(minQueue.contains(x)) minQueue.remove(x);
        else lastOp = false;
        balance();
    }
    
    String median() {
        if(!lastOp || (minQueue.isEmpty() && maxQueue.isEmpty())) 
            return "Wrong!";
        if(minQueue.size() == maxQueue.size()) {
            long sum = (long)minQueue.peek() + maxQueue.peek();
            return (sum == -1 ? "-" : "") 
                    + String.valueOf(sum/2) 
                    + (Math.abs(sum % 2) == 1 ? ".5" : "");
        } else 
            return String.valueOf(minQueue.peek());
    }
}

class Solution{
    /* Head ends here*/
    static void median(String a[],int x[]) {
        MedianQueue q = new MedianQueue();
        for(int i = 0; i < x.length; i++) {
            switch(a[i]) {
            case "a": q.add(x[i]); break;
            case "r": q.remove(x[i]); break;
            }
            System.out.println(q.median());
        }
       
    }
    
    /* Tail starts here*/
    
   public static void main( String args[] ){
      

      Scanner in = new Scanner(System.in);
      
      int N;
      N = in.nextInt();
   
      String s[] = new String[N];
      int x[] = new int[N];
      
      for(int i=0; i<N; i++){
         s[i] = in.next();
         x[i] = in.nextInt();
      }
   
      median(s,x);
    }
}

