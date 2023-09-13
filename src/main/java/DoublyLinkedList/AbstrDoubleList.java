package DoublyLinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Jero
 * @param <T>
 */

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    private Node<T> firstNode;
    private Node<T> currentNode;

    private static class Node<T> {

        T value;
        Node<T> nextNode;
        Node<T> prevNode;

        Node(T value) {
            this.value = value;
            this.nextNode = null;
            this.prevNode = null;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getNextNode() {
            return nextNode;
        }

        public Node<T> getPrevNode() {
            return prevNode;
        }

    }

    @Override
    public void deleteAll() {
        if (firstNode != null) {
            Node<T> tempNode;
            Node<T> currNode = firstNode.getNextNode();

            while (currNode != firstNode) {
                tempNode = currNode.getNextNode();
                currNode = tempNode;
            }
            this.firstNode = null;
            this.currentNode = null;
        }
    }

    @Override
    public boolean isEmpty() {
        return firstNode == null;
    }

    @Override
    public void insertFirst(T data) {
        Node<T> nodeToInsert = new Node<>(data);

        if (isEmpty()) {
            firstNode = nodeToInsert;
            nodeToInsert.prevNode = nodeToInsert;
            nodeToInsert.nextNode = nodeToInsert;
        } else {
            Node<T> lastNode = firstNode.getPrevNode();
            nodeToInsert.nextNode = firstNode;
            nodeToInsert.prevNode = lastNode;
            lastNode.nextNode = nodeToInsert;
            nodeToInsert.nextNode.prevNode = nodeToInsert;
            firstNode = nodeToInsert;
        }
        currentNode = nodeToInsert;
    }

    @Override
    public void insertLast(T data) {
        Node<T> nodeToInsert = new Node<>(data);

        if (isEmpty()) {
            firstNode = nodeToInsert;
            nodeToInsert.prevNode = nodeToInsert;
            nodeToInsert.nextNode = nodeToInsert;
        } else {
            Node<T> lastNode = firstNode.prevNode;
            nodeToInsert.nextNode = firstNode;
            nodeToInsert.prevNode = lastNode;
            lastNode.nextNode = nodeToInsert;
            firstNode.prevNode = nodeToInsert;
        }
        currentNode = nodeToInsert;
    }

    @Override
    public void insertNext(T data) {
        Node<T> nodeToInsert = new Node<>(data);

        if (isEmpty()) {
            firstNode = nodeToInsert;
            nodeToInsert.prevNode = nodeToInsert;
            nodeToInsert.nextNode = nodeToInsert;
        } else {
            Node<T> tempNode = currentNode;
            nodeToInsert.nextNode = tempNode.nextNode;
            tempNode.nextNode = nodeToInsert;
            tempNode.nextNode.prevNode = nodeToInsert;
            nodeToInsert.prevNode = tempNode;
            firstNode.prevNode = nodeToInsert;
        }
        currentNode = nodeToInsert;
    }

    @Override
    public void insertPrev(T data) {
        Node<T> node = new Node<>(data);

        if (isEmpty()) {
            firstNode = node;
            node.prevNode = node;
            node.nextNode = node;
        } else {
            Node<T> tempNode = currentNode;
            node.prevNode = tempNode.prevNode;
            tempNode.prevNode.nextNode = node;
            node.nextNode = tempNode;
            tempNode.prevNode = node;
        }
        currentNode = node;
    }

    @Override
    public T getCurrent() {
        return currentNode.value;
    }

    @Override
    public T getFirst() {
        currentNode = firstNode;
        return currentNode.value;
    }

    @Override
    public T getLast() {
        currentNode = firstNode.prevNode;
        return currentNode.value;
    }

    @Override
    public T getNext() {
        Node<T> tempNode = currentNode;
        currentNode = tempNode.nextNode;
        return currentNode.value;
    }

    @Override
    public T getPrev() {
        Node<T> tempNode = currentNode;
        currentNode = tempNode.prevNode;
        return currentNode.value;
    }

    @Override
    public T deleteCurrent() {
        Node<T> tempNode = currentNode;

        if (tempNode.nextNode == currentNode) {
            deleteAll();
        } else if (currentNode == firstNode) {
            tempNode.prevNode.nextNode = tempNode.nextNode;
            tempNode.nextNode.prevNode = tempNode.prevNode;
            firstNode = tempNode.nextNode;
            currentNode = tempNode.nextNode;
        } else {
            tempNode.prevNode.nextNode = tempNode.nextNode;
            tempNode.nextNode.prevNode = tempNode.prevNode;
            currentNode = tempNode.nextNode;
        }
        return tempNode.value;
    }

    @Override
    public T deleteFirst() {
        Node<T> tempNode = firstNode;

        if (isEmpty()) {
            return null;
        } else if (tempNode.nextNode == firstNode) {
            deleteAll();
            return tempNode.value;
        }

        firstNode.prevNode.nextNode = tempNode.nextNode;
        firstNode.nextNode.prevNode = tempNode.prevNode;
        firstNode = tempNode.nextNode;
        currentNode = firstNode;
        if (tempNode == firstNode.nextNode) {
            deleteAll();
        }
        return tempNode.value;

    }

    @Override
    public T deleteLast() {
        Node<T> tempNode = firstNode.prevNode;
        if (isEmpty()) {
            return null;
        } else if (tempNode == tempNode.nextNode) {
            deleteAll();
            return tempNode.value;
        } else {
            firstNode.prevNode = tempNode.prevNode;
            tempNode.prevNode.nextNode = firstNode;
            currentNode = firstNode;
            return tempNode.value;
        }
    }

    @Override
    public T deleteNext() {
        Node<T> tempNode = currentNode;
        Node<T> nodeToDelete = tempNode.nextNode;
        if (isEmpty()) {
            return null;
        } else {
            tempNode.nextNode.nextNode.prevNode = tempNode;
            tempNode.nextNode = tempNode.nextNode.nextNode;
        }
        currentNode = tempNode.nextNode.nextNode;
        return nodeToDelete.value;
    }

    @Override
    public T deletePrev() {
        Node<T> tempNode = currentNode;
        Node<T> nodeToDelete = tempNode.prevNode;

        if (isEmpty()) {
            return null;
        } else {
            tempNode.prevNode.prevNode.nextNode = tempNode;
            tempNode.prevNode = tempNode.prevNode.prevNode;
        }
        currentNode = tempNode;
        return nodeToDelete.value;
    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<>() {

            Node<T> currentNode = null;
            boolean isIterable = true;

            @Override
            public boolean hasNext() {
                if (isEmpty()) {
                    return false;
                }
                if (currentNode == firstNode.prevNode) {
                    isIterable = false;
                }
                return isIterable && !isEmpty();

            }

            @Override
            public T next() {
                if (currentNode == null && firstNode != null) {
                    currentNode = firstNode;
                    return firstNode.getValue();
                } else if (currentNode != null) {
                    currentNode = currentNode.getNextNode();
                    return currentNode.getValue();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}