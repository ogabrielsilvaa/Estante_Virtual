package com.br.estante_virtual.controller;

import com.br.estante_virtual.dto.request.readingNote.ReadingNoteAtualizarDTORequest;
import com.br.estante_virtual.dto.request.readingNote.ReadingNoteDTORequest;
import com.br.estante_virtual.dto.response.ReadingNoteDTOResponse;
import com.br.estante_virtual.enums.ReadingNoteStatus;
import com.br.estante_virtual.security.UserDetailsImpl;
import com.br.estante_virtual.service.ReadingNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/readingNotes")
@Tag(name = "Reading Note", description = "API para gerenciamento das notas do usuário autenticado.")
public class ReadingNoteController {

    private ReadingNoteService readingNoteService;

    /**
     * Construtor para injeção de dependência do ReadingNoteService.
     * @param readingNoteService O serviço que contém a lógica de negócio para notas.
     */
    public ReadingNoteController(ReadingNoteService readingNoteService) {
        this.readingNoteService = readingNoteService;
    }

    /**
     * Lista todas as notas com status ATIVO.
     * @param userDetails dados do usuário autenticado.
     * @param status status desejado (ATIVO).
     * @param pageable
     * @return lista de notas ativas com paginação.
     */
    @GetMapping("/listarNotasAtivas")
    @Operation(summary = "Listar notas ativas.", description = "Endpoint para listar notas ativas do usuário logado.")
    public ResponseEntity<Page<ReadingNoteDTOResponse>> listarNotasAtivas(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(defaultValue = "ATIVO") ReadingNoteStatus status,
            @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.ok(
                readingNoteService.listarNotasPorStatus(userDetails.getUserId(), status, pageable)
        );
    }

    /**
     * Busca uma nota específica pelo seu ID, garantindo que pertença ao usuário autenticado.
     * @param noteId ID da nota desejada.
     * @param userDetails dados do usuário autenticado.
     * @return o {@link ReadingNoteDTOResponse} da nota buscada.
     */
    @GetMapping("/listarNotaPorId/{noteId}")
    @Operation(summary = "Listar nota pelo ID.", description = "Endpoint para listar uma nota específica do usuário logado.")
    public ResponseEntity<ReadingNoteDTOResponse> listarNotaPorId(
            @PathVariable("noteId") Integer noteId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ReadingNoteDTOResponse dtoResponse = readingNoteService.listarNotaPorId(noteId, userDetails.getUserId());
        return ResponseEntity.ok(dtoResponse);
    }

    /**
     * Cria uma nova nota para o usuário autenticado.
     * @param dtoRequest o DTO contendo os dados da nota a ser criada.
     * @param userDetails dados do usuário autenticado.
     * @return A nota recém-criada.
     */
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar Nota.", description = "Endpoint para cadastrar Notas.")
    public ResponseEntity<ReadingNoteDTOResponse> cadastrarNota(
            @Valid @RequestBody ReadingNoteDTORequest dtoRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        ReadingNoteDTOResponse dtoResponse = readingNoteService.cadastrarNota(dtoRequest, userDetails.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    /**
     * Atualiza uma nota existente do usuário autenticado.
     * @param noteId ID da nota desejada.
     * @param atualizarDTORequest o DTO contendo os dados para atualização.
     * @param userDetails dados do usuário autenticado.
     * @return o {@link ReadingNoteDTOResponse} da nota com os dados atualizados.
     */
    @PatchMapping("/atualizar/{noteId}")
    @Operation(summary = "Atualizar todos os dados da Nota.", description = "Endpoint para atualizar o registro de uma Nota existente do usuário logado.")
    public ResponseEntity<ReadingNoteDTOResponse> atualizarNotaPorId(
            @PathVariable("noteId") Integer noteId,
            @RequestBody @Valid ReadingNoteAtualizarDTORequest atualizarDTORequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        ReadingNoteDTOResponse updatedReview = readingNoteService.atualizarNotaPorId(noteId, atualizarDTORequest, userDetails.getUserId());
        return ResponseEntity.ok(updatedReview);
    }

    /**
     * Realiza a exclusão lógica de uma nota do usuário autenticado.
     * @param noteId ID da nota desejada.
     * @param userDetails dados do usuário autenticado.
     */
    @DeleteMapping("/deletar/{noteId}")
    @Operation(summary = "Deletar todos os dados de uma Nota.", description = "Endpoint para deletar o registro de uma Nota do usuário logado.")
    public ResponseEntity<Void> deletarNota(
            @PathVariable("noteId") Integer noteId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        readingNoteService.deletarLogico(noteId, userDetails.getUserId());
        return ResponseEntity.noContent().build();
    }
}
