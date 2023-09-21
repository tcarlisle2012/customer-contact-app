package com.gfs.gordonnow.service;

import com.gfs.gordonnow.domain.CustomerContact;
import com.gfs.gordonnow.repository.CustomerContactRepository;
import com.gfs.gordonnow.service.dto.CustomerContactDTO;
import com.gfs.gordonnow.service.mapper.CustomerContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link CustomerContact}.
 */
@Service
@Transactional
public class CustomerContactService {

    private final Logger log = LoggerFactory.getLogger(CustomerContactService.class);

    private final CustomerContactRepository customerContactRepository;

    private final CustomerContactMapper customerContactMapper;

    public CustomerContactService(CustomerContactRepository customerContactRepository, CustomerContactMapper customerContactMapper) {
        this.customerContactRepository = customerContactRepository;
        this.customerContactMapper = customerContactMapper;
    }

    /**
     * Save a customerContact.
     *
     * @param customerContactDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CustomerContactDTO> save(CustomerContactDTO customerContactDTO) {
        log.debug("Request to save CustomerContact : {}", customerContactDTO);
        return customerContactRepository.save(customerContactMapper.toEntity(customerContactDTO)).map(customerContactMapper::toDto);
    }

    /**
     * Update a customerContact.
     *
     * @param customerContactDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CustomerContactDTO> update(CustomerContactDTO customerContactDTO) {
        log.debug("Request to update CustomerContact : {}", customerContactDTO);
        return customerContactRepository.save(customerContactMapper.toEntity(customerContactDTO)).map(customerContactMapper::toDto);
    }

    /**
     * Partially update a customerContact.
     *
     * @param customerContactDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<CustomerContactDTO> partialUpdate(CustomerContactDTO customerContactDTO) {
        log.debug("Request to partially update CustomerContact : {}", customerContactDTO);

        return customerContactRepository
            .findById(customerContactDTO.getId())
            .map(existingCustomerContact -> {
                customerContactMapper.partialUpdate(existingCustomerContact, customerContactDTO);

                return existingCustomerContact;
            })
            .flatMap(customerContactRepository::save)
            .map(customerContactMapper::toDto);
    }

    /**
     * Get all the customerContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<CustomerContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerContacts");
        return customerContactRepository.findAllBy(pageable).map(customerContactMapper::toDto);
    }

    /**
     * Get all the customerContacts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Flux<CustomerContactDTO> findAllWithEagerRelationships(Pageable pageable) {
        return customerContactRepository.findAllWithEagerRelationships(pageable).map(customerContactMapper::toDto);
    }

    /**
     * Returns the number of customerContacts available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return customerContactRepository.count();
    }

    /**
     * Get one customerContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<CustomerContactDTO> findOne(Long id) {
        log.debug("Request to get CustomerContact : {}", id);
        return customerContactRepository.findOneWithEagerRelationships(id).map(customerContactMapper::toDto);
    }

    /**
     * Delete the customerContact by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete CustomerContact : {}", id);
        return customerContactRepository.deleteById(id);
    }
}
