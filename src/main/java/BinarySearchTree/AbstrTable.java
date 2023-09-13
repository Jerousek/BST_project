package BinarySearchTree;

import Enums.eTraversal;
import Iterators.IteratorQueue;
import Iterators.IteratorStack;

import java.util.Iterator;

/**
 *
 * @author 42072
 * @param <K>
 * @param <V>
 */
public class AbstrTable<K, V> implements IAbstrTable {

    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public class Node<T> implements Comparable<K> {

        private Node<T> leftChild;
        private Node<T> rightChild;

        private V value;
        private K key;

        public Node(V value, K key) {
            this.key = key;
            this.value = value;
        }

        public Node<T> getLeftChild() {
            return leftChild;
        }

        public Node<T> getRightChild() {
            return rightChild;
        }

        public V getValue() {
            return value;
        }

        public void setLeftChild(Node<T> leftChild) {
            this.leftChild = leftChild;
        }

        public void setRightChild(Node<T> rightChild) {
            this.rightChild = rightChild;
        }

        public K getKey() {
            return key;
        }

        @Override
        public String toString() {
            return value.toString();
        }

        @Override
        public int compareTo(K key) {
            return Integer.compare(key.toString().hashCode(), this.key.toString().hashCode());
        }
    }

    @Override
    public boolean deleteAll() {
        if (root == null) {
            return false;
        }
        root = null;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    //projde všechny prvky, když se ve stromu nenachází zadaná hodnota, vrací null
    public V findByKey(Object key) {
        Node currentNode = root;
        while (currentNode != null) {
            if (currentNode.compareTo(key) > 0) {
                currentNode = currentNode.rightChild;
            } else if (currentNode.compareTo(key) < 0) {
                currentNode = currentNode.leftChild;
            } else {
                return (V) currentNode.value;
            }
        }
        return null;
    }

    @Override
    //vloží prvek na pozici podle klíče
    public void insert(Object key, Object value) {
        Node newNode = new Node((V) value, (K) key);

        if (root == null) {
            root = newNode;
        } else {
            Node currentNode = root;
            Node parrentNode;
            while (true) {
                parrentNode = currentNode;
                if (currentNode.compareTo(key) <= 0) {
                    currentNode = currentNode.leftChild;
                    if (currentNode == null) {
                        parrentNode.leftChild = newNode;
                        return;
                    }
                } else {
                    currentNode = currentNode.rightChild;
                    if (currentNode == null) {
                        parrentNode.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }

    @Override
    //odeber příslušný prvek
    public V delete(Object key) {
        Node currentNode = root;
        Node prevNode = null;

        while (currentNode != null && currentNode.compareTo(key) != 0) {
            prevNode = currentNode;
            if (currentNode.compareTo(key) < 0) {
                currentNode = currentNode.leftChild;
            } else {
                currentNode = currentNode.rightChild;
            }
        }

        //kdyz je poseldni prvek ke smazani koren, zrusi strom
        if (currentNode.leftChild == null && currentNode.rightChild == null && root.compareTo(key) == 0) {
            V vypis = (V) currentNode.value;
            deleteAll();
            return vypis;

            //kdyz je prvek ke smazani koren, ale nema alespon jednoho, najde nahradnika,
            //ktery koren nahradí a pak nahradnika rekurzivne smaze
        }
        else if ((currentNode.leftChild == null || currentNode.rightChild == null) && root.compareTo(key) == 0) {
            V returnNodeValue = (V) currentNode.value;
            Node replacementNode;
            Node parrentNode;
            V replacementNodeValue;
            K replacementNodeKey;
            if (root.leftChild != null) {
                replacementNode = root.leftChild;
                replacementNodeValue = (V) replacementNode.value;
                replacementNodeKey = (K) replacementNode.key;
                parrentNode = root;
                while (replacementNode.rightChild != null) {
                    parrentNode = replacementNode;
                    replacementNode = replacementNode.rightChild;
                    replacementNodeValue = (V) replacementNode.value;
                    replacementNodeKey = (K) replacementNode.key;
                }
            }
            else {
                replacementNode = root.rightChild;
                replacementNodeValue = (V) replacementNode.value;
                replacementNodeKey = (K) replacementNode.key;
                parrentNode = root;
                while (replacementNode.leftChild != null) {
                    parrentNode = replacementNode;
                    replacementNode = replacementNode.leftChild;
                    replacementNodeValue = (V) replacementNode.value;
                    replacementNodeKey = (K) replacementNode.key;
                }
            }
            this.delete(replacementNode.key);
            root.value = replacementNodeValue;
            root.key = replacementNodeKey;
            return returnNodeValue;
            //prvek urceny ke smazani neni koren a ma alespon 1 potomka
        }
        else if (currentNode.leftChild == null || currentNode.rightChild == null) {
            Node newNode;
            if (currentNode.leftChild == null) {
                newNode = currentNode.rightChild;
            } else {
                newNode = currentNode.leftChild;
            }
            if (prevNode == null) {
                return (V) newNode.value;
            }
            if (currentNode == prevNode.leftChild) {
                prevNode.leftChild = newNode;
            } else {
                prevNode.rightChild = newNode;
            }
            //prvek ma dva potomky
        }
        else {
            Node parrentNode = null;
            Node tempNode;
            V vypis = (V) currentNode.value;
            tempNode = currentNode.rightChild;
            while (tempNode.leftChild != null) {
                parrentNode = tempNode;
                tempNode = tempNode.leftChild;
            }
            if (parrentNode != null) {
                parrentNode.leftChild = tempNode.rightChild;
            } else {
                currentNode.rightChild = tempNode.rightChild;
            }

            currentNode.value = tempNode.value;
            currentNode.key = tempNode.key;
            return vypis;

        }
        return (V) currentNode.value;
    }



    public Iterator<Node> createIterator(eTraversal typ) {
        switch (typ) {
            case VALUE -> {
                return new IteratorQueue(root);
            }
            case REFERENCE -> {
                return new IteratorStack(root);
            }
            default ->
                    throw new AssertionError();
        }
    }
}