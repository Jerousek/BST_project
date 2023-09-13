package Queue;

import DoublyLinkedList.AbstrDoubleList;

import java.util.Iterator;

/**
 *
 * @author Jero
 * @param <T>
 */
public class AbstrFifo<T> implements IAbstrFifo<T> {

    public AbstrDoubleList<T> list = new AbstrDoubleList<>();

    @Override
    public void deleteAll() {
        list.deleteAll();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enqueue(T data) {
        list.insertLast(data);
    }

    @Override
    public T dequeue() {
        return list.deleteFirst();
    }

    @Override
    public Iterator<T> vytvorIterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return isEmpty();
            }

            @Override
            public T next() { return dequeue(); }
        };
    }

}
