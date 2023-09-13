package Iterators;

import BinarySearchTree.AbstrTable.Node;
import Stack.AbstrLifo;

import java.util.Iterator;

/**
 *
 * @author 42072
 * @param <T>
 */
public class IteratorStack<T> implements Iterator<T> {

    AbstrLifo<Node> stack;

    public IteratorStack(Node koren) {
        stack = new AbstrLifo<>();
        moveToLeft(koren);
    }

    private void moveToLeft(Node currentNode) {
        while (currentNode != null) {
            stack.push(currentNode);
            currentNode = currentNode.getLeftChild();
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public T next() {
        if (hasNext()) {
            Node currentNode = stack.pop();
            if (currentNode.getRightChild() != null) {
                moveToLeft(currentNode.getRightChild());
            }
            return (T) currentNode;
        } else {
            return null;
        }
    }

}
