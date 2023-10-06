package com.gfs.gordonnow.web.rest;

import com.gfs.gordonnow.domain.CustomerUnitKey;
import com.gfs.gordonnow.repository.CustomerContactRepository;
import com.gfs.gordonnow.service.CustomerContactService;
import com.gfs.gordonnow.service.CustomerUnitKeyService;
import com.gfs.gordonnow.service.dto.CustomerContactDTO;
import com.gfs.gordonnow.service.dto.CustomerUnitKeyDTO;
import com.gfs.gordonnow.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.gfs.gordonnow.domain.CustomerContact}.
 */
@RestController
@RequestMapping("/api")
public class CustomerContactResource {

    private final Logger log = LoggerFactory.getLogger(CustomerContactResource.class);

    private static final String ENTITY_NAME = "customerContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustomerContactService customerContactService;

    private final CustomerContactRepository customerContactRepository;

    public CustomerContactResource(CustomerContactService customerContactService, CustomerContactRepository customerContactRepository) {
        this.customerContactService = customerContactService;
        this.customerContactRepository = customerContactRepository;
    }

    /**
     * {@code POST  /customer-contacts} : Create a new customerContact.
     *
     * @param customerContactDTO the customerContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerContactDTO, or with status {@code 400 (Bad Request)} if the customerContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer-contacts")
    public Mono<ResponseEntity<CustomerContactDTO>> createCustomerContact(@Valid @RequestBody CustomerContactDTO customerContactDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustomerContact : {}", customerContactDTO);
        if (customerContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new customerContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return customerContactService
            .save(customerContactDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/customer-contacts/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /customer-contacts/:id} : Updates an existing customerContact.
     *
     * @param id the id of the customerContactDTO to save.
     * @param customerContactDTO the customerContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerContactDTO,
     * or with status {@code 400 (Bad Request)} if the customerContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer-contacts/{id}")
    public Mono<ResponseEntity<CustomerContactDTO>> updateCustomerContact(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustomerContactDTO customerContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustomerContact : {}, {}", id, customerContactDTO);
        if (customerContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return customerContactRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return customerContactService
                    .update(customerContactDTO)
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
     * {@code PATCH  /customer-contacts/:id} : Partial updates given fields of an existing customerContact, field will ignore if it is null
     *
     * @param id the id of the customerContactDTO to save.
     * @param customerContactDTO the customerContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerContactDTO,
     * or with status {@code 400 (Bad Request)} if the customerContactDTO is not valid,
     * or with status {@code 404 (Not Found)} if the customerContactDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the customerContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/customer-contacts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<CustomerContactDTO>> partialUpdateCustomerContact(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustomerContactDTO customerContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustomerContact partially : {}, {}", id, customerContactDTO);
        if (customerContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, customerContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return customerContactRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<CustomerContactDTO> result = customerContactService.partialUpdate(customerContactDTO);

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
     * {@code GET  /customer-contacts} : get all the customerContacts.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customerContacts in body.
     */
    @GetMapping("/customer-contacts")
    public Mono<ResponseEntity<List<CustomerContactDTO>>> getAllCustomerContacts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of CustomerContacts");
        return customerContactService
            .countAll()
            .zipWith(customerContactService.findAll(pageable).collectList())
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
     * {@code GET  /customer-contacts/:id} : get the "id" customerContact.
     *
     * @param id the id of the customerContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer-contacts/{id}")
    public Mono<ResponseEntity<CustomerContactDTO>> getCustomerContact(@PathVariable Long id) {
        log.debug("REST request to get CustomerContact : {}", id);
        Mono<CustomerContactDTO> customerContactDTO = customerContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(customerContactDTO);
    }

    /**
     * {@code DELETE  /customer-contacts/:id} : delete the "id" customerContact.
     *
     * @param id the id of the customerContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer-contacts/{id}")
    public Mono<ResponseEntity<Void>> deleteCustomerContact(@PathVariable Long id) {
        log.debug("REST request to delete CustomerContact : {}", id);
        return customerContactService
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
