package com.medicalDuarte.panelAdministrativo.service;

import com.medicalDuarte.panelAdministrativo.model.Page;

import java.util.List;

public interface PageService {
    List<Page> getAllPages();
    Page getPageBySlug(String slug);
    Page savePage(Page page); // Sirve para crear y actualizar
    void deletePage(Long id);
}