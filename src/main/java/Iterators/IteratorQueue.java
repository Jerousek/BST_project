package Iterators;

import BinarySearchTree.AbstrTable.Node;
import Queue.AbstrFifo;

import java.util.Iterator;

/**
 *
 * @author 42072
 * @param <T>
 */
public class IteratorQueue<T> implements Iterator<T> {

    AbstrFifo<Node> queue;

    public IteratorQueue(Node koren) {
        queue = new AbstrFifo<>();
        moveToLeft(koren);
    }

    private void moveToLeft(Node currentNode) {
        while (currentNode != null) {
            queue.enqueue(currentNode);
            currentNode = currentNode.getLeftChild();
        }
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public T next() {
        if (hasNext()) {
            Node currentNode = queue.dequeue();
            if (currentNode.getRightChild() != null) {
                moveToLeft(currentNode.getRightChild());
            }

            return (T) currentNode.getValue();
        } else {
            return null;
        }
    }

}