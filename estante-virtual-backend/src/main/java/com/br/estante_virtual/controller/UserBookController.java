package com.br.estante_virtual.controller;

import com.br.estante_virtual.dto.request.userBook.UserBookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.userBook.UserBookDTORequest;
import com.br.estante_virtual.dto.response.UserBookDTOResponse;
import com.br.estante_virtual.security.UserDetailsImpl;
import com.br.estante_virtual.service.UserBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável pelo gerenciamento da estante de livros do usuário.
 * Gerencia a relação entre Usuário e Livro (UserBook).
 */
@RestController
@RequestMapping("/api/userBook")
@Tag(name = "UserBook", description = "API para gerenciamento de usuários e seus livros.")
public class UserBookController {

    private UserBookService userBookService;

    public UserBookController(UserBookService userBookService) {
        this.userBookService = userBookService;
    }

    /**
     * Retorna a lista completa de livros na estante do usuário autenticado.
     *
     * @param userDetails Dados do usuário logado (injetado via Token).
     * @return Lista com os dados de leitura de cada livro.
     */
    @GetMapping("/listarEstante")
    @Operation(summary = "Listar Estante", description = "Endpoint para listar a estante de livros de um Usuário.")
    public ResponseEntity<List<UserBookDTOResponse>> listarEstante(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        Integer userId = userDetails.getUserId();
        List<UserBookDTOResponse> estante = userBookService.listarEstanteDoUsuario(userId);

        return ResponseEntity.ok(estante);
    }

    /**
     * Busca os detalhes de um livro específico na estante do usuário.
     *
     * @param userDetails Dados do usuário logado.
     * @param bookId ID do livro a ser buscado.
     * @return Dados da leitura (status, páginas lidas, datas).
     */
    @GetMapping("/listarEstante/{bookId}")
    @Operation(summary = "Buscar livro na estante.", description = "Busca os dados de leitura de um livro específico pelo ID.")
    public ResponseEntity<UserBookDTOResponse> buscarLivroNaEstante(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer bookId
    ) {
        Integer  userId = userDetails.getUserId();
        UserBookDTOResponse dtoResponse = userBookService.buscarLivroNaEstante(userId, bookId);

        return ResponseEntity.ok(dtoResponse);
    }

    /**
     * Adiciona um novo livro à estante do usuário.
     *
     * @param userDetails Dados do usuário logado.
     * @param dtoRequest DTO contendo o ID do livro e dados iniciais de leitura.
     * @return O registro criado com status 201 Created.
     */
    @PostMapping("/adicionarNaEstante")
    @Operation(summary = "Adicionar Livro.", description = "Adiciona um novo livro à estante do usuário.")
    public ResponseEntity<UserBookDTOResponse> adicionarLivro(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid UserBookDTORequest dtoRequest
            ) {
        Integer userId = userDetails.getUserId();
        UserBookDTOResponse dtoResponse = userBookService.adicionarLivroNaEstante(userId, dtoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    /**
     * Atualiza parcialmente os dados de leitura de um livro (status, páginas, notas).
     *
     * @param userDetails Dados do usuário logado.
     * @param bookId ID do livro a ser atualizado.
     * @param atualizarDTORequest DTO com os campos a serem alterados.
     * @return O registro atualizado.
     */
    @PatchMapping("/atualizar/{bookId}")
    @Operation(summary = "Atualizar Leitura", description = "Atualiza status, páginas lidas, nota, etc. Envie apenas os campos que deseja alterar.")
    public ResponseEntity<UserBookDTOResponse> atualizarLeitura(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer bookId,
            @RequestBody @Valid UserBookAtualizarDTORequest atualizarDTORequest
            ) {
        Integer userId = userDetails.getUserId();
        UserBookDTOResponse dtoResponse = userBookService.atualizarLeitura(userId, bookId, atualizarDTORequest);

        return ResponseEntity.ok(dtoResponse);
    }

    /**
     * Remove um livro da estante do usuário.
     *
     * @param userDetails Dados do usuário logado.
     * @param bookId ID do livro a ser removido.
     * @return Retorna 204 No Content se a remoção for bem-sucedida.
     */
    @DeleteMapping("/removerDaEstante/{bookId}")
    @Operation(summary = "Remover Livro", description = "Remove um livro da estante do usuário.")
    public ResponseEntity<Void> removerLivro(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Integer bookId
        ) {
        Integer userId = userDetails.getUserId();
        userBookService.removerLivroDaEstante(userId, bookId);

        return ResponseEntity.noContent().build();
    }

}
