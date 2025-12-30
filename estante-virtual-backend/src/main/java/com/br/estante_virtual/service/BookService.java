package com.br.estante_virtual.service;

import com.br.estante_virtual.client.OpenLibraryClient;
import com.br.estante_virtual.dto.request.book.BookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.book.BookDTORequest;
import com.br.estante_virtual.dto.response.BookDTOResponse;
import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.mapper.BookMapper;
import com.br.estante_virtual.repository.BookRepository;
import com.br.estante_virtual.repository.UserBookRepository;
import com.br.estante_virtual.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de serviço para gerenciar a lógica de negócio do catálogo de livros.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final OpenLibraryClient openLibraryClient;

    @Autowired
    public BookService(
            BookRepository bookRepository,
            BookMapper bookMapper,
            OpenLibraryClient openLibraryClient
    ) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.openLibraryClient = openLibraryClient;
    }

    /**
     * Busca os livros que estão com status ATIVO.
     * @return lista de livros ativos.
     */
    public Page<BookDTOResponse> listarLivrosAtivos(Pageable pageable) {
        return bookRepository.buscarTodosAtivos(pageable)
                .map(BookDTOResponse::new);
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

        Book newBook = bookMapper.toEntity(dtoRequest);

        Book savedBook = bookRepository.save(newBook);
        return new BookDTOResponse(savedBook);
    }

    /**
     * ADMIN: Atualiza os dados de um livro existente.
     * @param bookId O ID do livro a ser atualizado.
     * @param dtoAtualizar O DTO com os novos dados.
     * @return O {@link BookAtualizarDTORequest} da entidade atualizada.
     */
    @Transactional
    public BookDTOResponse atualizarLivroPorId(Integer bookId, BookAtualizarDTORequest dtoAtualizar) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado no catálogo."));

        bookMapper.updateEntity(existingBook, dtoAtualizar);

        Book updatedBook = bookRepository.save(existingBook);
        return new BookDTOResponse(updatedBook);
    }

    /**
     * ADMIN: Ativa ou Desativa um livro (Soft Delete Global).
     */
    @Transactional
    public void atualizarStatusGlobal(Integer bookId, Boolean status) {
        Book verifiedBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado no catálogo."));

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


    /**
     * Busca livros na Open Library filtrando por português e limita a 10 resultados.
     * Retorna os dados já convertidos para o padrão interno (DTO), prontos para cadastro.
     *
     * @param query Título, Autor ou ISBN.
     * @return Lista de livros formatados ou lista vazia se nada for encontrado.
     */
    public List<BookDTORequest> buscarLivrosNaAPIExterna(String query) {
        String queryFormatada = query.trim() + " language:por";

        var response = openLibraryClient.buscarLivros(queryFormatada);

        if (response == null || response.docs() == null) {
            return List.of();
        }

        return response.docs().stream()
                .limit(10)
                .map(bookMapper::toDTORequest)
                .toList();
    }

}
