package Stack;

import java.util.Iterator;

/**
 *
 * @author Jero
 * @param <T>
 */
public interface IAbstrLifo<T> {

    void deleteAll();

    boolean isEmpty();

    void push(T data);

    T pop();

    Iterator<T> vytvorIterator();
}