package com.br.estante_virtual.dto.response;

import com.br.estante_virtual.entity.UserBook;
import com.br.estante_virtual.enums.BookReadingStatus;

import java.time.LocalDate;

public class UserBookDTOResponse {

    private Integer id;
    private BookDTOResponse book;
    private BookReadingStatus status;
    private Integer pagesRead;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Boolean favorite;

    public UserBookDTOResponse() {
    }

    public UserBookDTOResponse(Integer id, BookDTOResponse book, BookReadingStatus status, Integer pagesRead, LocalDate startDate, LocalDate finishDate, Boolean favorite) {
        this.id = id;
        this.book = book;
        this.status = status;
        this.pagesRead = pagesRead;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.favorite = favorite;
    }

    public UserBookDTOResponse(UserBook userBook) {
        this.status = userBook.getStatus();
        this.pagesRead = userBook.getPagesRead();
        this.startDate = userBook.getStartDate();
        this.finishDate = userBook.getFinishDate();
        this.favorite = userBook.getFavorite();

        if (userBook.getBook() != null) {
            this.book = new BookDTOResponse(userBook.getBook());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BookDTOResponse getBook() {
        return book;
    }

    public void setBook(BookDTOResponse book) {
        this.book = book;
    }

    public BookReadingStatus getStatus() {
        return status;
    }

    public void setStatus(BookReadingStatus status) {
        this.status = status;
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
