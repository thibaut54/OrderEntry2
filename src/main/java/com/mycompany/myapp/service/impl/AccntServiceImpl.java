package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.AccntService;
import com.mycompany.myapp.domain.Accnt;
import com.mycompany.myapp.repository.AccntRepository;
import com.mycompany.myapp.service.dto.AccntDTO;
import com.mycompany.myapp.service.mapper.AccntMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Accnt}.
 */
@Service
@Transactional
public class AccntServiceImpl implements AccntService {

    private final Logger log = LoggerFactory.getLogger(AccntServiceImpl.class);

    private final AccntRepository accntRepository;

    private final AccntMapper accntMapper;

    public AccntServiceImpl(AccntRepository accntRepository, AccntMapper accntMapper) {
        this.accntRepository = accntRepository;
        this.accntMapper = accntMapper;
    }

    @Override
    public AccntDTO save(AccntDTO accntDTO) {
        log.debug("Request to save Accnt : {}", accntDTO);
        Accnt accnt = accntMapper.toEntity(accntDTO);
        accnt = accntRepository.save(accnt);
        return accntMapper.toDto(accnt);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccntDTO> findAll() {
        log.debug("Request to get all Accnts");
        return accntRepository.findAll().stream()
            .map(accntMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AccntDTO> findOne(Long id) {
        log.debug("Request to get Accnt : {}", id);
        return accntRepository.findById(id)
            .map(accntMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Accnt : {}", id);
        accntRepository.deleteById(id);
    }
}
