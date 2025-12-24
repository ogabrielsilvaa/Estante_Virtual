package com.br.estante_virtual.dto.response;

import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.enums.BookReadingStatus;

public class BookDTOResponse {

    private Integer id;
    private String title;
    private String author;
    private String isbn;
    private String coverUrl;
    private String synopsis;
    private Integer pageCount;
    private String publisher;
    private Integer publicationYear;
    private Boolean statusActive;

    public BookDTOResponse() {
    }

    public BookDTOResponse(Book bookEntity) {
        this.id = bookEntity.getId();
        this.title = bookEntity.getTitle();
        this.author = bookEntity.getAuthor();
        this.isbn = bookEntity.getIsbn();
        this.coverUrl = bookEntity.getCoverUrl();
        this.synopsis = bookEntity.getSynopsis();
        this.pageCount = bookEntity.getPageCount();
        this.publisher = bookEntity.getPublisher();
        this.publicationYear = bookEntity.getPublicationYear();
        this.statusActive = bookEntity.getStatusActive();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Boolean getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(Boolean statusActive) {
        this.statusActive = statusActive;
    }
}