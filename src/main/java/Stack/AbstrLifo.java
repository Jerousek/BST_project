package Stack;

import DoublyLinkedList.AbstrDoubleList;

import java.util.Iterator;

/**
 *
 * @author Jero
 * @param <T>
 */
public class AbstrLifo<T> implements IAbstrLifo<T> {

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
    public void push(T data) {
        list.insertFirst(data);
    }

    @Override
    public T pop() {
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
            public T next() {
                return pop();
            }
        };
    }

}