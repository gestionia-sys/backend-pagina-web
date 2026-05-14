package com.medicalDuarte.panelAdministrativo.repository;

import com.medicalDuarte.panelAdministrativo.model.PageSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageSectionRepository extends JpaRepository<PageSection, Long> {
    // Aquí podrías añadir métodos para buscar secciones específicas si fuera necesario
}
