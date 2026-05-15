package com.medicalDuarte.panelAdministrativo.controller;

import com.medicalDuarte.panelAdministrativo.model.Page;
import com.medicalDuarte.panelAdministrativo.service.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages")
public class PageController {

    private final PageService pageService;

    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    // --- ENDPOINTS PÚBLICOS (Para la web de la clínica) ---

    @GetMapping("/public/detail")
    public ResponseEntity<Page> getPublicPageBySlug(@RequestParam("slug") String slug) {
        // La API ahora se consume así: /api/pages/public/detail?slug=servicios/dermatologia
        return ResponseEntity.ok(pageService.getPublicPageBySlug(slug));
    }

    @GetMapping("/detail")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Page> getAdminPageBySlug(@RequestParam("slug") String slug) {
        // La API ahora se consume así: /api/pages/detail?slug=servicios/dermatologia
        return ResponseEntity.ok(pageService.getPageBySlug(slug));
    }

    @GetMapping
    public ResponseEntity<List<Page>> listAll() {
        // Útil para generar menús de navegación dinámicos
        return ResponseEntity.ok(pageService.getAllPages());
    }

    // --- ENDPOINTS PRIVADOS (Para el Panel Administrativo) ---

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    public ResponseEntity<Page> createOrUpdate(@RequestBody Page page) {
        // Gracias a nuestra lógica en el Service, este método crea o actualiza
        // incluyendo todas las secciones que envíes en el JSON
        return ResponseEntity.status(HttpStatus.CREATED).body(pageService.savePage(page));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pageService.deletePage(id);
        return ResponseEntity.noContent().build();
    }
}
