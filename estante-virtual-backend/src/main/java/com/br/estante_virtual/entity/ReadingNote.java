package com.br.estante_virtual.entity;

import com.br.estante_virtual.enums.ReadingNoteStatus;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "reading_note")
public class ReadingNote implements Serializable {

    /**
     * Identificador único da nota, gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_note_id")
    private Integer id;

    /**
     * Vínculo com o livro na estante do usuário que escreveu a nota.
     */
    @ManyToOne
    @JoinColumn(name = "user_book_id", nullable = false)
    private UserBook userBook;

    /**
     * Página da nota.
     */
    @Column(name = "reading_note_page")
    private Integer page;

    /**
     * Texto escrito na nota.
     */
    @Column(name = "reading_note_note", columnDefinition = "TEXT")
    private String note;

    /**
     * Estado atual da nota (ex: Ativo, Inativo).
     * Já é criada com status ATIVO.
     */
    @Column(name = "reading_note_status")
    private ReadingNoteStatus status = ReadingNoteStatus.ATIVO;

    public ReadingNote() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserBook getUserBook() {
        return userBook;
    }

    public void setUserBook(UserBook userBook) {
        this.userBook = userBook;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ReadingNoteStatus getStatus() {
        return status;
    }

    public void setStatus(ReadingNoteStatus status) {
        this.status = status;
    }
}
