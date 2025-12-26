package com.br.estante_virtual.mapper;

import com.br.estante_virtual.dto.request.book.BookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.book.BookDTORequest;
import com.br.estante_virtual.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

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
