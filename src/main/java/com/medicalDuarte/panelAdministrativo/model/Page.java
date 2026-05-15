package com.medicalDuarte.panelAdministrativo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pages")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String slug;

    private String title;

    @Enumerated(EnumType.STRING) // Guarda el texto "DRAFT" en Postgres, no un número
    @Builder.Default
    private PageStatus status = PageStatus.DRAFT;

    // Asegúrate de que este nombre coincida con el atributo 'page' en PageSection
    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @Builder.Default // Evita que Lombok resetee la lista a null al usar Builder
    private List<PageSection> sections = new ArrayList<>();
}
