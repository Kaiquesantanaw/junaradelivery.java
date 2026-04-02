package com.junaradelivery.junara.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private Double preco;
    private String imagemUrl;
}
