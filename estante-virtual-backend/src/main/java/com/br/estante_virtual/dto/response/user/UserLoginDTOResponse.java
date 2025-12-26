package com.br.estante_virtual.dto.response.user;

import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class UserLoginDTOResponse {

    private Integer id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private UserStatus status;
    private Set<String> role;

    public UserLoginDTOResponse() {
    }

    public UserLoginDTOResponse(Integer id, String name, String email, LocalDateTime createdAt, UserStatus status, Set<String> role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.status = status;
        this.role = role;
    }

    public UserLoginDTOResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.status = user.getStatus();
        this.role = user.getRoles().stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());
    }

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

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
