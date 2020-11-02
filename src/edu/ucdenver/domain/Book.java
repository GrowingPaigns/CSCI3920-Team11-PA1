package edu.ucdenver.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Book extends Product implements Serializable
{

    private String title;
    private String authorName;
    private LocalDate publicationDate;
    private int numberOfPages;

    //constructors
    public Book(String id, String name, String brandName, String description, LocalDate dateAdded,
                String title, String authorName, LocalDate publicationDate, int numberOfPages)
    {
        super (id, name, brandName, description, dateAdded);
        this.title = title;
        this.authorName = authorName;
        this.publicationDate =  publicationDate;
        this.numberOfPages = numberOfPages;
    }

    public Book(String id, String name, String brandName, String description, LocalDate dateAdded,
                ArrayList<Category> categories, String title, String authorName, LocalDate publicationDate, int numberOfPages)
    {
        super (id, name, brandName, description, dateAdded, categories);
        this.title = title;
        this.authorName = authorName;
        this.publicationDate =  publicationDate;
        this.numberOfPages = numberOfPages;
    }

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

    @Override
    public String toString()
    {
        return super.toString() + String.format("%nTITLE: %s, AUTHOR: %s, DATE OF PUBLICATION: %s, NUM. OF PAGES: %d",
                title, authorName, publicationDate.toString(), numberOfPages);
    }
}
