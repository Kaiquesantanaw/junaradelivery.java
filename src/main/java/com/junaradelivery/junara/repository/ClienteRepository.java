package com.junaradelivery.junara.repository;

import com.junaradelivery.junara.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
