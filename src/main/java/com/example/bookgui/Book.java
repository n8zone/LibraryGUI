package com.example.bookgui;



public class Book {
    enum Condition {
        EXCELLENT,
        VERY_GOOD,
        GOOD,
        FAIR,
        POOR;

        public static Condition random() {
            Condition[] allValues = Condition.values();
            int choiceIndex = (int) Math.floor(Math.random() * allValues.length);
            return allValues[choiceIndex];
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
    private static String test;

    private String author;
    private String title;

    private Condition condition;
    private Status status;

    private double price;

    public Book(String author, String title, double price) {
        numberOfBooks++;
        this.author = author;
        this.title = title;
        this.price = price;

        this.condition = Condition.EXCELLENT;
        this.status = Status.AVAILABLE;
    }

    public void testMethod() {
        enum TestEnum { HELLO, GOODBYE };
        System.out.println(TestEnum.HELLO);
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

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

}
