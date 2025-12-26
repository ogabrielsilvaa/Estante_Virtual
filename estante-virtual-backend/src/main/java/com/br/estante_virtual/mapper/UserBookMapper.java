package com.br.estante_virtual.mapper;

import com.br.estante_virtual.dto.request.userBook.UserBookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.userBook.UserBookDTORequest;
import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.entity.UserBook;
import org.springframework.stereotype.Component;

@Component
public class UserBookMapper {

    /**
     * Método auxiliar público para popular os dados do DTORequest para a Entidade.
     */
    public UserBook toEntity(User user, Book book, UserBookDTORequest dto) {
        UserBook userBook = new UserBook();

        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setReadingStatus(dto.getReadingStatus());
        userBook.setStatusActive(true);
        userBook.setPagesRead(dto.getPagesRead() != null ? dto.getPagesRead() : 0);
        userBook.setStartDate(dto.getStartDate());
        userBook.setFinishDate(dto.getFinishDate());
        userBook.setFavorite(dto.getFavorite() != null ? dto.getFavorite() : false);

        return userBook;
    }

    /**
     * Método auxiliar público para transferir os dados do DTO para a Entidade.
     * Verifica campo a campo se houve alteração (não nulo).
     */
    public void updateEntity(UserBook userBook, UserBookAtualizarDTORequest dtoRequest) {
        if (dtoRequest.getPagesRead() != null) {
            userBook.setPagesRead(dtoRequest.getPagesRead());
        }

        if (dtoRequest.getStartDate() != null) {
            userBook.setStartDate(dtoRequest.getStartDate());
        }

        if (dtoRequest.getFinishDate() != null) {
            userBook.setFinishDate(dtoRequest.getFinishDate());
        }

        if (dtoRequest.getFavorite() != null) {
            userBook.setFavorite(dtoRequest.getFavorite());
        }
    }

}
