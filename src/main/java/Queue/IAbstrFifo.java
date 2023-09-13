package Queue;

import java.util.Iterator;

/**
 *
 * @author Jero
 * @param <T>
 */
public interface IAbstrFifo<T> {

    void deleteAll();

    boolean isEmpty();

    void enqueue(T data);

    T dequeue();

    Iterator<T> vytvorIterator();
}