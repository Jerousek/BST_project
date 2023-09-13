package BinarySearchTree;

import Enums.eTraversal;

import java.util.Iterator;

/**
 *
 * @author 42072
 * @param <K>
 * @param <V>
 */
public interface IAbstrTable<K, V> {

    boolean deleteAll();

    boolean isEmpty();

    V findByKey(K key);

    void insert(K key, V value);

    V delete(K key);

    Iterator<V> createIterator(eTraversal typ);
}