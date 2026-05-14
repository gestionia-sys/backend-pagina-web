package com.medicalDuarte.panelAdministrativo.service.impl;

import com.medicalDuarte.panelAdministrativo.dto.AuthResponse;
import com.medicalDuarte.panelAdministrativo.dto.LoginRequest;
import com.medicalDuarte.panelAdministrativo.dto.RegisterRequest;
import com.medicalDuarte.panelAdministrativo.model.ERole;
import com.medicalDuarte.panelAdministrativo.model.Role;
import com.medicalDuarte.panelAdministrativo.model.User;
import com.medicalDuarte.panelAdministrativo.repository.RoleRepository;
import com.medicalDuarte.panelAdministrativo.repository.UserRepository;
import com.medicalDuarte.panelAdministrativo.security.JwtUtil;
import com.medicalDuarte.panelAdministrativo.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @Transactional
    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya está en uso");
        }

        // Buscamos el rol READER para nuevos registros (Seguridad activa)
        Role readerRole = roleRepository.findByName(ERole.ROLE_READER)
                .orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_READER' no encontrado."));

        Set<Role> roles = new HashSet<>();
        roles.add(readerRole);

        User newUser = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(newUser);
    }

    @Override
    public AuthResponse authenticateUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new SecurityException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new SecurityException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}
