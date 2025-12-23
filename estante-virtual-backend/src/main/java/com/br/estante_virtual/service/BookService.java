package com.br.estante_virtual.service;

import com.br.estante_virtual.dto.request.book.BookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.book.BookDTORequest;
import com.br.estante_virtual.dto.response.BookDTOResponse;
import com.br.estante_virtual.dto.response.UserBookDTOResponse;
import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.entity.UserBook;
import com.br.estante_virtual.enums.BookReadingStatus;
import com.br.estante_virtual.enums.BookStatus;
import com.br.estante_virtual.repository.BookRepository;
import com.br.estante_virtual.repository.UserBookRepository;
import com.br.estante_virtual.repository.UserRepository;
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
    public List<BookDTOResponse> listarLivrosAtivos(Integer userId) {
        return bookRepository.listarPorStatusEUsuario(BookStatus.ATIVO, userId)
                .stream()
                .map(BookDTOResponse::new)
                .toList();
    }

    /**
     * Busca os livros que estão com status INATIVO.
     * @return lista de livros inativos.
     */
    public List<BookDTOResponse> listarLivrosInativos(Integer userId) {
        return bookRepository.listarPorStatusEUsuario(BookStatus.INATIVO, userId)
                .stream()
                .map(BookDTOResponse::new)
                .toList();
    }

    /**
     * Busca um livro pelo seu ID.
     * @param bookId O ID do livro a ser buscado.
     * @return O {@link BookDTOResponse} correspondente ao ID.
     */
    public BookDTOResponse listarLivroPorId(Integer bookId, Integer userId) {
        Book verifiedBook = validarLivro(bookId, userId);
        return new BookDTOResponse(verifiedBook);
    }

    /**
     * Cadastra um novo livro para a conta do usuário logado.
     * @param dtoRequest os dados do livro que será cadastrado.
     * @param userId ID do usuário logado.
     * @return
     */
    @Transactional
    public BookDTOResponse cadastrarLivro(BookDTORequest dtoRequest, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        Book newBook = new Book();

        newBook.setTitle(dtoRequest.getTitle());
        newBook.setAuthor(dtoRequest.getAuthor());
        newBook.setIsbn(dtoRequest.getIsbn());
        newBook.setCoverUrl(dtoRequest.getCoverUrl());
        newBook.setSynopsis(dtoRequest.getSynopsis());
        newBook.setPageCount(dtoRequest.getPageCount());
        newBook.setPublisher(dtoRequest.getPublisher());
        newBook.setPublicationYear(dtoRequest.getPublicationYear());
        newBook.setStatus(dtoRequest.getStatus());

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
    public BookDTOResponse atualizarLivroPorId(Integer bookId, BookAtualizarDTORequest atualizarDTORequest, Integer userId) {
        Book existingBook = validarLivro(bookId, userId);

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
     * Atualiza o status de leitura de um livro na estante do usuário.
     *
     * @param bookId ID do livro a ser atualizado.
     * @param userId ID do usuário dono da estante.
     * @param status Novo status de leitura a ser definido.
     * @return DTO contendo os dados atualizados do vínculo.
     * @throws EntityNotFoundException Se o livro não for encontrado na estante deste usuário.
     */
    @Transactional
    public UserBookDTOResponse mudarStatusDeLeitura(Integer bookId, Integer userId, BookReadingStatus status) {
        if (!userBookRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new EntityNotFoundException("Livro não encontrado na sua estante.");
        }

        userBookRepository.mudarStatusDoLivro(userId, bookId, status);

        UserBook userBookAtualizado = userBookRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new EntityNotFoundException("Erro ao recuperar livro atualizado."));

        return new UserBookDTOResponse(userBookAtualizado);
    }

    /**
     * Valida a existência de um livro e sua posse pelo usuário.
     * @param bookId o ID do livro a ser validado.
     * @return A entidade {@link Book} encontrada.
     * @throws EntityNotFoundException se o livro não for encontrado.
     */
    private Book validarLivro(Integer bookId, Integer userId) {
        return bookRepository.listarLivroPorIdEUsuario(bookId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado ou não existe na estante do Usuário."));
    }
}
