package DoublyLinkedList;

import java.util.Iterator;

public interface IAbstrDoubleList<T> extends Iterable<T>{

    void deleteAll();

    boolean isEmpty();

    void insertFirst(T data);

    void insertLast(T data);

    void insertNext(T data);

    void insertPrev(T data);

    T getCurrent();

    T getFirst();

    T getLast();

    T getNext();

    T getPrev();

    T deleteCurrent();

    T deleteFirst();

    T deleteLast();

    T deleteNext();

    T deletePrev();

    @Override
    Iterator<T> iterator();
}
