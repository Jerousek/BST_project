package BookLibrary;

import Enums.eTraversal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;

public interface IBookLibrary {

    int loadCSV(File fileAddress) throws FileNotFoundException;
    int insertBook(Book book);
    Book findBook(String key);
    Book deleteBook(String key);
    boolean deleteAllBooks();
    void createBalancedTree();
    Iterator createIterator(eTraversal typ);


}
