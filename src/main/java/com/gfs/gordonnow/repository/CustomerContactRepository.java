package com.gfs.gordonnow.repository;

import com.gfs.gordonnow.domain.CustomerContact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the CustomerContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerContactRepository extends ReactiveCrudRepository<CustomerContact, Long>, CustomerContactRepositoryInternal {
    Flux<CustomerContact> findAllBy(Pageable pageable);

    @Override
    Mono<CustomerContact> findOneWithEagerRelationships(Long id);

    @Override
    Flux<CustomerContact> findAllWithEagerRelationships();

    @Override
    Flux<CustomerContact> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM customer_contact entity WHERE entity.customer_unit_key_id = :id")
    Flux<CustomerContact> findByCustomerUnitKey(Long id);

    @Query("SELECT * FROM customer_contact entity WHERE entity.customer_unit_key_id IS NULL")
    Flux<CustomerContact> findAllWhereCustomerUnitKeyIsNull();

    @Override
    <S extends CustomerContact> Mono<S> save(S entity);

    @Override
    Flux<CustomerContact> findAll();

    @Override
    Mono<CustomerContact> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface CustomerContactRepositoryInternal {
    <S extends CustomerContact> Mono<S> save(S entity);

    Flux<CustomerContact> findAllBy(Pageable pageable);

    Flux<CustomerContact> findAll();

    Mono<CustomerContact> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<CustomerContact> findAllBy(Pageable pageable, Criteria criteria);

    Mono<CustomerContact> findOneWithEagerRelationships(Long id);

    Flux<CustomerContact> findAllWithEagerRelationships();

    Flux<CustomerContact> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
