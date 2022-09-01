import java.util.*;

class ListOperations {
    public static void removeTheSame(LinkedList<String> linkedList, ArrayList<String> arrayList) {
        // write your code here
        ArrayList<String> tmp = new ArrayList<>(arrayList);
        for (String element: tmp) {
            if (linkedList.contains(element)) {
                arrayList.remove(element);
                linkedList.remove(element);
            }
        }
    }
}