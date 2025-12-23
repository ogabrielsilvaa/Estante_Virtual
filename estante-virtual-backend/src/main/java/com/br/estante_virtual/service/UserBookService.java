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
    public List<UserBookDTOResponse> listarTudo() {
        return userBookRepository.findAll()
                .stream()
                .map(UserBookDTOResponse::new)
                .toList();
    }

    /**
     * Lista a estante de um usuário específico.
     * @param userId ID do usuário.
     */
    public List<UserBookDTOResponse> listarEstanteDoUsuario(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }

        return userBookRepository.findByUserId(userId)
                .stream()
                .map(UserBookDTOResponse::new)
                .toList();
    }

    /**
     * Adiciona um novo livro à estante do usuário.
     * @param userId ID do usuário logado.
     * @param dtoRequest DTO com o ID do livro e dados de leitura
     */
    @Transactional
    public UserBookDTOResponse adicionarLivroNaEstante(Integer userId, UserBookDTORequest dtoRequest) {
        if(userBookRepository.existsByUserIdAndBookId(userId, dtoRequest.getBookId())) {
            throw new RuntimeException("Este livro já está na sua estante.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        Book book = bookRepository.findById(dtoRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Livro não encontrado."));

        UserBook novoRegistro = new UserBook();
        novoRegistro.setUser(user);
        novoRegistro.setBook(book);

        novoRegistro.setStatus(dtoRequest.getStatus());

        if (dtoRequest.getPagesRead() == null) {
            novoRegistro.setPagesRead(0);
        } else {
            novoRegistro.setPagesRead(dtoRequest.getPagesRead());
        }

        novoRegistro.setStartDate(dtoRequest.getStartDate());
        novoRegistro.setFinishDate(dtoRequest.getFinishDate());
        novoRegistro.setFavorite(dtoRequest.getFavorite());

        userBookRepository.save(novoRegistro);
        return new UserBookDTOResponse(novoRegistro);
    }

    /**
     * Atualiza o status da leitura, páginas lidas, etc.
     * @param userId o ID do usuário logado.
     * @param bookId o ID do livro correspondente.
     * @param atualizarDTORequest DTO com os dados para atualização.
     * @return entidade UserBook atualizada.
     */
    @Transactional
    public UserBookDTOResponse atualizarLeitura(Integer userId, Integer bookId, UserBookAtualizarDTORequest atualizarDTORequest) {
        UserBook userBook = userBookRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado na estante."));

        if (atualizarDTORequest.getStatus() != null) {
            userBook.setStatus(atualizarDTORequest.getStatus());
        }

        if (atualizarDTORequest.getPagesRead() != null) {
            userBook.setPagesRead(atualizarDTORequest.getPagesRead());
        }

        if (atualizarDTORequest.getStartDate() != null) {
            userBook.setStartDate(atualizarDTORequest.getStartDate());
        }

        if (atualizarDTORequest.getFinishDate() != null) {
            userBook.setFinishDate(atualizarDTORequest.getFinishDate());
        }

        if (atualizarDTORequest.getFavorite() != null) {
            userBook.setFavorite(atualizarDTORequest.getFavorite());
        }

        userBookRepository.save(userBook);
        return new UserBookDTOResponse(userBook);
    }

    /**
     * Remove um livro da estante do usuário.
     * @param userId o ID do usuário logado.
     * @param bookId o ID do livro correspondente.
     */
    @Transactional
    public void removerLivroDaEstante(Integer userId, Integer bookId) {
        if (!userBookRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new EntityNotFoundException("Livro não encontrado.");
        }

        userBookRepository.mudarStatusDoLivro(userId, bookId, BookReadingStatus.ABANDONEI);
    }

    public UserBookDTOResponse buscarLivroNaEstante(Integer userId, Integer bookId) {
        UserBook userBook = userBookRepository.findByUserIdAndBookId(userId, bookId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado na estante deste usuário."));

        return new UserBookDTOResponse(userBook);
    }

}
