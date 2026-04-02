package com.junaradelivery.junara.repository;

import com.junaradelivery.junara.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(Pedido.StatusPedido status);
}
