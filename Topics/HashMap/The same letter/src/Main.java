import java.util.*;


class MapFunctions {

    public static void printWithSameLetter(Map<String, String> map) {
        // write your code
        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            if (entry.getKey().charAt(0) == entry.getValue().charAt(0)) {
                System.out.println("%s %s".formatted(entry.getKey(), entry.getValue()));
            }
        }
    }
}

/* Do not change code below */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> map = new HashMap<>();

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String[] pair = s.split(" ");
            map.put(pair[0], pair[1]);
        }

        MapFunctions.printWithSameLetter(map);
    }
}