package com.br.estante_virtual.dto.response.user;

import com.br.estante_virtual.enums.UserStatus;

import java.time.LocalDateTime;

public class UserLoginDTOResponse {

    private Integer id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private UserStatus status;

    public UserLoginDTOResponse() {
    }

    public UserLoginDTOResponse(Integer id, String name, String email, LocalDateTime createdAt, UserStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.status = status;
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
}
