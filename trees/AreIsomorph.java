import lists.Position;

public class AreIsomorph {

    public static <E> boolean areIsomorph (LinkedBinaryTree<E> T1, LinkedBinaryTree<E> T2)  {

        boolean value = false;

        try {
            if(T1 == null || T2 == null)
                throw new IllegalArgumentException();
            
            if(T1.size() != T2.size())
                return false;
            
            if(T1.isEmpty() && T2.isEmpty())
                return true;
            
            value = areIsomorph(T1, T2, T1.root(), T2.root());
        }
        catch(IllegalArgumentException | InvalidPositionException e) {
            throw new RuntimeException("areIsomorph failed with error: " + e.getMessage(), e);  // Corrected exception handling
        }
        return value;
    }
    
    private static <E> boolean areIsomorph (LinkedBinaryTree<E> T1, LinkedBinaryTree<E> T2, Position<E> p1, Position<E> p2) throws InvalidPositionException {
    
        if(T1.isExternal(p1) && T2.isExternal(p2))
            return true;
        
        if(T1.hasLeft(p1) && T2.hasLeft(p2) && T1.hasRight(p1) && T2.hasRight(p2))
            return areIsomorph(T1, T2, T1.left(p1), T2.left(p2)) &&  areIsomorph(T1, T2, T1.right(p1), T2.right(p2));
        
        // altrimenti sicuramente non sono propri e quindi nemmeno isomorfi
        // basta il seguente false per rendere falsa la catena di restituzione &&
        return false;
    }
}