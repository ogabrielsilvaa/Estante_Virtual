package com.br.estante_virtual.dto;

import com.br.estante_virtual.dto.response.user.UserLoginDTOResponse;

public record RecoveryJwtTokenDTO(
        String token,
        UserLoginDTOResponse user
) {
}
