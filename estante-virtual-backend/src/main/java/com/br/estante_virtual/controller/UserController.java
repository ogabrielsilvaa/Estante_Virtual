package com.br.estante_virtual.controller;

import com.br.estante_virtual.dto.RecoveryJwtTokenDTO;
import com.br.estante_virtual.dto.request.user.UserAtualizarDTORequest;
import com.br.estante_virtual.dto.request.user.UserDTORequest;
import com.br.estante_virtual.dto.request.user.UserLoginDTORequest;
import com.br.estante_virtual.dto.response.user.UserDTOResponse;
import com.br.estante_virtual.security.UserDetailsImpl;
import com.br.estante_virtual.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para gerenciar as operações de Usuário.
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuário", description = "API para gerenciamento de usuários.")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Cadastra um novo usuário no sistema.
     * <p>
     * Este endpoint recebe os dados de um novo usuário e os persiste no banco de dados.
     * </p>
     *
     * @param userDTORequest DTO contendo os dados necessários para o cadastro do novo usuário.
     * @return Resposta com status 201 Created.
     */
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar Usuário.", description = "Endpoint para cadastrar Usuários, com SecurityJWT.")
    public ResponseEntity<Void> criarUsuario(@RequestBody UserDTORequest userDTORequest) {
        userService.criarUsuario(userDTORequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Autentica um usuário com base em suas credenciais e retorna um token JWT.
     * <p>
     * Este endpoint recebe um objeto com email e senha. Se as credenciais forem válidas,
     * o serviço de usuário gera e retorna um token de acesso.
     * </p>
     *
     * @param userLoginDTORequest DTO contendo o email e a senha do usuário para autenticação.
     * @return O token JWT para ser usado nas próximas requisições.
     */
    @PostMapping("/login")
    @Operation(summary = "Login de Usuário.", description = "Endpoint para fazer o Login de 1 Usuário cadastrado no Banco.")
    public ResponseEntity<RecoveryJwtTokenDTO> autenticarUsuarioSecurity(@RequestBody UserLoginDTORequest userLoginDTORequest) {
        RecoveryJwtTokenDTO token = userService.autenticarUsuario(userLoginDTORequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * Busca um usuário ativo específico pelo seu ID.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     * @return O perfil completo do usuário logado.
     */
    @GetMapping("/buscarMeuPerfil")
    @Operation(summary = "Buscar meu perfil.", description = "Endpoint para o usuário logado buscar seus próprios dados de perfil.")
    public ResponseEntity<UserDTOResponse> buscarMeuPerfil(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok(userService.buscarMeuPerfil(userDetails.getUserId()));
    }

    /**
     * Atualiza dados de um usuário existente (nome e telefone).
     * @param atualizarDTORequest DTO contendo os dados a serem alterados.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     * @return O perfil do usuário com os dados atualizados.
     */
    @PutMapping("/atualizar")
    @Operation(summary = "Atualizar meu perfil.", description = "Endpoint para o usuário logado atualizar seus dados de perfil.")
    public ResponseEntity<UserDTOResponse> atualizarUsuarioPorId(
            @RequestBody @Valid UserAtualizarDTORequest atualizarDTORequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        Integer userId = userDetails.getUserId();
        UserDTOResponse userAtualizado = userService.autalizarMeuPerfil(userId, atualizarDTORequest);
        return ResponseEntity.ok(userAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um usuário, alterando seu status para INATIVO.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/deletarMinhaConta")
    @Operation(summary = "Deletar minha conta.", description = "Endpoint para o usuário logado deletar (logicamente) sua própria conta.")
    public ResponseEntity<Void> deletarUsuario(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer userId = userDetails.getUserId();
        userService.deletarMinhaConta(userId);
        return ResponseEntity.noContent().build();
    }

}
