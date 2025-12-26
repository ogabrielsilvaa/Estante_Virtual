package com.br.estante_virtual.mapper;

import com.br.estante_virtual.dto.request.book.BookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.book.BookDTORequest;
import com.br.estante_virtual.entity.Book;
import org.springframework.stereotype.Component;

/**
 * Componente responsável pelo mapeamento de dados entre DTOs e a entidade Book (Livro).
 */
@Component
public class BookMapper {

    /**
     * Converte o DTO de cadastro em uma nova entidade Book.
     * Inicializa o livro como ativo (statusActive = true) por padrão.
     *
     * @param dtoRequest Dados do livro a ser cadastrado.
     * @return Uma nova instância de {@link Book} pronta para ser salva.
     */
    public Book toEntity(BookDTORequest dtoRequest) {

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

    /**
     * Atualiza os dados de um livro existente no catálogo.
     * Realiza atualização parcial (apenas campos não nulos no DTO são alterados).
     *
     * @param book A entidade do livro a ser atualizada.
     * @param dtoAtualizar O DTO contendo os novos dados.
     */
    public void updateEntity(Book book, BookAtualizarDTORequest dtoAtualizar) {
        if (dtoAtualizar.getTitle() != null) {
            book.setTitle(dtoAtualizar.getTitle());
        }

        if (dtoAtualizar.getAuthor() != null) {
            book.setAuthor(dtoAtualizar.getAuthor());
        }

        if (dtoAtualizar.getIsbn() != null) {
            book.setIsbn(dtoAtualizar.getIsbn());
        }

        if (dtoAtualizar.getCoverUrl() != null) {
            book.setCoverUrl(dtoAtualizar.getCoverUrl());
        }

        if (dtoAtualizar.getSynopsis() != null) {
            book.setSynopsis(dtoAtualizar.getSynopsis());
        }

        if (dtoAtualizar.getPageCount() != null) {
            book.setPageCount(dtoAtualizar.getPageCount());
        }

        if (dtoAtualizar.getPublisher() != null) {
            book.setPublisher(dtoAtualizar.getPublisher());
        }

        if (dtoAtualizar.getPublicationYear() != null) {
            book.setPublicationYear(dtoAtualizar.getPublicationYear());
        }
    }

}
