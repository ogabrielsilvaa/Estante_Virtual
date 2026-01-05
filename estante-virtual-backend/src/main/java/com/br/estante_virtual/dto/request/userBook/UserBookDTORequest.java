package com.br.estante_virtual.dto.request.userBook;

import com.br.estante_virtual.enums.BookReadingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public class UserBookDTORequest {

    private Integer bookId;

    @NotBlank(message = "O ISBN é obrigatório para cadastrar novos livros.")
    private String isbn;

    private String title;
    private String author;
    private String coverUrl;
    private String publisher;
    private Integer publicationYear;
    private Integer pageCount;
    private String synopsis;

    @NotNull(message = "O status da leitura é obrigatório (ex: QUERO_LER, LENDO).")
    @Schema(type = "integer", example = "0", description = "0: Quero Ler, 1: Lendo, 2: Lido, 3: Pendente, 4: Abandonei")
    private BookReadingStatus readingStatus;

    @PositiveOrZero(message = "O número de páginas lidas não pode ser negativo.")
    private Integer pagesRead = 0;

    private LocalDate startDate;
    private LocalDate finishDate;
    private Boolean favorite;

    public UserBookDTORequest() {
    }

    public UserBookDTORequest(Integer bookId, String isbn, String title, String author, String coverUrl, String publisher, Integer publicationYear, Integer pageCount, String synopsis, BookReadingStatus readingStatus, Integer pagesRead, LocalDate startDate, LocalDate finishDate, Boolean favorite) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;
        this.synopsis = synopsis;
        this.readingStatus = readingStatus;
        this.pagesRead = pagesRead;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.favorite = favorite;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public BookReadingStatus getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(BookReadingStatus readingStatus) {
        this.readingStatus = readingStatus;
    }

    public Integer getPagesRead() {
        return pagesRead;
    }

    public void setPagesRead(Integer pagesRead) {
        this.pagesRead = pagesRead;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }
}
