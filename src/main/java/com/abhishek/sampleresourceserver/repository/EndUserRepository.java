package com.abhishek.sampleresourceserver.repository;

import com.abhishek.sampleresourceserver.modal.EndUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EndUserRepository extends R2dbcRepository<EndUser, Long> {
    Mono<EndUser> findEndUserByUsername(String username);
}
