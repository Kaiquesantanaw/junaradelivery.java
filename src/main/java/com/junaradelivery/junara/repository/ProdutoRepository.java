package com.junaradelivery.junara.repository;

import com.junaradelivery.junara.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
