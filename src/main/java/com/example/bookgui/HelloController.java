package com.example.bookgui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

    private String selectedUID = "";

    private final TextFormatter<Object> numericInput = new TextFormatter<>(change -> {
        String newText = change.getControlNewText();
        if (newText.matches("\\d*(\\.\\d{0,2})?")) {
            return change;
        } else {
            return null;
        }
    });

    public Button addBookBtn;
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

        displayBooks();
        cb = new ChoiceBox<Book.Condition>();
        cb.getItems().addAll(Book.Condition.stringValues());
        cb.setValue(cb.getItems().getFirst());
        bookControl.getChildren().add(cb);

        priceInput.setTextFormatter(numericInput);
    }


    // This function is doing too much. It shouldn't control book selection so directly.
    private void displayBooks() {
        if ( Book.allBooks.isEmpty() ) {
            Label emptyLbl = new Label("Library is empty!");
            boxTest.getChildren().add(emptyLbl);
        }

        for (Book b : Book.allBooks) {
            VBox bookBox = b.getVBook().box();

            // This event listener is really waiting for selectedBook to update
            bookBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                refreshBooks();
                populateEditFields();
            });
            boxTest.getChildren().add(bookBox);
        }
    }

    private void populateEditFields() {
        Book b = Book.selectedBook;

        if (b == null) {
            clearInput();
            return;
        }

        addBookBtn.setText("Save Changes");
        authorInput.setText(b.getAuthor());
        titleInput.setText(b.getTitle());
        priceInput.setText(String.format("%.2f", b.getPrice()));
        cb.setValue(b.getConditionString());
    }

    private void clearBooks() {
        boxTest.getChildren().clear();
    }
    
    private void refreshBooks() {
        clearBooks();
        displayBooks();
    }

    private void addBook(Book newBook) {
        Book.addBook(newBook);
        refreshBooks();
    }

    private void clearInput() {
        authorInput.clear();
        titleInput.clear();
        priceInput.clear();
        addBookBtn.setText("Add Book");
    }

    // One day I'll rename this to what it actually is!
    @FXML
    protected void onSaveButtonClick() {
        String author = authorInput.getText();
        String title = titleInput.getText();
        double price = Double.parseDouble(priceInput.getText());
        Book.Condition condition = Book.Condition.toCondition(cb.getValue().toString());

        Book selectedBook = Book.selectedBook;
        if (selectedBook != null) {
            selectedBook.setAuthor(author);
            selectedBook.setTitle(title);
            selectedBook.setPrice(price);
            selectedBook.setCondition(condition);
            Book.clearSelected();
        } else {
            Book newBook = new Book(author, title, price);
            newBook.setCondition(condition);
            addBook(newBook);
        }

        clearInput();
        refreshBooks();
    }
}