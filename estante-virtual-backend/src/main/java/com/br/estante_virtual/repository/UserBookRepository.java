package com.br.estante_virtual.repository;

import com.br.estante_virtual.entity.UserBook;
import com.br.estante_virtual.entity.primaryKeys.UserBookId;
import com.br.estante_virtual.enums.BookReadingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link UserBook}
 */
@Repository
public interface UserBookRepository extends JpaRepository<UserBook, UserBookId> {

    /**
     * Busca toda a estante de livros do usuário informado.
     *
     * @param userId O ID do usuário dono da estante.
     * @return Uma lista contendo todos os relacionamentos {@link UserBook} deste usuário.
     */
    List<UserBook> findAllByIdUserId(Integer userId);

    /**
     * Busca a estante do usuário de forma paginada.
     *
     * @param userId O ID do usuário dono da estante.
     * @param pageable Objeto contendo as informações de paginação (número da página, tamanho e ordenação).
     * @return Uma {@link Page} contendo a fatia solicitada dos livros do usuário.
     */
    Page<UserBook> findAllByIdUserId(Integer userId, Pageable pageable);

    /**
     * Busca livros do usuário filtrando pelo status (ex: LENDO, LIDO).
     *
     * @param userId O ID do usuário.
     * @param status O status de leitura desejado (enum {@link BookReadingStatus}).
     * @return Uma lista de {@link UserBook} que correspondem ao usuário e ao status fornecido.
     */
    List<UserBook> findAllByIdUserIdAndStatus(Integer userId, BookReadingStatus status);

    /**
     * Verifica se este livro já existe na estante deste usuário.
     *
     * @param id A chave composta {@link UserBookId} contendo o ID do usuário e o ID do livro.
     * @return {@code true} se o relacionamento já existir no banco de dados, caso contrário, {@code false}.
     */
    boolean existsById(UserBookId id);
}
