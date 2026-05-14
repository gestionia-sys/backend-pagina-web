package com.medicalDuarte.panelAdministrativo.util;

import com.medicalDuarte.panelAdministrativo.model.ERole;
import com.medicalDuarte.panelAdministrativo.model.Role;
import com.medicalDuarte.panelAdministrativo.model.User;
import com.medicalDuarte.panelAdministrativo.repository.RoleRepository;
import com.medicalDuarte.panelAdministrativo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // 1. Crear Roles si no existen
        Role adminRole = createRoleIfNotFound(ERole.ROLE_ADMIN);
        Role editorRole = createRoleIfNotFound(ERole.ROLE_EDITOR);
        Role readerRole = createRoleIfNotFound(ERole.ROLE_READER);

        // 2. Crear Superusuario Inicial
        String adminUsername = "admin_duarte";
        if (!userRepository.existsByUsername(adminUsername)) {

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User admin = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode("clinica2026*"))
                    .roles(roles)
                    .build();

            userRepository.save(admin);
            System.out.println(">>> Base de datos inicializada: Usuario '" + adminUsername + "' creado.");
        }
    }

    private Role createRoleIfNotFound(ERole name) {
        return roleRepository.findByName(name).orElseGet(() -> {
            Role role = new Role();
            role.setName(name);
            return roleRepository.save(role);
        });
    }
}
