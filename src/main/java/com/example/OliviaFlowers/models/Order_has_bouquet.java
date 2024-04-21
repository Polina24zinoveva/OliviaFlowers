package com.example.OliviaFlowers.models;//package com.example.OliviaFlowers.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order_has_bouquet {
    @Id
    private Long idOrder; //id заказа
    @Id
    private Long idBouquet; //id букета
    @Column
    private Long count; //Количество букетов
}