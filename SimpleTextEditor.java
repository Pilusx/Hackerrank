import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        Stack<String> state = new Stack<String>();
        int q = scanner.nextInt();
        for(int i = 0; i < q; i++) {
            int cmd = scanner.nextInt();
            switch(cmd) {
                case 1:
                    String x = scanner.next();
                    if(state.empty()) state.push(x);
                    else state.push(state.peek() + x);
                    break;
                case 2:
                    int z = scanner.nextInt();
                    String s = state.peek();
                    state.push(s.substring(0, s.length() - z));
                    break;
                case 3:
                    int k = scanner.nextInt();
                    System.out.println(state.peek().charAt(k-1));
                    break;
                case 4:
                    state.pop();
                    break;
            }    
        }
    }
}

