package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AccntDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Accnt}.
 */
public interface AccntService {

    /**
     * Save a accnt.
     *
     * @param accntDTO the entity to save.
     * @return the persisted entity.
     */
    AccntDTO save(AccntDTO accntDTO);

    /**
     * Get all the accnts.
     *
     * @return the list of entities.
     */
    List<AccntDTO> findAll();


    /**
     * Get the "id" accnt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccntDTO> findOne(Long id);

    /**
     * Delete the "id" accnt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
