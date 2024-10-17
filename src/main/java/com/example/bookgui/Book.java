package com.example.bookgui;


import java.util.ArrayList;

public class Book {
    enum Condition {
        EXCELLENT,
        VERY_GOOD,
        GOOD,
        FAIR,
        POOR;

        // Could I abstract this using generics?
        public static Condition random() {
            Condition[] allValues = Condition.values();
            int choiceIndex = (int) Math.floor(Math.random() * allValues.length);
            return allValues[choiceIndex];
        }

        public static String toConditionString(Condition condition) {
            String words[] = condition.toString().split("\\_");
            String finalString = "";

            for (String s : words) {
                String temp = s.toLowerCase();
                temp = temp.substring(0,1).toUpperCase() + temp.substring(1).replace('_', ' ');
                finalString += temp + " ";
            }

            return finalString.strip();
        }

        public static Condition toCondition(String conditionString) {
            conditionString = conditionString.toUpperCase();
            switch (conditionString) {
                case "EXCELLENT":
                    return EXCELLENT;
                case "VERY_GOOD", "VERY GOOD":
                    return VERY_GOOD;
                case "GOOD":
                    return GOOD;
                case "FAIR":
                    return FAIR;
                case "POOR":
                    return POOR;
                default:
                    return null;
            }
        }

        public static String[] stringValues() {
            Condition[] values = Condition.values();
            int len = values.length;
            String[] strings = new String[len];

            for (int i = 0; i < len; i++) {
                strings[i] = Condition.toConditionString(values[i]);
            }

            return strings;
        }
    }

    enum Status {
        AVAILABLE,
        CHECKED_OUT,
        RESERVED,
        ARCHIVED,
        LOST
    }

    private static int numberOfBooks;
    // Refactor into private eventually
    public static ArrayList<Book> allBooks = new ArrayList<>();
    public static Book selectedBook = null;

    public static Book getBookByID(String UID) {
        Book target = null;

        for (Book b : allBooks) {
            if (b.UID.equals(UID)) {
                target = b;
                break;
            }
        }
        return target;
    }


    public static void clearSelected() {
        selectedBook = null;
    }

    public static ArrayList<Book> getBooks() {
        return allBooks;
    }

    public static void addBook(Book book) {
        System.out.println("Called!");
        if (getBookByID(book.UID) == null) {
            allBooks.add(book);
        }
    }

    public final String UID;

    private String author;
    private String title;

    private Condition condition;
    private Status status;
    private VBook vBook;

    private double price;

    private boolean selected;

    public Book(String author, String title, double price) {
        numberOfBooks++;
        this.author = author;
        this.title = title;
        this.price = price;

        condition = Condition.EXCELLENT;
        status = Status.AVAILABLE;

        UID = String.format("%05.0f", Math.floor(Math.random() * 10000));
        System.out.println(UID);

        vBook = new VBook(this);

        allBooks.add(this);
    }

    public void randomizeCondition() {
        this.setCondition(Condition.random());
    }

    public void checkout() {
        this.setStatus(Status.CHECKED_OUT);
    }

    public void returnBook() {
        this.setStatus(Status.AVAILABLE);
    }

    public void markLost() {
        this.setStatus(Status.LOST);
    }

    public void reserve() {
        this.setStatus(Status.RESERVED);
    }

    public void archive() {
        this.setStatus(Status.ARCHIVED);
    }

    public boolean isAvailable() {
        return this.getStatus() == Status.AVAILABLE;
    }

    public void select() {
        Book.selectedBook = this;
    }

    public boolean isSelected() {
        return this == Book.selectedBook;
    }

    @Override
    public String toString() {
        return "com.example.bookgui.Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", condition=" + condition +
                ", status=" + status +
                ", price=" + price +
                '}';
    }

    public VBook getVBook() {
        return vBook;
    }

    // * ACCESSORS * //
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Condition getCondition() {
        return condition;
    }

    public String getConditionString() {
        return Condition.toConditionString(condition);
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

}
