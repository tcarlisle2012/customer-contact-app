package com.gfs.gordonnow.service;

import com.gfs.gordonnow.domain.CustomerUnitKey;
import com.gfs.gordonnow.repository.CustomerUnitKeyRepository;
import com.gfs.gordonnow.service.dto.CustomerUnitKeyDTO;
import com.gfs.gordonnow.service.mapper.CustomerUnitKeyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link CustomerUnitKey}.
 */
@Service
@Transactional
public class CustomerUnitKeyService {

    private final Logger log = LoggerFactory.getLogger(CustomerUnitKeyService.class);

    private final CustomerUnitKeyRepository customerUnitKeyRepository;

    private final CustomerUnitKeyMapper customerUnitKeyMapper;

    public CustomerUnitKeyService(CustomerUnitKeyRepository customerUnitKeyRepository, CustomerUnitKeyMapper customerUnitKeyMapper) {
        this.customerUnitKeyRepository = customerUnitKeyRepository;
        this.customerUnitKeyMapper = customerUnitKeyMapper;
    }

    /**
     * Save a customerUnitKey.
     *
     * @param customerUnitKeyDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CustomerUnitKeyDTO> save(CustomerUnitKeyDTO customerUnitKeyDTO) {
        log.debug("Request to save CustomerUnitKey : {}", customerUnitKeyDTO);
        return customerUnitKeyRepository.save(customerUnitKeyMapper.toEntity(customerUnitKeyDTO)).map(customerUnitKeyMapper::toDto);
    }

    /**
     * Update a customerUnitKey.
     *
     * @param customerUnitKeyDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<CustomerUnitKeyDTO> update(CustomerUnitKeyDTO customerUnitKeyDTO) {
        log.debug("Request to update CustomerUnitKey : {}", customerUnitKeyDTO);
        return customerUnitKeyRepository.save(customerUnitKeyMapper.toEntity(customerUnitKeyDTO)).map(customerUnitKeyMapper::toDto);
    }

    /**
     * Partially update a customerUnitKey.
     *
     * @param customerUnitKeyDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<CustomerUnitKeyDTO> partialUpdate(CustomerUnitKeyDTO customerUnitKeyDTO) {
        log.debug("Request to partially update CustomerUnitKey : {}", customerUnitKeyDTO);

        return customerUnitKeyRepository
            .findById(customerUnitKeyDTO.getId())
            .map(existingCustomerUnitKey -> {
                customerUnitKeyMapper.partialUpdate(existingCustomerUnitKey, customerUnitKeyDTO);

                return existingCustomerUnitKey;
            })
            .flatMap(customerUnitKeyRepository::save)
            .map(customerUnitKeyMapper::toDto);
    }

    /**
     * Get all the customerUnitKeys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<CustomerUnitKeyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerUnitKeys");
        return customerUnitKeyRepository.findAllBy(pageable).map(customerUnitKeyMapper::toDto);
    }

    /**
     * Returns the number of customerUnitKeys available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return customerUnitKeyRepository.count();
    }

    /**
     * Get one customerUnitKey by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<CustomerUnitKeyDTO> findOne(Long id) {
        log.debug("Request to get CustomerUnitKey : {}", id);
        return customerUnitKeyRepository.findById(id).map(customerUnitKeyMapper::toDto);
    }

    /**
     * Delete the customerUnitKey by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete CustomerUnitKey : {}", id);
        return customerUnitKeyRepository.deleteById(id);
    }

    // custom code
    @Transactional(readOnly = true)
    public Flux<CustomerUnitKeyDTO> findByCustomerNumber(String customerNumber) {
        log.debug("Request to get CustomerUnitKey : {}", customerNumber);
        return customerUnitKeyRepository.findAllByCustomerNumber(customerNumber).map(customerUnitKeyMapper::toDto);
    }
}
