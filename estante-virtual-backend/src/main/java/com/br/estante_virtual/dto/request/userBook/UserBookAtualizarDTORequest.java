package com.br.estante_virtual.dto.request.userBook;

import com.br.estante_virtual.enums.BookReadingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public class UserBookAtualizarDTORequest {

    @Schema(type = "integer", example = "0", description = "0: Quero Ler, 1: Lendo, 2: Lido, 3: Pendente, 4: Abandonei")
    private BookReadingStatus status;

    @PositiveOrZero(message = "O número de páginas lidas não pode ser negativo.")
    private Integer pagesRead = 0;

    private LocalDate startDate;
    private LocalDate finishDate;
    private Boolean favorite;


    public UserBookAtualizarDTORequest() {
    }

    public UserBookAtualizarDTORequest(BookReadingStatus status, Integer pagesRead, LocalDate startDate, LocalDate finishDate, Boolean favorite) {
        this.status = status;
        this.pagesRead = pagesRead;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.favorite = favorite;
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
