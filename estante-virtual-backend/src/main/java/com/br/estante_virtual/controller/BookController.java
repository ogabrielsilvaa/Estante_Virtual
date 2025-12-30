package com.br.estante_virtual.controller;

import com.br.estante_virtual.dto.request.book.BookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.book.BookDTORequest;
import com.br.estante_virtual.dto.response.BookDTOResponse;
import com.br.estante_virtual.dto.response.UserBookDTOResponse;
import com.br.estante_virtual.enums.BookReadingStatus;
import com.br.estante_virtual.security.UserDetailsImpl;
import com.br.estante_virtual.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book (Catálogo Global)", description = "API para gerenciamento do catálogo de livros do sistema.")
public class BookController {

    private BookService bookService;

    /**
     * Construtor para injeção de dependência do BookService.
     * @param bookService O serviço que contém a lógica de negócio para livros.
     */
    public BookController(BookService bookService) { this.bookService = bookService; }

    /**
     * Lista todos os livros ativos do sistema.
     * @return Uma lista de livros.
     */
    @GetMapping("/listarCatalogo")
    @Operation(summary = "Listar catálogo.", description = "Lista todos os livros ativos disponíveis no sistema.")
    public ResponseEntity<Page<BookDTOResponse>> listarCatalogo(
            @PageableDefault(size = 10, sort = "tittle")Pageable pageable
            ) {
        return ResponseEntity.ok(bookService.listarLivrosAtivos(pageable));
    }

    /**
     * Busca um livro específico pelo seu ID.
     * @param bookId O ID do livro a ser buscado.
     * @return o livro encontrado.
     */
    @GetMapping("/listarLivroPorId/{bookId}")
    @Operation(summary = "Listar o livro pelo ID dele.", description = "Endpoint para listar um Livro específico do sistema.")
    public ResponseEntity<BookDTOResponse> listarLivroPorId(
            @PathVariable("bookId") Integer bookId
    ) {
        BookDTOResponse dtoResponse = bookService.listarLivroPorId(bookId);
        return ResponseEntity.ok(dtoResponse);
    }

    /**
     * Cria um novo livro para o catálogo global.
     * @param dtoRequest O DTO contendo os dados do livro a ser criado.
     * @return O livro recém-criado.
     */
    @PostMapping("/cadastrar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @Operation(summary = "Cadastrar Livro.", description = "Adiciona um novo livro ao catálogo global.")
    public ResponseEntity<BookDTOResponse> cadastrar(
            @Valid @RequestBody BookDTORequest dtoRequest
            ) {

        BookDTOResponse dtoResponse = bookService.cadastrarLivro(dtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    /**
     * Atualiza um livro existente do usuário autenticado.
     * @param bookId O ID do livro a ser atualizado.
     * @param atualizarDTORequest O DTO contendo os dados para atualização (apenas os campos que devem ser alterados).
     * @return O livro autalizado.
     */
    @PutMapping("/atualizar/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @Operation(summary = "Atualizar Livro.", description = "Endpoint para atualizar o registro do Livro existente.")
    public ResponseEntity<BookDTOResponse> atualizarLivroPorId(
            @PathVariable("bookId") Integer bookId,
            @RequestBody @Valid BookAtualizarDTORequest atualizarDTORequest
            ) {

        BookDTOResponse updatedBook = bookService.atualizarLivroPorId(bookId, atualizarDTORequest);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Mudar o status global de um livro no sistema.
     * @param bookId ID do livro que vai atualizar o status.
     * @param status Status que será atualizado no livro.
     */
    @PatchMapping("/mudarStatus/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mudar Status Global (Admin).", description = "Ativa ou inativa um livro no catálogo.")
    public ResponseEntity<Void> mudarStatus(
            @PathVariable Integer bookId,
            @RequestBody Boolean status
    ) {
        bookService.atualizarStatusGlobal(bookId, status);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para buscar livros na API externa (Open Library).
     * Repassa a consulta para o serviço e retorna os dados formatados para pré-cadastro.
     *
     * @param query Termo de pesquisa (Título, Autor ou ISBN).
     * @return Lista de livros encontrados (200 OK) ou erro 400 se a busca for vazia.
     */
    @GetMapping("/external-search")
    @Operation(summary = "Buscar livros na Open Library.",
            description = "Pesquisa livros na API externa e retorna objetos prontos para serem usados no cadastro.")
    public ResponseEntity<List<BookDTORequest>> buscarLivrosExternos(
            @RequestParam String query
    ) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bookService.buscarLivrosNaAPIExterna(query));
    }

}
