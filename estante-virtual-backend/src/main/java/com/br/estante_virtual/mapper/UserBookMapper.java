package com.br.estante_virtual.mapper;

import com.br.estante_virtual.dto.request.userBook.UserBookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.userBook.UserBookDTORequest;
import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.entity.UserBook;
import org.springframework.stereotype.Component;

/**
 * Componente responsável pelo mapeamento de dados entre DTOs e a entidade UserBook (Estante).
 */
@Component
public class UserBookMapper {

    /**
     * Cria uma nova entidade UserBook associando um usuário a um livro.
     * Define valores padrão para status ativo, páginas lidas e favorito.
     *
     * @param user O usuário dono da estante.
     * @param book O livro a ser adicionado.
     * @param dtoRequest Os dados iniciais da leitura.
     * @return Uma nova instância de {@link UserBook} pronta para persistência.
     */
    public UserBook toEntity(User user, Book book, UserBookDTORequest dtoRequest) {
        UserBook userBook = new UserBook();

        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setReadingStatus(dtoRequest.getReadingStatus());
        userBook.setStatusActive(true);
        userBook.setPagesRead(dtoRequest.getPagesRead() != null ? dtoRequest.getPagesRead() : 0);
        userBook.setStartDate(dtoRequest.getStartDate());
        userBook.setFinishDate(dtoRequest.getFinishDate());
        userBook.setFavorite(dtoRequest.getFavorite() != null ? dtoRequest.getFavorite() : false);

        return userBook;
    }

    /**
     * Atualiza os campos de um registro existente na estante.
     * Realiza atualização parcial (apenas campos não nulos no DTO são alterados).
     *
     * @param userBook A entidade a ser atualizada.
     * @param dtoAtualizar O DTO contendo os novos dados.
     */
    public void updateEntity(UserBook userBook, UserBookAtualizarDTORequest dtoAtualizar) {
        if (dtoAtualizar.getReadingStatus() != null) {
            userBook.setReadingStatus(dtoAtualizar.getReadingStatus());
        }

        if (dtoAtualizar.getPagesRead() != null) {
            userBook.setPagesRead(dtoAtualizar.getPagesRead());
        }

        if (dtoAtualizar.getStartDate() != null) {
            userBook.setStartDate(dtoAtualizar.getStartDate());
        }

        if (dtoAtualizar.getFinishDate() != null) {
            userBook.setFinishDate(dtoAtualizar.getFinishDate());
        }

        if (dtoAtualizar.getFavorite() != null) {
            userBook.setFavorite(dtoAtualizar.getFavorite());
        }
    }

    /**
     * Converte o request complexo do UserBook (que tem dados do livro e do usuário)
     * em uma entidade Book limpa.
     *
     * @param dtoRequest O DTO contendo dados misturados.
     * @return A entidade Book pronta para salvar.
     */
    public Book userBookDTOToBookEntity(UserBookDTORequest dtoRequest) {
        Book newBook = new Book();

        newBook.setTitle(dtoRequest.getTitle());
        newBook.setAuthor(dtoRequest.getAuthor());
        newBook.setIsbn(dtoRequest.getIsbn());
        newBook.setCoverUrl(dtoRequest.getCoverUrl());
        newBook.setSynopsis(dtoRequest.getSynopsis());
        newBook.setPageCount(dtoRequest.getPageCount());
        newBook.setPublisher(dtoRequest.getPublisher());
        newBook.setPublicationYear(dtoRequest.getPublicationYear());

        newBook.setStatusActive(true);

        return newBook;
    }

}
