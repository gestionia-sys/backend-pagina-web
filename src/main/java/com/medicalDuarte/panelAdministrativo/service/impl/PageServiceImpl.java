package com.medicalDuarte.panelAdministrativo.service.impl;

import com.medicalDuarte.panelAdministrativo.model.Page;
import com.medicalDuarte.panelAdministrativo.repository.PageRepository;
import com.medicalDuarte.panelAdministrativo.service.PageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;

    public PageServiceImpl(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Page> getAllPages() {
        return pageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page getPageBySlug(String slug) {
        return pageRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("La página con slug '" + slug + "' no existe."));
    }

    @Override
    @Transactional
    public Page savePage(Page page) {
        if (page.getSections() != null) { // Aquí Lombok ya debería reconocer el getter
            page.getSections().forEach(section -> section.setPage(page));
        }
        return pageRepository.save(page);
    }

    @Override
    @Transactional
    public void deletePage(Long id) {
        if (!pageRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Página no encontrada.");
        }
        pageRepository.deleteById(id);
    }
}
