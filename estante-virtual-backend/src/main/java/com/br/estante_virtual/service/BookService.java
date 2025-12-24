package com.br.estante_virtual.service;

import com.br.estante_virtual.dto.request.book.BookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.book.BookDTORequest;
import com.br.estante_virtual.dto.response.BookDTOResponse;
import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.repository.BookRepository;
import com.br.estante_virtual.repository.UserBookRepository;
import com.br.estante_virtual.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de serviço para gerenciar a lógica de negócio do catálogo de livros.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;

    @Autowired
    public BookService(
            BookRepository bookRepository,
            UserRepository userRepository,
            UserBookRepository userBookRepository
    ) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userBookRepository = userBookRepository;
    }

    /**
     * Busca os livros que estão com status ATIVO.
     * @return lista de livros ativos.
     */
    public List<BookDTOResponse> listarLivrosAtivos(Pageable pageable) {
        return bookRepository.buscarTodosAtivos(pageable)
                .stream()
                .map(BookDTOResponse::new)
                .toList();
    }

    /**
     * Busca um livro ativo pelo seu ID.
     * @param bookId O ID do livro a ser buscado.
     * @return O {@link BookDTOResponse} correspondente ao ID.
     */
    public BookDTOResponse listarLivroPorId(Integer bookId) {
        Book verifiedBook = validarLivro(bookId);
        return new BookDTOResponse(verifiedBook);
    }

    /**
     * ADMIN: Cadastra um novo livro para a conta do usuário logado.
     * @param dtoRequest os dados do livro que será cadastrado.
     */
    @Transactional
    public BookDTOResponse cadastrarLivro(BookDTORequest dtoRequest) {
        if (bookRepository.existeIsbn(dtoRequest.getIsbn())) {
            throw new EntityExistsException("Já existe um livro cadastrado com este ISBN.");
        }

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

        Book savedBook = bookRepository.save(newBook);
        return new BookDTOResponse(savedBook);
    }

    /**
     * ADMIN: Atualiza os dados de um livro existente.
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

        Book updatedBook = bookRepository.save(existingBook);

        return new BookDTOResponse(updatedBook);
    }

    /**
     * ADMIN: Ativa ou Desativa um livro (Soft Delete Global).
     */
    @Transactional
    public void atualizarStatusGlobal(Integer bookId, Boolean status) {
        Book verifiedBook = validarLivro(bookId);

        verifiedBook.setStatusActive(status);
        bookRepository.save(verifiedBook);
    }

    /**
     * Valida a existência de um livro e sua posse pelo usuário.
     * @param bookId o ID do livro a ser validado.
     * @return A entidade {@link Book} encontrada.
     * @throws EntityNotFoundException se o livro não for encontrado.
     */
    private Book validarLivro(Integer bookId) {
        return bookRepository.buscarPorIdAtivo(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado ou não existe na estante do Usuário."));
    }
}
