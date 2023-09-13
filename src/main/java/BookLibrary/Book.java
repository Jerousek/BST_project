package BookLibrary;

import BookLibrary.Author;

public class Book {
    private String isbn;
    private String name;
    private int numOfPages;
    private Author author;

    public Book(String isbn, String name, int numOfPages, Author author) {
        this.isbn = isbn;
        this.name = name;
        this.numOfPages = numOfPages;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public Author getAuthor() {
        return author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return  name + ", " + isbn  + " s počtem stran: " + numOfPages + " od autora: "+ author;
    }

    public String toStringFile() {
        return isbn + ";" + name + ";" + numOfPages + ";" + author.getFirstName() + ";" + author.getLastName()+"\r\n";
    }
}
