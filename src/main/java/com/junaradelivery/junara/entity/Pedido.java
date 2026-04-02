package com.junaradelivery.junara.entity;

import com.junaradelivery.junara.model.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToMany
    @JoinTable(name = "pedido_produtos", joinColumns = @JoinColumn(name = "pedido_id"), inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produtos;

    @Column
    private Double valorTotal;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(name = "data_criacao", updatable = false)
    @Builder.Default
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_atualizacao")
    @Builder.Default
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    public enum StatusPedido {
        PENDENTE, CONFIRMADO, ENVIADO, ENTREGUE, CANCELADO
    }
}
