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
    private int sum;
    private Map<Character, TrieNode> children;
    
    public TrieNode() {
        children = new HashMap<Character, TrieNode>();
        sum = 0;
    }
    
    public void add(String s) {
        sum++;
        if(s.isEmpty()) return;
        if(!children.containsKey(s.charAt(0)))
            children.put(s.charAt(0), new TrieNode());
        
        children.get(s.charAt(0)).add(s.substring(1));
    }
    
    public int find(String s) {
        if(s.isEmpty()) return sum;
        if(children.containsKey(s.charAt(0))) 
            return children.get(s.charAt(0)).find(s.substring(1));
        else
            return 0;
    }
}


class Result {

    /*
     * Complete the 'contacts' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts 2D_STRING_ARRAY queries as parameter.
     */

    public static List<Integer> contacts(List<List<String>> queries) {
        TrieNode trie = new TrieNode();
        List<Integer> result = new ArrayList<>();
        for(List<String> query : queries) {
            switch(query.get(0)) {
                case "add": trie.add(query.get(1)); break;
                default: result.add(trie.find(query.get(1))); break;
            }
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int queriesRows = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<String>> queries = new ArrayList<>();

        IntStream.range(0, queriesRows).forEach(i -> {
            try {
                queries.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> result = Result.contacts(queries);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining("\n"))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}

