package com.example.bookgui;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class VBook {
    public static int vBookCount = 0;

    private Book book;

    // It makes sense to me to set this to be public and directly access since it's final and cannot be changed.

    public VBook(Book book) {
        this.book = book;
        System.out.println("Generated new book " + book.toString());
    }


    public boolean isSelected() { return book.isSelected(); }

    public VBox box() {
        VBox bookBox = new VBox();
        bookBox.setAlignment(Pos.CENTER);

        Color borderColor = book.isSelected() ? Color.CORNFLOWERBLUE : Color.BLACK;

        Label lTitle = new Label(book.getTitle());
        Label lCondition = new Label(book.getConditionString());
        Label lAuthor = new Label(book.getAuthor());
        Label lStatus = new Label(book.getStatus().toString());
        Label lId = new Label(book.UID);

        Rectangle coverImage = new Rectangle();
        coverImage.setWidth(150);
        coverImage.setHeight(250);
        coverImage.setStrokeWidth(3);
        coverImage.setStroke(borderColor);

        bookBox.getChildren().addAll(lTitle, lAuthor, coverImage, lStatus, lCondition, lId);

        bookBox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            coverImage.setStroke(Color.CORNFLOWERBLUE);
        });

        bookBox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            coverImage.setStroke(borderColor);
        });

        bookBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (isSelected()) {
                Book.clearSelected();
                return;
            }

            book.select();
            System.out.println("Selected");
            System.out.println(Book.selectedBook.UID);
        });

        return bookBox;
    }


    public Book getBook() {
        return book;
    }
}
