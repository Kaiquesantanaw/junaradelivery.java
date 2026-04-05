package com.junaradelivery.junara.repository;

import com.junaradelivery.junara.entity.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    List<PedidoItem> findByPedidoId(Long pedidoId);

    Optional<PedidoItem> findByPedidoIdAndProdutoId(Long pedidoId, Long produtoId);

    void deleteByPedidoIdAndProdutoId(Long pedidoId, Long produtoId);
}
