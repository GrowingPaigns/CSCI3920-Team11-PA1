package edu.ucdenver.domain;

import java.time.LocalDate;

public class Book extends Product{

    private String title;
    private String authorName;
    private LocalDate publicationDate;
    private int numberOfPages;

    //constructors
    public Book(String title, String authorName, LocalDate publicationDate, int numberOfPages) {
        this.title = title;
        this.authorName = authorName;
        this.publicationDate =  publicationDate;
        this.numberOfPages = numberOfPages;
    }
    //TODO Do we need a default constructor here?

    //getters and setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) { this.title = title; }

    String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
