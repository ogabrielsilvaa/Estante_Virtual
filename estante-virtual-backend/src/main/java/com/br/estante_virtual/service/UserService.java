package com.br.estante_virtual.service;

import com.br.estante_virtual.dto.RecoveryJwtTokenDTO;
import com.br.estante_virtual.dto.request.user.UserAtualizarDTORequest;
import com.br.estante_virtual.dto.request.user.UserDTORequest;
import com.br.estante_virtual.dto.request.user.UserLoginDTORequest;
import com.br.estante_virtual.dto.response.user.UserDTOResponse;
import com.br.estante_virtual.dto.response.user.UserLoginDTOResponse;
import com.br.estante_virtual.entity.Role;
import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.enums.RoleName;
import com.br.estante_virtual.enums.UserStatus;
import com.br.estante_virtual.repository.RoleRepository;
import com.br.estante_virtual.repository.UserRepository;
import com.br.estante_virtual.security.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Camada de serviço para gerenciar a lógica de negócio dos Usuários.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenService jwtTokenService,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.roleRepository = roleRepository;
    }

    /**
     * Busca o perfil completo do usuário autenticado.
     * @param userId O ID do usuário autenticado (vindo do token).
     * @return O {@link UserDTOResponse} correspondente ao seu perfil.
     */
    public UserDTOResponse buscarMeuPerfil(Integer userId) {
        User user = validarUser(userId);
        return new UserDTOResponse(user);
    }

    /**
     * Cria um novo usuário no sistema com a permissão padrão de cliente.
     * Este método faz parte do fluxo de segurança e registro.
     *
     * @param userDTORequest DTO contendo os dados(nome, email, senha) do novo usuário.
     * @throws RuntimeException se já existir um usuário cadastrado com o mesmo e-mail,
     * ou se a role padrão 'ROLE_CUSTOMER' não for encontrada no sistema.
     */
    public void criarUsuario(UserDTORequest userDTORequest) {
        if (userRepository.findByEmail(userDTORequest.getEmail()).isPresent()) {
            throw new RuntimeException("Usuário com este email já existe.");
        }

        User novoUsuario = new User();
        novoUsuario.setName(userDTORequest.getName());
        novoUsuario.setEmail(userDTORequest.getEmail().toLowerCase());
        novoUsuario.setPassword(passwordEncoder.encode(userDTORequest.getPassword()));

        Role rolePadrao = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Erro crítico: A role padrão 'ROLE_CUSTOMER' não foi encontrada no banco."));

        novoUsuario.setRoles(Set.of(rolePadrao));
        userRepository.save(novoUsuario);
    }

    /**
     * Autentica um usuário com base em suas credenciais e gera um token de acesso JWT.
     * Este método faz parte do fluxo de segurança e login.
     *
     * @param request DTO contendo o e-mail e a senha para autenticação.
     * @return um {@link RecoveryJwtTokenDTO} contendo o token JWT gerado para o usuário autenticado.
     * @throws org.springframework.security.core.AuthenticationException se as credenciais forem inválidas.
     * @throws RuntimeException se o usuário com o e-mail fornecido não for encontrado após a autenticação.
     */
    public RecoveryJwtTokenDTO autenticarUsuario(UserLoginDTORequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email().toLowerCase(), request.password())
        );

        User user = userRepository.findByEmail(request.email().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        String token = jwtTokenService.generateToken(new UserDetailsImpl(user));

        UserLoginDTOResponse dtoResponse = new UserLoginDTOResponse(user);

        return new RecoveryJwtTokenDTO(token, dtoResponse);
    }

    /**
     * Atualiza os dados de um usuário existente.
     * @param userId O ID do usuário autenticado (vindo do token).
     * @param atualizarDTORequest O DTO com os novos dados.
     * @return O {@link UserDTOResponse} da entidade atualizada.
     */
    @Transactional
    public UserDTOResponse autalizarMeuPerfil(Integer userId, UserAtualizarDTORequest atualizarDTORequest) {
        User existingUser = validarUser(userId);

        if (atualizarDTORequest.getName() != null) {
            existingUser.setName(atualizarDTORequest.getName());
        }

        User updatedUser = userRepository.save(existingUser);
        return new UserDTOResponse(updatedUser);
    }

    /**
     * Realiza a exclusão lógica da conta do usuário autenticado.
     * @param userId O ID do usuário autenticado (vindo do token).
     */
    @Transactional
    public void deletarMinhaConta(Integer userId) {
        User user = validarUser(userId);

        user.setStatus(UserStatus.INATIVO);
        userRepository.save(user);
    }

    /**
     * Valida a existência de um usuário pelo seu ID e o retorna.
     * Este é um método auxiliar privado para evitar a repetição de código nos
     * métodos públicos que precisam de buscar uma entidade antes de realizar uma ação.
     *
     * @param userId O ID do usuário a ser validado e buscado (vindo do token).
     * @return A entidade {@link User} encontrada.
     */
    private User validarUser(Integer userId) {
        User user = userRepository.listarUsuarioPorId(userId);
        if (user == null) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + userId);
        }
        return user;
    }

    /**
     * Promove um usuário comum do sistema para administrador.
     * @param userId ID do usuário existente.
     * @return UserLoginDTOResponse mostrando dados do usuário.
     */
    @Transactional
    public UserLoginDTOResponse promoverUsuario(Integer userId) {
        User user = validarUser(userId);

        Role roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Erro crítico: A role padrão 'ROLE_ADMIN' não foi encontrada no banco."));

        user.setRoles(new HashSet<>(Set.of(roleAdmin)));

        User promotedUser = userRepository.save(user);

        return new UserLoginDTOResponse(promotedUser);
    }
}
