package com.abhishek.sampleresourceserver.repository;

import com.abhishek.sampleresourceserver.modal.EndUserRole;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EndUserRoleRepository extends R2dbcRepository<EndUserRole, Long> {
    Flux<EndUserRole> findEndUserRoleByEndUserId(long endUserId);
}
