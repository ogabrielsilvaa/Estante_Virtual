package com.br.estante_virtual.config;

import com.br.estante_virtual.entity.Role;
import com.br.estante_virtual.entity.User;
import com.br.estante_virtual.enums.RoleName;
import com.br.estante_virtual.repository.RoleRepository;
import com.br.estante_virtual.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Erro crítico: A role padrão 'ROLE_ADMIN' não foi encontrada no banco."));

        var adminAccount = userRepository.findByEmail("admin@gmail.com");

        if (adminAccount.isEmpty()) {
            User admin = new User();
            admin.setName("Admin Inicial");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRoles(Set.of(roleAdmin));

            userRepository.save(admin);
            System.out.println("Administrador raiz criado com sucesso!");
        } else {
            System.out.println("Administrador raiz já existe!");
        }

    }

}
