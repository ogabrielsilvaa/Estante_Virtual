package com.br.estante_virtual.repository;

import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.enums.UserStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link User}
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Busca um usuário ativo pelo seu ID. Ideal para buscar o perfil do próprio usuário logado.
     *
     * @param userId o ID do usuário a ser buscado.
     */
    @Query("SELECT u FROM User u WHERE u.id = :userId")
    User listarUsuarioPorId(@Param("userId") Integer userId);

    /**
     * Realiza a exclusão lógica do usuário, alterando seu status para inativo.
     *
     * @param userId O ID do usuário a ser desativado.
     * @param status O status de inativação (ex: UserStatus.INATIVO).
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = :statusInativo WHERE u.id = :userId")
    void apagarUsuarioLogico(@Param("userId") Integer userId, @Param("statusInativo") UserStatus status);

    /**
     * Busca um usuário pelo seu endereço de e-mail.
     * Essencial para verificar se um e-mail já está em uso durante o cadastro.
     * @param email O e-mail a ser verificado.
     * @return Um {@link Optional} contendo o usuário, se encontrado.
     */
    Optional<User> findByEmail(String email);
}
