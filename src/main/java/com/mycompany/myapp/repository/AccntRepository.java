package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Accnt;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Accnt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccntRepository extends JpaRepository<Accnt, Long> {
}
