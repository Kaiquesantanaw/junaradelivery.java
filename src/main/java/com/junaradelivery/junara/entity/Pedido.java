package com.junaradelivery.junara.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.junaradelivery.junara.model.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<PedidoItem> itens = new ArrayList<>();

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

    public void recalcularTotal() {
        this.valorTotal = itens.stream()
                .mapToDouble(PedidoItem::getSubtotal)
                .sum();
    }

    public enum StatusPedido {
        PENDENTE, CONFIRMADO, ENVIADO, ENTREGUE, CANCELADO
    }
}
