package com.br.estante_virtual.entity;

import com.br.estante_virtual.entity.primaryKeys.UserBookId;
import com.br.estante_virtual.enums.BookReadingStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "user_book")
public class UserBook implements Serializable {

    @EmbeddedId
    private UserBookId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "user_book_status")
    private BookReadingStatus status;

    @Column(name = "user_book_pages_read")
    private Integer pagesRead = 0;

    @Column(name = "user_book_start_date")
    private LocalDate startDate;

    @Column(name = "user_book_finish_date")
    private LocalDate finishDate;

    @Column(name = "user_book_is_favorite")
    private Boolean favorite;


    public UserBook() {
    }

    public UserBook(User user, Book book) {
        this.user = user;
        this.book = book;
        this.id = new UserBookId(user.getId(), book.getId());
        this.pagesRead = 0;
        this.favorite = false;
        this.status = BookReadingStatus.QUERO_LER; // Ou outro status padrão
    }

    public UserBookId getId() {
        return id;
    }

    public void setId(UserBookId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBook userBook = (UserBook) o;
        return Objects.equals(id, userBook.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
