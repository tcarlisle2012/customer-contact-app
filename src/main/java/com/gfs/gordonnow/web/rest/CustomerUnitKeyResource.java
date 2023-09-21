package com.gfs.gordonnow.web.rest;

import com.gfs.gordonnow.repository.CustomerUnitKeyRepository;
import com.gfs.gordonnow.service.CustomerUnitKeyService;
import com.gfs.gordonnow.service.dto.CustomerUnitKeyDTO;
import com.gfs.gordonnow.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.gfs.gordonnow.domain.CustomerUnitKey}.
 */
@RestController
@RequestMapping("/api")
public class CustomerUnitKeyResource {

    private final Logger log = LoggerFactory.getLogger(CustomerUnitKeyResource.class);

    private static final String ENTITY_NAME = "customerUnitKey";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerUnitKeyService customerUnitKeyService;

    private final CustomerUnitKeyRepository customerUnitKeyRepository;

    public CustomerUnitKeyResource(CustomerUnitKeyService customerUnitKeyService, CustomerUnitKeyRepository customerUnitKeyRepository) {
        this.customerUnitKeyService = customerUnitKeyService;
        this.customerUnitKeyRepository = customerUnitKeyRepository;
    }

    /**
     * {@code POST  /customer-unit-keys} : Create a new customerUnitKey.
     *
     * @param customerUnitKeyDTO the customerUnitKeyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerUnitKeyDTO, or with status {@code 400 (Bad Request)} if the customerUnitKey has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-unit-keys")
    public Mono<ResponseEntity<CustomerUnitKeyDTO>> createCustomerUnitKey(@Valid @RequestBody CustomerUnitKeyDTO customerUnitKeyDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomerUnitKey : {}", customerUnitKeyDTO);
        if (customerUnitKeyDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerUnitKey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return customerUnitKeyService
            .save(customerUnitKeyDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/customer-unit-keys/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /customer-unit-keys/:id} : Updates an existing customerUnitKey.
     *
     * @param id the id of the customerUnitKeyDTO to save.
     * @param customerUnitKeyDTO the customerUnitKeyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerUnitKeyDTO,
     * or with status {@code 400 (Bad Request)} if the customerUnitKeyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerUnitKeyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-unit-keys/{id}")
    public Mono<ResponseEntity<CustomerUnitKeyDTO>> updateCustomerUnitKey(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomerUnitKeyDTO customerUnitKeyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerUnitKey : {}, {}", id, customerUnitKeyDTO);
        if (customerUnitKeyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerUnitKeyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return customerUnitKeyRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return customerUnitKeyService
                    .update(customerUnitKeyDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /customer-unit-keys/:id} : Partial updates given fields of an existing customerUnitKey, field will ignore if it is null
     *
     * @param id the id of the customerUnitKeyDTO to save.
     * @param customerUnitKeyDTO the customerUnitKeyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerUnitKeyDTO,
     * or with status {@code 400 (Bad Request)} if the customerUnitKeyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customerUnitKeyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerUnitKeyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-unit-keys/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<CustomerUnitKeyDTO>> partialUpdateCustomerUnitKey(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomerUnitKeyDTO customerUnitKeyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerUnitKey partially : {}, {}", id, customerUnitKeyDTO);
        if (customerUnitKeyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerUnitKeyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return customerUnitKeyRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<CustomerUnitKeyDTO> result = customerUnitKeyService.partialUpdate(customerUnitKeyDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /customer-unit-keys} : get all the customerUnitKeys.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerUnitKeys in body.
     */
    @GetMapping("/customer-unit-keys")
    public Mono<ResponseEntity<List<CustomerUnitKeyDTO>>> getAllCustomerUnitKeys(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of CustomerUnitKeys");
        return customerUnitKeyService
            .countAll()
            .zipWith(customerUnitKeyService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            UriComponentsBuilder.fromHttpRequest(request),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /customer-unit-keys/:id} : get the "id" customerUnitKey.
     *
     * @param id the id of the customerUnitKeyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerUnitKeyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-unit-keys/{id}")
    public Mono<ResponseEntity<CustomerUnitKeyDTO>> getCustomerUnitKey(@PathVariable Long id) {
        log.debug("REST request to get CustomerUnitKey : {}", id);
        Mono<CustomerUnitKeyDTO> customerUnitKeyDTO = customerUnitKeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerUnitKeyDTO);
    }

    /**
     * {@code DELETE  /customer-unit-keys/:id} : delete the "id" customerUnitKey.
     *
     * @param id the id of the customerUnitKeyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-unit-keys/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomerUnitKey(@PathVariable Long id) {
        log.debug("REST request to delete CustomerUnitKey : {}", id);
        return customerUnitKeyService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
