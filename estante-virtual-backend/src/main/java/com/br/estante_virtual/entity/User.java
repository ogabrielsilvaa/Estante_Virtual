package com.br.estante_virtual.entity;

import com.br.estante_virtual.enums.UserStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    /**
     * Identificador único do utilizador, gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    /**
     * Nome completo do utilizador.
     */
    @Column(name = "user_name")
    private String name;

    /**
     * Endereço de email do utilizador, usado para login e comunicação.
     * Este campo deve ser único para cada utilizador.
     */
    @Column(name = "user_email", unique = true)
    private String email;

    /**
     * Senha do utilizador.
     * Nota de segurança: esta senha deve ser armazenada no banco de dados
     * de forma criptografada (hash), nunca em texto plano.
     */
    @Column(name = "user_password")
    private String password;

    /**
     * Data em que o utilizador ingressou no sistema.
     */
    @CreationTimestamp
    @Column(name = "user_created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Status atual da conta do utilizador.
     * O valor padrão para um novo utilizador é Ativo.
     */
    @Column(name = "user_status")
    private UserStatus status = UserStatus.ATIVO;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
