package com.br.estante_virtual.mapper;

import com.br.estante_virtual.dto.request.user.UserAtualizarDTORequest;
import com.br.estante_virtual.dto.request.user.UserDTORequest;
import com.br.estante_virtual.entity.Role;
import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.enums.RoleName;
import com.br.estante_virtual.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserMapper {

    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User toEntity(UserDTORequest dtoRequest) {
        User newUser = new User();
        newUser.setName(dtoRequest.getName());
        newUser.setEmail(dtoRequest.getEmail().toLowerCase());
        newUser.setPassword(passwordEncoder.encode(dtoRequest.getPassword()));

        Role rolePadrao = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Erro crítico: A role padrão 'ROLE_CUSTOMER' não foi encontrada no banco."));

        newUser.setRoles(new HashSet<>(Set.of(rolePadrao)));

        return newUser;
    }

    public void updateEntity(User user, UserAtualizarDTORequest dtoAtualizar) {
        if (dtoAtualizar.getName() != null) {
            user.setName(dtoAtualizar.getName());
        }
    }

}
