package com.medicalDuarte.panelAdministrativo.service;

import com.medicalDuarte.panelAdministrativo.dto.AuthResponse;
import com.medicalDuarte.panelAdministrativo.dto.LoginRequest;
import com.medicalDuarte.panelAdministrativo.dto.RegisterRequest;

public interface AuthService {
    void registerUser(RegisterRequest request);
    AuthResponse authenticateUser(LoginRequest request);
}
