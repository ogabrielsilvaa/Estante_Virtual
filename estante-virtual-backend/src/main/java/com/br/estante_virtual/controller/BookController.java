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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book", description = "API para gerenciamento de livros do usuário autenticado.")
public class BookController {

    private BookService bookService;

    /**
     * Construtor para injeção de dependência do BookService.
     * @param bookService O serviço que contém a lógica de negócio para livros.
     */
    public BookController(BookService bookService) { this.bookService = bookService; }

    /**
     * Lista todos os livros ativos do usuário autenticado.
     * @return Uma lista de livros do usuário.
     */
    @GetMapping("/listarLivrosAtivos")
    @Operation(summary = "Listar livros ativos.", description = "Endpoint para listar livros com status ATIVO, do usuário logado.")
    public ResponseEntity<List<BookDTOResponse>> listarLivrosAtivos(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(bookService.listarLivrosAtivos(userDetails.getUserId()));
    }

    /**
     * Lista todos os livros inativos do usuário autenticado.
     * @return Uma lista de livros do usuário.
     */
    @GetMapping("/listarLivrosInativos")
    @Operation(summary = "Listar livros inativos.", description = "Endpoint para listar livros com status INATIVO, do usuário logado.")
    public ResponseEntity<List<BookDTOResponse>> listarLivrosInativos(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(bookService.listarLivrosInativos(userDetails.getUserId()));
    }

    /**
     * Busca um livro específico pelo seu ID, garantindo que pertença ao usuário autenticado.
     * @param bookId O ID do livro a ser buscado.
     * @return o livro encontrado.
     */
    @GetMapping("/listarLivroPorId/{bookId}")
    @Operation(summary = "Listar o livro pelo ID dele.", description = "Endpoint para listar um Livro específico do usuário logado.")
    public ResponseEntity<BookDTOResponse> listarLivroPorId(
            @PathVariable("bookId") Integer bookId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        BookDTOResponse dtoResponse = bookService.listarLivroPorId(bookId, userDetails.getUserId());
        return ResponseEntity.ok(dtoResponse);
    }

    /**
     * Cria um novo livro para o usuário autenticado.
     * @param dtoRequest O DTO contendo os dados do livro a ser criado.
     * @return O livro recém-criado.
     */
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar Livro.", description = "Endpoint para cadastrar Livros.")
    public ResponseEntity<BookDTOResponse> cadastrarBook(
            @Valid @RequestBody BookDTORequest dtoRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {

        BookDTOResponse dtoResponse = bookService.cadastrarLivro(dtoRequest, userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    /**
     * Atualiza um livro existente do usuário autenticado.
     * @param bookId O ID do livro a ser atualizado.
     * @param atualizarDTORequest O DTO contendo os dados para atualização (apenas os campos que devem ser alterados).
     * @return O livro autalizado.
     */
    @PatchMapping("/atualizar/{bookId}")
    @Operation(summary = "Atualizar todos os dados do Livro.", description = "Endpoint para atualizar o registro do Livro existente do usuário logado.")
    public ResponseEntity<BookDTOResponse> atualizarLivroPorId(
            @PathVariable("bookId") Integer bookId,
            @RequestBody @Valid BookAtualizarDTORequest atualizarDTORequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {

        BookDTOResponse updatedBook = bookService.atualizarLivroPorId(bookId, atualizarDTORequest, userDetails.getUserId());
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Atualiza o status de leitura (Lendo, Lido, Quero Ler) na estante do usuário.
     * Observação: Retorna UserBookDTOResponse pois a mudança ocorre no vínculo, não na obra.
     */
    @PatchMapping("/atualizarStatusDeLeitura/{bookId}")
    @Operation(summary = "Atualiza o status de leitura do Livro.", description = "Endpoint para atualizar o status de leitura do Livro do usuário logado.")
    public ResponseEntity<UserBookDTOResponse> atualizarStatusDeLeitura(
            @PathVariable("bookId") Integer bookId,
            @RequestParam("status") BookReadingStatus status,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserBookDTOResponse response = bookService.mudarStatusDeLeitura(bookId, userDetails.getUserId(), status);
        return ResponseEntity.ok(response);
    }

}
