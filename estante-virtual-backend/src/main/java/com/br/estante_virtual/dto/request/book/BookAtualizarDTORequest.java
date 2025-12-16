package com.br.estante_virtual.dto.request.book;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

public class BookAtualizarDTORequest {

    private String title;

    private String author;

    private String isbn;

    @URL(message = "A URL da capa informada não é válida.")
    private String coverUrl;

    private String synopsis;

    @Positive(message = "O número de páginas deve ser maior que zero.")
    private Integer pageCount;

    private String publisher;

    @Min(value = 1000, message = "Insira um ano válido.")
    private Integer publicationYear;

    public BookAtualizarDTORequest() {
    }

    public BookAtualizarDTORequest(String title, String author, String isbn, String coverUrl, String synopsis, Integer pageCount, String publisher, Integer publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.coverUrl = coverUrl;
        this.synopsis = synopsis;
        this.pageCount = pageCount;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
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
}
