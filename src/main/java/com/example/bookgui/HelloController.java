package com.example.bookgui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


// TODO: Eventually refactor Book.Condition into its own enum
public class HelloController implements Initializable {
    Book book1 = new Book("Jeremy Kubica","Data Structures The Fun Way", 39.99);
    Book book2 = new Book("Ronald T Kneusel", "Math For Deep Learning", 49.99);
    Book book3 = new Book("Matthew Dicks", "Storyworthy", 12.99);


    // This is disgusting
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<VBook> vBooks = new ArrayList<>();

    private Book selectedBook = null;

    private final TextFormatter<Object> numericInput = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d*(\\.\\d*)?")) {
            return change;
        } else {
            return null;
        }
    });

    public HBox bookControl;
    public HBox boxTest;
    @FXML
    private Label welcomeText;

    @FXML
    private Label broText;


    public TextField authorInput;
    public TextField titleInput;
    public TextField priceInput;

    public ChoiceBox cb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        books.add(book1);
        books.add(book2);
        books.add(book3);

        // This code is terrible.
        for (Book b : books) { vBooks.add(new VBook(b)); }

        displayBooks();
        cb = new ChoiceBox<Book.Condition>();
        cb.getItems().addAll(Book.Condition.values());
        cb.setValue(cb.getItems().getFirst());
        bookControl.getChildren().add(cb);

        priceInput.setTextFormatter(numericInput);
    }



    private void displayBooks() {
        if ( books.isEmpty() ) {
            Label emptyLbl = new Label("Library is empty!");
            boxTest.getChildren().add(emptyLbl);
        }

        for (int i = 0; i < vBooks.size(); i++) {
            // Could it be the method to abstract this into a method that returns the VBox?
            VBook b = vBooks.get(i);
            final int bookId = i; // THIS CANNOT BE WHERE WE STORE BOOKID

            VBox bookBox = b.box();

            bookBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                if (e.getButton() == MouseButton.PRIMARY) {
                    System.out.println("Open editing dialogue");

                    if (b.isSelected()) {
                        b.deselect();
                        selectedBook = null;
                    } else {
                        for (VBook vb : vBooks) { vb.deselect(); }
                        b.select();
                        selectedBook = b.getBook();
                    }

                    populateEditFields();
                } else {
                    System.out.println("Quick delete");

                    if (selectedBook == vBooks.get(bookId).getBook()) {
                        selectedBook = null;
                        vBooks.get(bookId).deselect();
                    }

                    books.remove(bookId);
                    vBooks.remove(bookId);
                }

                refreshBooks();
            });

            boxTest.getChildren().add(bookBox);
        }
    }

    private void populateEditFields() {

        if (selectedBook == null) {
            clearInput();
            return;
        }

        authorInput.setText(selectedBook.getAuthor());
        titleInput.setText(selectedBook.getTitle());
        priceInput.setText(Double.toString(selectedBook.getPrice()));
        cb.setValue(selectedBook.getCondition());
    }

    private void clearBooks() {
        boxTest.getChildren().clear();
    }
    
    private void refreshBooks() {
        clearBooks();
        displayBooks();
    }

    private void addBook(Book newBook) {
        books.add(newBook);
        vBooks.add(new VBook(newBook));
        refreshBooks();
    }

    private void clearInput() {
        authorInput.clear();
        titleInput.clear();
        priceInput.clear();
    }

    // One day I'll rename this to what it actually is!
    @FXML
    protected void onHelloButtonClick() {
        String author = authorInput.getText();
        String title = titleInput.getText();
        double price = Double.parseDouble(priceInput.getText());
        Book.Condition condition = (Book.Condition) cb.getValue();


        if (selectedBook != null) {
            selectedBook.setAuthor(author);
            selectedBook.setTitle(title);
            selectedBook.setPrice(price);
            selectedBook.setCondition(condition);
            selectedBook = null;
        } else {
            Book testBook = new Book(author, title, price);
            testBook.setCondition(condition);
            addBook(testBook);
        }

        clearInput();
        refreshBooks();
    }
}