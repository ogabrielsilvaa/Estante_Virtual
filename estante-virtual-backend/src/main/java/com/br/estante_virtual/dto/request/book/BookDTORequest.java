package com.br.estante_virtual.dto.request.book;

import com.br.estante_virtual.enums.BookReadingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

public class BookDTORequest {

    @NotBlank(message = "Não é possível cadastrar um livro sem inserir um título.")
    private String title;

    @NotBlank(message = "O autor do livro é obrigatório.")
    private String author;

    @NotBlank(message = "O ISBN é obrigatório.")
    private String isbn;

    @NotBlank(message = "A URL da capa é obrigatória.")
    @URL(message = "A URL da capa informada não é válida.")
    private String coverUrl;

    private String synopsis;

    @NotNull(message = "O número de páginas é obrigatório.")
    @Positive(message = "O número de páginas deve ser maior que zero.")
    private Integer pageCount;

    private String publisher;

    @NotNull(message = "O ano de publicação é obrigatório.")
    @Min(value = 1000, message = "Insira um ano válido.")
    private Integer publicationYear;

    @Schema(type = "integer", example = "1", description = "1-LENDO, 0-QUERO_LER")
    private BookReadingStatus status;

    public BookDTORequest() {
    }

    public BookDTORequest(String title, String author, String isbn, String coverUrl, String synopsis, Integer pageCount, String publisher, Integer publicationYear, BookReadingStatus status) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.coverUrl = coverUrl;
        this.synopsis = synopsis;
        this.pageCount = pageCount;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.status = status;
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

    public BookReadingStatus getStatus() {
        return status;
    }

    public void setStatus(BookReadingStatus status) {
        this.status = status;
    }
}