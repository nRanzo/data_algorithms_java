package trees;

import java.util.ArrayList;
import java.util.List;

import lists.Position;

public class TreeNode<E> implements Position<E> {
    private E element;
    private TreeNode<E> parent;
    private List<TreeNode<E>> children;

    public TreeNode(E element, TreeNode<E> parent) {
        this.element = element;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    @Override
    public E getElement() throws IllegalStateException {
        if (element == null) {
            throw new IllegalStateException("Position no longer valid.");
        }
        return element;
    }

    public TreeNode<E> getPrev() {
        // For a general tree, previous and next are not inherently meaningful,
        // so we return null, or this could be customized as needed.
        return null;
    }

    public TreeNode<E> getNext() {
        // Similar to getPrev, this will depend on your specific use case.
        return null;
    }

    public TreeNode<E> getParent() {
        return parent;
    }

    public void setParent(TreeNode<E> parent) {
        this.parent = parent;
    }

    public List<TreeNode<E>> getChildren() {
        return children;
    }

    public void addChild(TreeNode<E> child) {
        children.add(child);
    }

    public E replaceElement(E newElement) {
        E oldElement = this.element;
        this.element = newElement;
        return oldElement;
    }
}
