public class Sorting{

    // inserction sort, complexity O(n^2) which means terrible performance in worst and average case,
    // but in the best case (which we are not interested in) it's O(n)
    public static void insertionSort(char[] data) {
        for (int i = 1; i < data.length; i++) {
            char current = data[i];
            int k = i;
            while(k > 0 && data[k-1] > current){
                data[k] = data[k-1];    // slide right
                k--;                    // consider the previus k
            }
            // no more slices needed
            data[k] = current;
        }
    }
    
}