package com.br.estante_virtual.dto.response;

import com.br.estante_virtual.dto.response.user.UserDTOResponse;
import com.br.estante_virtual.entity.ReadingNote;
import com.br.estante_virtual.enums.ReadingNoteStatus;

public class ReadingNoteDTOResponse {

    private Integer id;
    private UserDTOResponse user;
    private BookDTOResponse book;
    private Integer page;
    private String note;
    private ReadingNoteStatus status;

    public ReadingNoteDTOResponse() {
    }

    public ReadingNoteDTOResponse(Integer id, UserDTOResponse user, BookDTOResponse book, Integer page, String note, ReadingNoteStatus status) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.page = page;
        this.note = note;
        this.status = status;
    }

    public ReadingNoteDTOResponse(ReadingNote readingNote) {
        this.id = readingNote.getId();

        if (readingNote.getUserBook() != null && readingNote.getUserBook().getUser() != null) {
            this.user = new UserDTOResponse(readingNote.getUserBook().getUser());
        }

        if (readingNote.getUserBook() != null && readingNote.getUserBook().getBook() != null) {
            this.book = new BookDTOResponse(readingNote.getUserBook().getBook());
        }

        this.page = readingNote.getPage();
        this.note = readingNote.getNote();
        this.status = readingNote.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTOResponse getUser() {
        return user;
    }

    public void setUser(UserDTOResponse user) {
        this.user = user;
    }

    public BookDTOResponse getBook() {
        return book;
    }

    public void setBook(BookDTOResponse book) {
        this.book = book;
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
