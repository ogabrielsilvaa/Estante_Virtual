package com.br.estante_virtual.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserAtualizarDTORequest {

    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String name;

    public UserAtualizarDTORequest() {
    }

    public UserAtualizarDTORequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
