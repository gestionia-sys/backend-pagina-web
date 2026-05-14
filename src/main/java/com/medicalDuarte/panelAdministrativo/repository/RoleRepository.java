package com.medicalDuarte.panelAdministrativo.repository;

import com.medicalDuarte.panelAdministrativo.model.ERole;
import com.medicalDuarte.panelAdministrativo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
