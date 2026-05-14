package com.medicalDuarte.panelAdministrativo.repository;

import com.medicalDuarte.panelAdministrativo.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    // Buscamos una página por su nombre de URL
    Optional<Page> findBySlug(String slug);

    // Para saber si una URL ya existe antes de crear una nueva
    boolean existsBySlug(String slug);
}