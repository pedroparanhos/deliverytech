package com.deliverytech.repository;

import com.deliverytech.model.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    boolean existsByEmail(String email);

    // Modificado: Retorna uma 'Page' em vez de uma 'List' e aceita 'Pageable'
    Page<Cliente> findByAtivoTrue(Pageable pageable);
}
