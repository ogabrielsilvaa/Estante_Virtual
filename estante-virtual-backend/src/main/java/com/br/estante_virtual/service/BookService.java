package com.br.estante_virtual.service;

import com.br.estante_virtual.dto.request.book.BookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.book.BookDTORequest;
import com.br.estante_virtual.dto.response.BookDTOResponse;
import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.enums.BookStatus;
import com.br.estante_virtual.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de serviço para gerenciar a lógica de negócio do catálogo de livros.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(
            BookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }

    /**
     * Busca os livros que estão com status ATIVO.
     * @return lista de livros ativos.
     */
    public List<BookDTOResponse> listarLivrosAtivos() {
        return bookRepository.listarPorStatus(BookStatus.ATIVO)
                .stream()
                .map(BookDTOResponse::new)
                .toList();
    }

    /**
     * Busca os livros que estão com status INATIVO.
     * @return lista de livros inativos.
     */
    public List<BookDTOResponse> listarLivrosInativos() {
        return bookRepository.listarPorStatus(BookStatus.INATIVO)
                .stream()
                .map(BookDTOResponse::new)
                .toList();
    }

    /**
     * Busca um livro pelo seu ID.
     * @param bookId O ID do livro a ser buscado.
     * @return O {@link BookDTOResponse} correspondente ao ID.
     */
    public BookDTOResponse listarLivroPorId(Integer bookId) {
        Book verifiedBook = validarLivro(bookId);
        return new BookDTOResponse(verifiedBook);
    }

    @Transactional
    public BookDTOResponse cadastrarLivro(BookDTORequest dtoRequest) {
        Book newBook = new Book();

        newBook.setTitle(dtoRequest.getTitle());
        newBook.setAuthor(dtoRequest.getAuthor());
        newBook.setIsbn(dtoRequest.getIsbn());
        newBook.setCoverUrl(dtoRequest.getCoverUrl());
        newBook.setSynopsis(dtoRequest.getSynopsis());
        newBook.setPageCount(dtoRequest.getPageCount());
        newBook.setPublisher(dtoRequest.getPublisher());
        newBook.setPublicationYear(dtoRequest.getPublicationYear());

        Book savedBook = bookRepository.save(newBook);

        return new BookDTOResponse(savedBook);
    }

    /**
     * Atualiza os dados de um livro existente.
     * @param bookId O ID do livro a ser atualizado.
     * @param atualizarDTORequest O DTO com os novos dados.
     * @return O {@link BookAtualizarDTORequest} da entidade atualizada.
     */
    @Transactional
    public BookDTOResponse atualizarLivroPorId(Integer bookId, BookAtualizarDTORequest atualizarDTORequest) {
        Book existingBook = validarLivro(bookId);

        if (atualizarDTORequest.getTitle() != null) {
            existingBook.setTitle(atualizarDTORequest.getTitle());
        }

        if (atualizarDTORequest.getAuthor() != null) {
            existingBook.setAuthor(atualizarDTORequest.getAuthor());
        }

        if (atualizarDTORequest.getIsbn() != null) {
            existingBook.setIsbn(atualizarDTORequest.getIsbn());
        }

        if (atualizarDTORequest.getCoverUrl() != null) {
            existingBook.setCoverUrl(atualizarDTORequest.getCoverUrl());
        }

        if (atualizarDTORequest.getSynopsis() != null) {
            existingBook.setSynopsis(atualizarDTORequest.getSynopsis());
        }

        if (atualizarDTORequest.getPageCount() != null) {
            existingBook.setPageCount(atualizarDTORequest.getPageCount());
        }

        if (atualizarDTORequest.getPublisher() != null) {
            existingBook.setPublisher(atualizarDTORequest.getPublisher());
        }

        if (atualizarDTORequest.getPublicationYear() != null) {
            existingBook.setPublicationYear(atualizarDTORequest.getPublicationYear());
        }

        return new BookDTOResponse(existingBook);
    }

    /**
     * Realiza a exclusão lógica de um livro.
     * @param bookId O ID do livro a ser desativado.
     */
    @Transactional
    public void deletarLogico(Integer bookId) {
        validarLivro(bookId);
        bookRepository.apagarLivroLogico(bookId, BookStatus.INATIVO);
    }

    /**
     * Valida a existência de um tratamento e sua posse pelo usuário.
     * @param bookId o ID do livro a ser validado.
     * @return A entidade {@link Book} encontrada.
     * @throws EntityNotFoundException se o livro não for encontrado.
     */
    private Book validarLivro(Integer bookId) {
        Book book = bookRepository.listarLivroPorId(bookId);

        if (book == null) {
            throw new EntityNotFoundException("Livro não encontrado com o ID: " + bookId);
        }

        return book;
    }
}
