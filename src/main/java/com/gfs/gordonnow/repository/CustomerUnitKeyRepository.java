package com.gfs.gordonnow.repository;

import com.gfs.gordonnow.domain.CustomerUnitKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the CustomerUnitKey entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerUnitKeyRepository extends ReactiveCrudRepository<CustomerUnitKey, Long>, CustomerUnitKeyRepositoryInternal {
    Flux<CustomerUnitKey> findAllBy(Pageable pageable);

    @Override
    <S extends CustomerUnitKey> Mono<S> save(S entity);

    @Override
    Flux<CustomerUnitKey> findAll();

    @Override
    Mono<CustomerUnitKey> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    // custom code
    Flux<CustomerUnitKey> findAllByCustomerNumber(String customerNumber);
}

interface CustomerUnitKeyRepositoryInternal {
    <S extends CustomerUnitKey> Mono<S> save(S entity);

    Flux<CustomerUnitKey> findAllBy(Pageable pageable);

    Flux<CustomerUnitKey> findAll();

    Mono<CustomerUnitKey> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<CustomerUnitKey> findAllBy(Pageable pageable, Criteria criteria);

}
