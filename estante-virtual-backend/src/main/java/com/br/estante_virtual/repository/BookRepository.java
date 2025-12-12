package com.br.estante_virtual.repository;

import com.br.estante_virtual.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link com.br.estante_virtual.entity.Book}.
 * Estende JpaRepository para funcionalidades CRUD padrão e define consultas customizadas.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

//    @Query("SELECT ub.book FROM UserBook ub WHERE ub.user.id = :userId")
//    List<Book> showBooks(@Param("userId") Integer userId);
//
//    @Query("SELECT ub.book FROM UserBook ub WHERE ub.book.id = :bookId AND ub.user.id = :userId AND ub.user.status = com.br.estante_virtual.enums.UserStatus.ATIVO")
//    Book showBookForId(@Param("bookId") Integer bookId, @Param("userId") Integer userId);

}
