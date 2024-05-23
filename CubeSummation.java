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

class Result {

    /*
     * Complete the 'cubeSum' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. STRING_ARRAY operations
     */

    public static List<Long> cubeSum(int n, List<String> operations) {
        int[][][] t = new int[n+1][n+1][n+1];
        List<Long> result = new ArrayList<>();
        
        for(String op : operations) {
            Scanner scanner = new Scanner(op);
            switch(scanner.next()) {
            case "UPDATE":
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int z = scanner.nextInt();
                int W = scanner.nextInt();
                t[x][y][z] = W;
                break;
            case "QUERY":
                int x1 = scanner.nextInt();
                int y1 = scanner.nextInt();
                int z1 = scanner.nextInt();
                int x2 = scanner.nextInt();
                int y2 = scanner.nextInt();
                int z2 = scanner.nextInt();
                long r = 0;
                for(int x_ = x1; x_ <= x2; x_++) {
                    for(int y_ = y1; y_ <= y2; y_++) {
                        for(int z_ = z1; z_ <= z2; z_++) {
                            r += t[x_][y_][z_];
                        }
                    }
                }
                result.add(r);
                break;
            default: break;
            }
        }
        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int T = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, T).forEach(TItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int matSize = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                List<String> ops = IntStream.range(0, m).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                    .collect(toList());

                List<Long> res = Result.cubeSum(matSize, ops);

                bufferedWriter.write(
                    res.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                    + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

