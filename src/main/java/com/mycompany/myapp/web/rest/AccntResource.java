package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.AccntService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.AccntDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Accnt}.
 */
@RestController
@RequestMapping("/api")
public class AccntResource {

    private final Logger log = LoggerFactory.getLogger(AccntResource.class);

    private static final String ENTITY_NAME = "accnt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccntService accntService;

    public AccntResource(AccntService accntService) {
        this.accntService = accntService;
    }

    /**
     * {@code POST  /accnts} : Create a new accnt.
     *
     * @param accntDTO the accntDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accntDTO, or with status {@code 400 (Bad Request)} if the accnt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/accnts")
    public ResponseEntity<AccntDTO> createAccnt(@RequestBody AccntDTO accntDTO) throws URISyntaxException {
        log.debug("REST request to save Accnt : {}", accntDTO);
        if (accntDTO.getId() != null) {
            throw new BadRequestAlertException("A new accnt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccntDTO result = accntService.save(accntDTO);
        return ResponseEntity.created(new URI("/api/accnts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /accnts} : Updates an existing accnt.
     *
     * @param accntDTO the accntDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accntDTO,
     * or with status {@code 400 (Bad Request)} if the accntDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accntDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/accnts")
    public ResponseEntity<AccntDTO> updateAccnt(@RequestBody AccntDTO accntDTO) throws URISyntaxException {
        log.debug("REST request to update Accnt : {}", accntDTO);
        if (accntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AccntDTO result = accntService.save(accntDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accntDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /accnts} : get all the accnts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accnts in body.
     */
    @GetMapping("/accnts")
    public List<AccntDTO> getAllAccnts() {
        log.debug("REST request to get all Accnts");
        return accntService.findAll();
    }

    /**
     * {@code GET  /accnts/:id} : get the "id" accnt.
     *
     * @param id the id of the accntDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accntDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/accnts/{id}")
    public ResponseEntity<AccntDTO> getAccnt(@PathVariable Long id) {
        log.debug("REST request to get Accnt : {}", id);
        Optional<AccntDTO> accntDTO = accntService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accntDTO);
    }

    /**
     * {@code DELETE  /accnts/:id} : delete the "id" accnt.
     *
     * @param id the id of the accntDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/accnts/{id}")
    public ResponseEntity<Void> deleteAccnt(@PathVariable Long id) {
        log.debug("REST request to delete Accnt : {}", id);
        accntService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
