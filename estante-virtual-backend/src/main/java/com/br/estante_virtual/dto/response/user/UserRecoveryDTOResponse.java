package com.br.estante_virtual.dto.response.user;

import com.br.estante_virtual.entity.Role;

import java.util.List;

public record UserRecoveryDTOResponse(
        Long id,
        String email,
        List<Role> roles
) {
}
