package com.br.estante_virtual.dto.request.user;

public record UserLoginDTORequest(
        String email,
        String password
) {
}
