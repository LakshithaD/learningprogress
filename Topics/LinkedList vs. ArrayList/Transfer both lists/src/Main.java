import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

class ListOperations {
    public static void transferAllElements(LinkedList<String> linkedList, ArrayList<String> arrayList) {
        // write your code here
        ArrayList<String> tmp = new ArrayList<>(arrayList);
        Collections.copy(arrayList, linkedList);
        Collections.copy(linkedList, tmp);
    }
}