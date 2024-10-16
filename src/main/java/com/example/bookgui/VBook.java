package com.example.bookgui;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class VBook {
    static int vBookCount = 0;

    private Book book;


    private final int UID;
    private boolean selected;

    public VBook(Book book) {
        this.book = book;
        UID = (int) (Math.random() * 1000);
    }

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    public boolean isSelected() { return selected; }

    public VBox box() {
        VBox bookBox = new VBox();
        bookBox.setAlignment(Pos.CENTER);

        Color borderColor = selected ? Color.CORNFLOWERBLUE : Color.BLACK;

        System.out.println(selected);

        Label lTitle = new Label(book.getTitle());
        Label lCondition = new Label(book.getCondition().toString());
        Label lAuthor = new Label(book.getAuthor());
        Label lStatus = new Label(book.getStatus().toString());

        Rectangle coverImage = new Rectangle();
        coverImage.setWidth(150);
        coverImage.setHeight(250);
        coverImage.setStrokeWidth(3);
        coverImage.setStroke(borderColor);

        bookBox.getChildren().addAll(lTitle,lCondition,coverImage,lStatus);

        bookBox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            coverImage.setStroke(Color.CORNFLOWERBLUE);
        });

        bookBox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            coverImage.setStroke(borderColor);
        });

        return bookBox;
    }


    public Book getBook() {
        return book;
    }
}
