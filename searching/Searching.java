public class Searching {
    
    // binSearch but iterative, if you don't have space this is a good solution
    // it keeps the O(logn) time complexity, but if does NOT returns the correct if data are NOT sorted
    // so you may call mergeSort before or just use a more direct linear search if you are ok with it
    public static boolean binarySearchIterative(int[] data, int target) {
        int low = 0;
        int high = data.length - 1;
        while(low < high){
            int mid = (low + high) / 2;
            if(target == data[mid])
                return true;
            else if(target < data[mid])
                high = mid - 1;
            else
                low = mid + 1;
        }
        // if we arrive it implies target not found
        return false;
    }
}
