package BookLibrary;

import BinarySearchTree.AbstrTable;
import BinarySearchTree.AbstrTable.Node;
import Enums.eTraversal;

import java.io.*;
import java.util.*;

public class BookLibrary implements IBookLibrary {
    AbstrTable<String, Book> bstBooks = new AbstrTable<>();

    @Override
    public int loadCSV(File fileAddress) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileAddress), "windows-1250"))) {
            int numOfLoadedBooks = 0;
            String[] valuesArray = new String[5];
            String loadedIsbn ;
            String loadedName ;
            int loadedNumOfPages;
            Author loadedAuthor;


            String line = bufferedReader.readLine();
            valuesArray = line.split(";");

            if (    !Objects.equals(valuesArray[0], "ISBN") ||
                    !Objects.equals(valuesArray[1], "NAZEV") ||
                    !Objects.equals(valuesArray[2], "POCET_STRAN") ||
                    !Objects.equals(valuesArray[3], "JMENO") ||
                    !Objects.equals(valuesArray[4], "PRIJMENI")
                ) {
                return -1;
            }



            while ((line = bufferedReader.readLine()) != null ) {
                valuesArray = line.split(";");

                loadedIsbn = valuesArray[0];
                loadedName = valuesArray[1];
                loadedNumOfPages = Integer.parseInt(valuesArray[2]);
                loadedAuthor = new Author(valuesArray[3], valuesArray[4]);

                Book loadedBook = new Book(loadedIsbn, loadedName, loadedNumOfPages, loadedAuthor);
                bstBooks.insert(loadedBook.getIsbn(), loadedBook);
                numOfLoadedBooks++;
            }
            createBalancedTree();
            return numOfLoadedBooks;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public Book findBook(String key) {
        return bstBooks.findByKey(key);
    }

    @Override
    public int insertBook(Book book) {
        bstBooks.insert(book.getIsbn(), book);
        createBalancedTree();
        return 1;
    }

    @Override
    public Book deleteBook(String key) {
        return bstBooks.delete(key) ;
    }

    @Override
    public boolean deleteAllBooks() {
        return bstBooks.deleteAll();
    }

    @Override
    public void createBalancedTree() {
        List<Node> prvky = new LinkedList<>();
        saveTreetoList(bstBooks.getRoot(), prvky);
        bstBooks.setRoot(saveSortedListToTree(prvky, 0, prvky.size() - 1));
    }

    private void saveTreetoList(Node koren, List<Node> nodeList) {
        if (koren == null) {
            return;
        }
        Iterator<Node> iter = createIterator(eTraversal.REFERENCE);
        while (iter.hasNext()) {
            nodeList.add(iter.next());
        }
    }

    private Node saveSortedListToTree(List<Node> nodeList, int first, int last) {
        if (first > last) {
            return null;
        }
        int middle = (first + last) / 2;

        Node node = nodeList.get(middle);
        node.setLeftChild(saveSortedListToTree(nodeList, first, middle - 1));
        node.setRightChild(saveSortedListToTree(nodeList, middle + 1, last));

        return node;
    }

    @Override
    public Iterator createIterator(eTraversal typ) {
        return bstBooks.createIterator(typ);
    }

}
