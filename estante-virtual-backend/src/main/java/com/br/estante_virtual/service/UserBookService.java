package com.br.estante_virtual.service;

import com.br.estante_virtual.dto.request.userBook.UserBookAtualizarDTORequest;
import com.br.estante_virtual.dto.request.userBook.UserBookDTORequest;
import com.br.estante_virtual.dto.response.UserBookDTOResponse;
import com.br.estante_virtual.entity.Book;
import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.entity.UserBook;
import com.br.estante_virtual.enums.BookReadingStatus;
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

    @Autowired
    public UserBookService(
            UserBookRepository userBookRepository,
            UserRepository userRepository,
            BookRepository bookRepository
    ) {
        this.userBookRepository = userBookRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
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
            throw new RuntimeException("Este livro já está na sua estante.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Book book = bookRepository.buscarPorIdAtivo(dtoRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado."));

        UserBook novoRegistro = montarUserBook(user, book, dtoRequest);

        userBookRepository.save(novoRegistro);
        return new UserBookDTOResponse(novoRegistro);
    }

    /**
     * Método auxiliar privado para instanciar e popular o UserBook.
     * Isola a complexidade de mapeamento DTO -> Entity.
     */
    private UserBook montarUserBook(User user, Book book, UserBookDTORequest dto) {
        UserBook userBook = new UserBook();

        userBook.setUser(user);
        userBook.setBook(book);
        userBook.setReadingStatus(dto.getReadingStatus());
        userBook.setStatusActive(true);
        userBook.setPagesRead(dto.getPagesRead() != null ? dto.getPagesRead() : 0);
        userBook.setStartDate(dto.getStartDate());
        userBook.setFinishDate(dto.getFinishDate());
        userBook.setFavorite(dto.getFavorite() != null ? dto.getFavorite() : false);

        return userBook;
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

        atualizarCampos(userBook, dtoRequest);

        return new UserBookDTOResponse(userBookRepository.save(userBook));
    }

    /**
     * Método auxiliar privado para transferir os dados do DTO para a Entidade.
     * Verifica campo a campo se houve alteração (não nulo).
     */
    private void atualizarCampos(UserBook userBook, UserBookAtualizarDTORequest dtoRequest) {
        if (dtoRequest.getPagesRead() != null) {
            userBook.setPagesRead(dtoRequest.getPagesRead());
        }

        if (dtoRequest.getStartDate() != null) {
            userBook.setStartDate(dtoRequest.getStartDate());
        }

        if (dtoRequest.getFinishDate() != null) {
            userBook.setFinishDate(dtoRequest.getFinishDate());
        }

        if (dtoRequest.getFavorite() != null) {
            userBook.setFavorite(dtoRequest.getFavorite());
        }
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
