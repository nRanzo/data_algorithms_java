import stack_queue.Deque;
import stack_queue.LinkedDeque;

public class Josephus {
    /**
     * Determines the winner of the Josephus problem using a deque.
     * @param deque The HDeque instance representing the participants.
     * @param k The step count after which a participant is removed.
     * @param <E> The type of elements in the deque.
     * @return The last remaining element (the winner).
     */
    public static <E> E simulate(Deque<E> deque, int k) {
        if (deque.isEmpty()) {
            return null;
        }
        while (deque.size() > 1) {
            for (int i = 0; i < k - 1; i++) {
                // Simulate rotation by moving the first element to the end
                deque.addLast(deque.removeFirst());
            }
            // Remove and print the k-th item
            E e = deque.removeFirst();
            System.out.println(" " + e + " is out");
        }
        // Return the last remaining element
        return deque.removeFirst();
    }

    /**
     * Constructs a deque from an array of objects.
     * @param a The array of elements to add to the deque.
     * @param <E> The type of elements in the array.
     * @return A HDeque instance containing the elements of the array.
     */
    public static <E> Deque<E> buildDeque(E[] a) {
        Deque<E> deque = new LinkedDeque<>();
        for (E element : a) {
            deque.addLast(element);
        }
        return deque;
    }

    /**
     * Testing method.
     */
    public static void main(String[] args) {
        String[] s1 = {"Alice", "Brian", "Clara", "Dom", "Ed", "Friener"};
        String[] s2 = {"Gian", "Hope", "Ilary", "John", "Kim", "Laura"};
        String[] s3 = {"Mike", "Roberto"};
        System.out.println("First winner is " + simulate(buildDeque(s1), 3));
        System.out.println("Second winner is " + simulate(buildDeque(s2), 10));
        System.out.println("Third winner is " + simulate(buildDeque(s3), 7));
    }
}