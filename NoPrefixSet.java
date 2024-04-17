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

class TrieNode {
    public boolean bomb;
    public Map<Character, TrieNode> children;
    public TrieNode() {
        children = new HashMap<Character, TrieNode>();
        bomb = false;
    }
    public boolean insert(String s, boolean inserted) {
        if(bomb) return true;
        if(s.isEmpty()) {
            bomb = true;
            return !inserted;
        }
        if(!children.containsKey(s.charAt(0))) {
            children.put(s.charAt(0), new TrieNode());
            inserted = true;
        }
        return children.get(s.charAt(0)).insert(s.substring(1), inserted);
    }
}

class Result {


    /*
     * Complete the 'noPrefix' function below.
     *
     * The function accepts STRING_ARRAY words as parameter.
     */

    public static void noPrefix(List<String> words) {
    // Write your code here
        TrieNode node = new TrieNode();
        for(String s : words) {
            boolean result = node.insert(s, false);
            if(result) {
                System.out.println("BAD SET");
                System.out.println(s);
                return;
            }
        }
        System.out.println("GOOD SET");
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> words = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        Result.noPrefix(words);

        bufferedReader.close();
    }
}

