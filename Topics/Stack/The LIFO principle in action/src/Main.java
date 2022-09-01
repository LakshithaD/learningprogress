import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < count; i++) {
            deque.push(scanner.nextInt());
        }
        for (int i = 0; i < count; i++) {
            System.out.println(deque.pop());
        }
    }
}