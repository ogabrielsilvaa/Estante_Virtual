package com.br.estante_virtual.entity;

import com.br.estante_virtual.enums.BookReadingStatus;
import com.br.estante_virtual.enums.BookStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Identificador único do livro.
     * Gerado automaticamente pelo banco de dados (Auto Increment).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer id;

    /**
     * Título do livro.
     * Campo obrigatório.
     */
    @Column(name = "book_title", nullable = false)
    private String title;

    /**
     * Autor principal da obra.
     * Campo obrigatório.
     */
    @Column(name = "book_author", nullable = false)
    private String author;

    /**
     * Código ISBN (International Standard Book Number).
     * Campo obrigatório e único no sistema.
     */
    @Column(name = "book_isbn", unique = true, nullable = false)
    private String isbn;

    /**
     * URL da imagem da capa do livro.
     * Campo obrigatório.
     */
    @Column(name = "book_cover_url", nullable = false)
    private String coverUrl;

    /**
     * Sinopse ou resumo detalhado do livro.
     * Mapeado como TEXT/CLOB no banco para suportar grandes volumes de texto.
     * Campo opcional.
     */
    @Column(name = "book_synopsis", columnDefinition = "TEXT")
    private String synopsis;

    /**
     * Número total de páginas.
     * Campo obrigatório.
     */
    @Column(name = "book_page_count", nullable = false)
    private Integer pageCount;

    /**
     * Editora responsável pela publicação.
     * Campo opcional.
     */
    @Column(name = "book_publisher")
    private String publisher;

    /**
     * Ano de publicação da edição.
     * Campo obrigatório.
     */
    @Column(name = "book_publication_year", nullable = false)
    private Integer publicationYear;

    /**
     * Campo de status para o livro.
     */
    @Column(name = "book_status", nullable = false)
    private BookReadingStatus status;


    public Book() {
    }

    public Book(Integer id, String title, String author, String isbn, String coverUrl, String synopsis, Integer pageCount, String publisher, Integer publicationYear, BookReadingStatus status) {
        this.id = id;
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

    public BookReadingStatus getStatus() {
        return status;
    }

    public void setStatus(BookReadingStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Book book = (Book) object;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
