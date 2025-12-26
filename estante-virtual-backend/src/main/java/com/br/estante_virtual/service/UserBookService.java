package com.br.estante_virtual.service;

import com.br.estante_virtual.dto.request.userBook.UserBookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.userBook.UserBookDTORequest;
import com.br.estante_virtual.dto.response.UserBookDTOResponse;
import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.entity.UserBook;
import com.br.estante_virtual.enums.BookReadingStatus;
import com.br.estante_virtual.mapper.UserBookMapper;
import com.br.estante_virtual.repository.BookRepository;
import com.br.estante_virtual.repository.UserBookRepository;
import com.br.estante_virtual.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookService {

    private final UserBookRepository userBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private final UserBookMapper userBookMapper;

    @Autowired
    public UserBookService(
            UserBookRepository userBookRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            UserBookMapper userBookMapper
    ) {
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userBookMapper = userBookMapper;
    }

    /**
     * Lista todos os registros de livros de todos os usuários.
     * Útil para administradores.
     */
    public List<UserBookDTOResponse> listarEstanteDoUsuario(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }

        return userBookRepository.listarTodosAtivos(userId)
                .stream()
                .map(UserBookDTOResponse::new)
                .toList();
    }

    public UserBookDTOResponse buscarLivroNaEstante(Integer userId, Integer bookId) {
        UserBook userBook = userBookRepository.listarPorId(userId, bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado na estante."));
        return new UserBookDTOResponse(userBook);
    }

    /**
     * Adiciona um novo livro à estante do usuário.
     * @param userId ID do usuário logado.
     * @param dtoRequest DTO com o ID do livro e dados de leitura
     */
    @Transactional
    public UserBookDTOResponse adicionarLivroNaEstante(Integer userId, UserBookDTORequest dtoRequest) {
        if(userBookRepository.verificarLivroNaEstante(userId, dtoRequest.getBookId())) {
            throw new IllegalArgumentException("Este livro já está na sua estante.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        Book book = bookRepository.buscarPorIdAtivo(dtoRequest.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado."));

        UserBook novoRegistro = userBookMapper.toEntity(user, book, dtoRequest);

        userBookRepository.save(novoRegistro);
        return new UserBookDTOResponse(novoRegistro);
    }

    /**
     * Atualiza os dados de um livro existente na estante do usuário logado.
     * @param userId ID do usuário logado.
     * @param bookId ID do livro.
     * @param dtoRequest Dados para atualização.
     * @return UserBookDTOResponse com os dados atualizados.
     */
    @Transactional
    public UserBookDTOResponse atualizarDetalhes(Integer userId, Integer bookId, UserBookAtualizarDTORequest dtoRequest) {
        UserBook userBook = userBookRepository.listarPorId(userId, bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado na estante."));

        userBookMapper.updateEntity(userBook, dtoRequest);

        UserBook updatedUserBook = userBookRepository.save(userBook);
        return new UserBookDTOResponse(updatedUserBook);
    }

    /**
     * Atualiza APENAS o Status de Leitura (Ex: Quero Ler -> Lendo)
     * @param userId ID do usuário logado.
     * @param bookId ID do livro.
     * @param novoStatus Status que vai ser aplicado no livro.
     * @return UserBookDTOResponse com o status atualizado.
     */
    @Transactional
    public UserBookDTOResponse atualizarStatusLeitura(Integer userId, Integer bookId, BookReadingStatus novoStatus) {
        UserBook userBook = userBookRepository.listarPorId(userId, bookId)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado na estante."));

        userBook.setReadingStatus(novoStatus);

        return new UserBookDTOResponse(userBookRepository.save(userBook));
    }


    /**
     * Remove um livro da estante do usuário.
     * @param userId o ID do usuário logado.
     * @param bookId o ID do livro correspondente.
     */
    @Transactional
    public void removerLivroDaEstante(Integer userId, Integer bookId) {
        if (!userBookRepository.verificarLivroNaEstante(userId, bookId)) {
            throw new EntityNotFoundException("Livro não encontrado.");
        }

        userBookRepository.desativarLivro(userId, bookId);
    }

}
