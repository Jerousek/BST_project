package com.example.javafxtest;

import BookLibrary.BookLibrary;
import Enums.eTraversal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import BookLibrary.Author;
import BookLibrary.Book;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;

import java.net.URL;

import java.util.Iterator;
import java.util.Objects;
import java.util.ResourceBundle;


public class LibraryGuiController implements Initializable {

    @FXML
    private TableColumn<Book, String> isbn;

    @FXML
    private TableColumn<Book, String> name;

    @FXML
    private TableColumn<Book, Integer> numOfPages;

    @FXML
    private TableColumn<Book, Author> author;

    @FXML
    private TableView<Book> table;

    @FXML
    private Button btnAddBook;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnFindBook;

    @FXML
    private Label lblSearchResult;

    @FXML
    private Label lblStatus;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuBtnChooseFile;

    @FXML
    private MenuItem menuBtnLoadFile;

    @FXML
    private MenuItem menuBtnSaveFile;

    @FXML
    private MenuItem menuBtnDeleteAll;

    @FXML
    private TextField tfAuthorFirstName;

    @FXML
    private TextField tfAuthorLastName;

    @FXML
    private TextField tfIsbn;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfSearch;

    @FXML
    private TextField tfNumOfPages;

    BookLibrary library = new BookLibrary();
    ObservableList<Book> tableList = FXCollections.observableArrayList();
    File saveLoadFileAddress = new File("");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isbn.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
        name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
        numOfPages.setCellValueFactory(new PropertyValueFactory<Book, Integer>("numOfPages"));
        author.setCellValueFactory(new PropertyValueFactory<Book, Author>("author"));
        table.setItems(tableList);
    }

    @FXML
    void addBook(ActionEvent event) {
        String errorOutput = "";

        if (Objects.equals(tfIsbn.getText(), "")) {
            errorOutput = "ISBN";

        }

        if (Objects.equals(tfName.getText(), ""))  {
            if (!errorOutput.equals("")) {
                errorOutput += ", ";
            }
            errorOutput += "název";
        }

        if (Objects.equals(tfNumOfPages.getText(), "")) {
            if (!errorOutput.equals("")) {
                errorOutput += ", ";
            }
            errorOutput += "počet stran";
        }

        if (Objects.equals(tfAuthorFirstName.getText(), "")) {
            if (!errorOutput.equals("")) {
                errorOutput += ", ";
            }
            errorOutput += "jméno autora";
        }

        if (Objects.equals(tfAuthorLastName.getText(), "")) {
            if (!errorOutput.equals("")) {
                errorOutput += ", ";
            }
            errorOutput += "příjmení autora";
        }
        if (!Objects.equals(tfNumOfPages.getText(), "")) {
            try {
                int isParsable = Integer.parseInt(tfNumOfPages.getText());
            } catch (NumberFormatException e) {
                if (!errorOutput.equals("")) {
                    errorOutput += "; ";
                }
                errorOutput += "Zadaná hodnota počtu stran není číslo";
            }
        }


        if (errorOutput.equals("")) {
            Author newAuthor = new Author(tfAuthorFirstName.getText(), tfAuthorLastName.getText());
            Book newBook = new Book(tfIsbn.getText(), tfName.getText(), Integer.parseInt(tfNumOfPages.getText()), newAuthor);
            library.insertBook(newBook);
            tableList.add(newBook);
            table.refresh();
            lblStatus.setText("Kniha byla přidána");
            lblSearchResult.setText(newBook.toString());
            clearTextFields();
        } else {
            lblStatus.setText("Kniha nebyla přidána");
            lblSearchResult.setText("Nebyla vyplněna pole: " + errorOutput);
        }
    }

    private void clearTextFields() {
        tfAuthorFirstName.clear();
        tfAuthorLastName.clear();
        tfIsbn.clear();
        tfName.clear();
        tfNumOfPages.clear();
    }


    @FXML
    private void deleteBook(ActionEvent event) {
        String input = tfSearch.getText();
        String outputResult = "";
        String outputStatus = "";

        if (Objects.equals(input, "")) {
            outputStatus = "Nebyl zadán text";
            outputResult = "Žádná kniha nebyla smazána";
        } else if (library.findBook(input) != null) {
            outputStatus = "Kniha byla smazána";
            outputResult = library.deleteBook(input).toString();
            refreshTable();
        } else {
            outputStatus = "Kniha nebyla nalezena";
            outputResult = "Žádná kniha nebyla smazána";
        }
        updateStatusResultLabels(outputStatus, outputResult);
        clearSearchBar();
    }

    @FXML
    void deleteAll(ActionEvent event) {
        String outputResult = "";
        String outputStatus = "";
        if (library.deleteAllBooks()) {
            outputStatus = "Úspěšně smazáno";
            outputResult = "Data byla smazána";
        } else {
            outputStatus = "Mazání nebylo provedeno";
            outputResult = "Nejsou nahrána žádná data";
        }
        updateStatusResultLabels(outputStatus, outputResult);
        refreshTable();
    }

    @FXML
    private void findBook(ActionEvent event) {
        String input = tfSearch.getText();
        String outputResult = "";
        String outputStatus = "";

        if (Objects.equals(input, "")) {
            outputStatus = "Nebyl zadán text";
            outputResult = "Žádný výsledek hledání";
        } else if (library.findBook(input) != null) {
            outputStatus = "Kniha byla nalezena";
            outputResult = library.findBook(input).toString();
        } else {
            outputStatus = "Kniha nebyla nalezena";
            outputResult = "Žádný výsledek hledání";
        }
        updateStatusResultLabels(outputStatus, outputResult);
        clearSearchBar();
    }

    private void clearSearchBar() {
        tfSearch.clear();
    }

    @FXML
    private void saveFile(ActionEvent event) {
        String statusOutput = "";
        String resultOutput = "";
            Iterator<Book> iterator = library.createIterator(eTraversal.VALUE);
            try (FileWriter fileWriter = new FileWriter(saveLoadFileAddress)) {
                String header = "ISBN;NAZEV;POCET_STRAN;JMENO;PRIJMENI\r\n";
                fileWriter.write(header);
                if (iterator.hasNext()) {
                    while (iterator.hasNext()) {
                        String line = iterator.next().toStringFile() ;
                        fileWriter.write(line);
                    }
                    statusOutput = "Ukládání se podařilo";
                    resultOutput = "Data byla úspěšně uložena do souboru: " + saveLoadFileAddress.toString();
                } else  {
                    statusOutput = "Ukládání se nezdařilo";
                    resultOutput = "Nejsou nahrána žádná data";
                }
            } catch (Exception e) {
                statusOutput = "Soubor nebyl načten";
                resultOutput = "Nebyla vybrána cesta k souboru nebo soubor neexistuje";
        }
        updateStatusResultLabels(statusOutput, resultOutput);
    }

    @FXML
    private void loadFile(ActionEvent event) {
        String statusOutput = "";
        String resultOutput = "";
        if (saveLoadFileAddress == null) {
            statusOutput = "Soubor nebyl načten";
            resultOutput = "Nebyla vybrána cesta k souboru nebo soubor neexistuje";
        } else {
            int numOfLoadedBooks = library.loadCSV(saveLoadFileAddress);

            if (numOfLoadedBooks < 0) {
                statusOutput = "Data nebyla nahrána";
                resultOutput = "Soubor není ve správném formátu";
            } else if (numOfLoadedBooks == 0){
                statusOutput = "Data nebyla nahrána";
                resultOutput = "Soubor je prázdný";
            } else {
                statusOutput = "Soubor byl načten";
                resultOutput = "Bylo načteno " + numOfLoadedBooks + " knih";
            }
        }
        updateStatusResultLabels(statusOutput, resultOutput);
        refreshTable();
    }

    private void updateStatusResultLabels(String statusOutput, String resultOutput) {
        lblStatus.setText(statusOutput);
        lblSearchResult.setText(resultOutput);
    }

    @FXML
    void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        saveLoadFileAddress = fileChooser.showOpenDialog(null);
        updateStatusResultLabels("Soubor byl vybrán", "Pracujete se souborem " + saveLoadFileAddress);
    }
    private void refreshTable() {
        tableList.clear();
        Iterator<Book> libraryIterator = library.createIterator(eTraversal.VALUE);
        while (libraryIterator.hasNext()) {
            tableList.add(libraryIterator.next());
        }
        table.refresh();
    }
}




