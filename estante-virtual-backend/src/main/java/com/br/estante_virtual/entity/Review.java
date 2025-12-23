package com.br.estante_virtual.entity;

import com.br.estante_virtual.enums.RatingLevel;
import com.br.estante_virtual.enums.ReviewStatus;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review implements Serializable {

    /**
     * Identificador único da review, gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer id;

    /**
     * Vínculo com o livro na estante do usuário que realizou a avaliação.
     */
    @ManyToOne
    @JoinColumn(name = "user_book_id", nullable = false)
    private UserBook userBook;

    /**
     * Nota atribuída ao enredo/trama da obra.
     */
    @Column(name = "review_rating_plot")
    private RatingLevel ratingPlot;

    /**
     * Nota atribuída ao desenvolvimento dos personagens.
     */
    @Column(name = "review_rating_characters")
    private RatingLevel ratingCharacters;

    /**
     * Nota atribuída ao estilo de escrita do autor.
     */
    @Column(name = "review_rating_writing")
    private RatingLevel ratingWriting;

    /**
     * Nota atribuída ao nível de imersão da leitura.
     */
    @Column(name = "review_rating_immersion")
    private RatingLevel ratingImmersion;

    /**
     * Conteúdo textual da resenha.
     */
    @Column(name = "review_text", columnDefinition = "TEXT")
    private String text;

    /**
     * Data e hora da criação do registro (imutável).
     */
    @Column(name = "review_created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Estado atual da publicação (ex: Rascunho, Publicada).
     */
    @Column(name = "review_status")
    private ReviewStatus status;

    public Review() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = ReviewStatus.RASCUNHO;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserBook getUserBook() {
        return userBook;
    }

    public void setUserBook(UserBook userBook) {
        this.userBook = userBook;
    }

    public RatingLevel getRatingPlot() {
        return ratingPlot;
    }

    public void setRatingPlot(RatingLevel ratingPlot) {
        this.ratingPlot = ratingPlot;
    }

    public RatingLevel getRatingCharacters() {
        return ratingCharacters;
    }

    public void setRatingCharacters(RatingLevel ratingCharacters) {
        this.ratingCharacters = ratingCharacters;
    }

    public RatingLevel getRatingWriting() {
        return ratingWriting;
    }

    public void setRatingWriting(RatingLevel ratingWriting) {
        this.ratingWriting = ratingWriting;
    }

    public RatingLevel getRatingImmersion() {
        return ratingImmersion;
    }

    public void setRatingImmersion(RatingLevel ratingImmersion) {
        this.ratingImmersion = ratingImmersion;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ReviewStatus getStatus() {
        return status;
    }

    public void setStatus(ReviewStatus status) {
        this.status = status;
    }
}
