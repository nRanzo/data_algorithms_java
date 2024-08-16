package priorityqueue;

import java.util.Comparator;

public class StringLengthComparator<E> implements Comparator<String>{
    // compares two strings based on their length
    public int compare(String a, String b) {
        if(a.length() < b.length())
            return -1;
        else if(a.length() == b.length())
            return 0;
        // otherwise it definitely is a.length() > b.length()
        return 1;
    }
}
