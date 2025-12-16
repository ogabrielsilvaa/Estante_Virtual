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
     * @param livroId O ID do livro a ser buscado.
     * @return O {@link BookDTOResponse} correspondente ao ID.
     */
    public BookDTOResponse listarLivroPorId(Integer livroId) {
        Book livroVerificado = validarLivro(livroId);
        return new BookDTOResponse(livroVerificado);
    }

    @Transactional
    public BookDTOResponse cadastrarLivro(BookDTORequest bookDTORequest) {
        Book novoLivro = new Book();

        novoLivro.setTitle(bookDTORequest.getTitle());
        novoLivro.setAuthor(bookDTORequest.getAuthor());
        novoLivro.setIsbn(bookDTORequest.getIsbn());
        novoLivro.setCoverUrl(bookDTORequest.getCoverUrl());
        novoLivro.setSynopsis(bookDTORequest.getSynopsis());
        novoLivro.setPageCount(bookDTORequest.getPageCount());
        novoLivro.setPublisher(bookDTORequest.getPublisher());
        novoLivro.setPublicationYear(bookDTORequest.getPublicationYear());

        Book livroSalvo = bookRepository.save(novoLivro);

        return new BookDTOResponse(livroSalvo);
    }

    /**
     * Atualiza os dados de um livro existente.
     * @param livroId O ID do livro a ser atualizado.
     * @param livroAtualizado O DTO com os novos dados.
     * @return O {@link BookAtualizarDTORequest} da entidade atualizada.
     */
    @Transactional
    public BookDTOResponse atualizarLivroPorId(Integer livroId, BookAtualizarDTORequest livroAtualizado) {
        Book livroExistente = validarLivro(livroId);

        if (livroAtualizado.getTitle() != null) {
            livroExistente.setTitle(livroAtualizado.getTitle());
        }

        if (livroAtualizado.getAuthor() != null) {
            livroExistente.setAuthor(livroAtualizado.getAuthor());
        }

        if (livroAtualizado.getIsbn() != null) {
            livroExistente.setIsbn(livroAtualizado.getIsbn());
        }

        if (livroAtualizado.getCoverUrl() != null) {
            livroExistente.setCoverUrl(livroAtualizado.getCoverUrl());
        }

        if (livroAtualizado.getSynopsis() != null) {
            livroExistente.setSynopsis(livroAtualizado.getSynopsis());
        }

        if (livroAtualizado.getPageCount() != null) {
            livroExistente.setPageCount(livroAtualizado.getPageCount());
        }

        if (livroAtualizado.getPublisher() != null) {
            livroExistente.setPublisher(livroAtualizado.getPublisher());
        }

        if (livroAtualizado.getPublicationYear() != null) {
            livroExistente.setPublicationYear(livroAtualizado.getPublicationYear());
        }

        return new BookDTOResponse(livroExistente);
    }

    /**
     * Realiza a exclusão lógica de um livro.
     * @param livroId O ID do livro a ser desativado.
     */
    @Transactional
    public void deletarLogico(Integer livroId) {
        validarLivro(livroId);
        bookRepository.apagarLivroLogico(livroId, BookStatus.INATIVO);
    }

    /**
     * Valida a existência de um tratamento e sua posse pelo usuário.
     * @param livroId o ID do livro a ser validado.
     * @return A entidade {@link Book} encontrada.
     * @throws EntityNotFoundException se o livro não for encontrado.
     */
    private Book validarLivro(Integer livroId) {
        Book livro = bookRepository.listarLivroPorId(livroId);

        if (livro == null) {
            throw new EntityNotFoundException("Livro não encontrado com o ID: " + livroId);
        }

        return livro;
    }
}
