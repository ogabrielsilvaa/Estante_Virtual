package com.br.estante_virtual.dto.request.userBook;

import com.br.estante_virtual.enums.BookReadingStatus;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public class UserBookAtualizarDTORequest {

    @PositiveOrZero(message = "O número de páginas lidas não pode ser negativo.")
    private Integer pagesRead = 0;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Boolean favorite;


    public UserBookAtualizarDTORequest() {
    }

    public UserBookAtualizarDTORequest(Integer pagesRead, LocalDate startDate, LocalDate finishDate, Boolean favorite) {
        this.pagesRead = pagesRead;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.favorite = favorite;
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
