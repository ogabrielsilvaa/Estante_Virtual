package com.br.estante_virtual.entity;

import com.br.estante_virtual.enums.BookReadingStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "user_book",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "book_id"}) //garante que não duplica livro pra o mesmo user
        })
public class UserBook implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_book_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "user_book_pages_read")
    private Integer pagesRead = 0;

    @Column(name = "user_book_start_date")
    private LocalDate startDate;

    @Column(name = "user_book_finish_date")
    private LocalDate finishDate;

    @Column(name = "user_book_is_favorite")
    private Boolean favorite;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_book_reading_status")
    private BookReadingStatus readingStatus;

    @Column(name = "user_book_is_active", nullable = false)
    private Boolean statusActive = true;


    public UserBook() {
    }

    public UserBook(User user, Book book, BookReadingStatus readingStatus) {
        this.user = user;
        this.book = book;
        this.pagesRead = 0;
        this.favorite = false;
        this.readingStatus = readingStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public BookReadingStatus getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(BookReadingStatus readingStatus) {
        this.readingStatus = readingStatus;
    }

    public Boolean getStatusActive() {
        return statusActive;
    }

    public void setStatusActive(Boolean statusActive) {
        this.statusActive = statusActive;
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
