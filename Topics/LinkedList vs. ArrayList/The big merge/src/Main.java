import java.util.ArrayList;
import java.util.LinkedList;

class ListOperations {
    public static void mergeLists(LinkedList<String> linkedList, ArrayList<String> arrayList) {
        // write your code here
        for (String str: arrayList) {
            linkedList.addLast(str);
        }
        System.out.println("The new size of LinkedList is %s".formatted(linkedList.size()));
    }
}