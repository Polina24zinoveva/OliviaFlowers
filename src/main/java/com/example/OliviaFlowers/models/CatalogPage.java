package com.example.OliviaFlowers.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "catalogpage")
public class CatalogPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID записи на главной странице

    @ManyToOne
    @JoinColumn(name = "bouquet_id")
    private Bouquet bouquet; // Ссылка на букет

    @Column
    private String description; // Описание букета
}
