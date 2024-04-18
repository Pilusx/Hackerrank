import java.io.*;
import java.util.*; 

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] list = line.split(";");
            
            int start = 0;
            StringBuilder builder = new StringBuilder();
            String match = list[0] + list[1];
            switch(match) {
            case "SM":
            case "SC":
            case "SV":
                for(int i = 0; i < list[2].length(); i++) {
                    if(Character.isUpperCase(list[2].charAt(i))) {
                        builder.append(list[2].substring(start, i).toLowerCase());
                        if(i != 0)builder.append(" ");
                        start = i;
                    }
                }
                builder.append(list[2].substring(start, list[2].length() + ("SM".equals(match) ? -2 : 0)).toLowerCase());
                break;
            case "CM":
            case "CC":
            case "CV":
                String[] strings = list[2].split(" ");
                for(int i = 0; i < strings.length; i++) {
                    if(i != 0 || "CC".equals(match)) 
                        strings[i] = strings[i].substring(0, 1).toUpperCase() + strings[i].substring(1).toLowerCase();
                    else strings[i] = strings[i].toLowerCase();
                    builder.append(strings[i]);
                }
                if("CM".equals(match)) builder.append("()");
                break;
            }
            System.out.println(builder.toString());
        }
    }
}

