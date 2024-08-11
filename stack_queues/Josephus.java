public class Josephus {

    /**
     * Determines the winner of the Josephus problem using a circular queue.
     * @param queue The CircularQueue instance representing the participants.
     * @param k The step count after which a participant is removed.
     * @param <E> The type of elements in the queue.
     * @return The last remaining element (the winner).
     */
    public static <E> E Josephus(HCircularQueue<E> queue, int k) {
        if (queue.isEmpty()) {
            return null;
        }
    
        while (queue.size() > 1) {
            int count = 0; // Initialize count of non-null items
            while (count < k - 1) {
                // Rotate until we skip k-1 non-null items
                queue.rotate();
                if (queue.first() != null) {
                    count++;
                }
            }
    
            // Remove and print the k-th non-null item
            E e = queue.dequeue();  // Remove the element
            System.out.println("   " + e + " is out");
            
            // If the queue becomes empty after removal, handle it gracefully
            if (queue.isEmpty()) {
                return null;
            }
        }
    
        // Return the last remaining element
        return queue.dequeue();
    }
    

    /**
     * Constructs a circular queue from an array of objects.
     * @param a The array of elements to add to the queue.
     * @param <E> The type of elements in the array.
     * @return A CircularQueue instance containing the elements of the array.
     */
    public static <E> HCircularQueue<E> buildQueue(E[] a) {
        HCircularQueue<E> queue = new LinkedCircularQueue<>();
        for (E element : a) {
            queue.enqueue(element);
        }
        return queue;
    }

    /**
     * Testing method.
     */
    public static void main(String[] args) {
        String[] s1 = {"Alice", "Brian", "Clara", "Dom", "Ed", "Friener"};
        String[] s2 = {"Gian", "Hope", "Ilary", "John", "Kim", "Laura"};
        String[] s3 = {"Mike", "Roberto"};

        System.out.println("First winner is " + Josephus(buildQueue(s1), 3));
        System.out.println("Second winner is " + Josephus(buildQueue(s2), 10));
        System.out.println("Third winner is " + Josephus(buildQueue(s3), 7));
    }
}
