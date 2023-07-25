package com.abhishek.sampleresourceserver.repository;

import com.abhishek.sampleresourceserver.modal.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndUserRepository extends JpaRepository<EndUser, Long> {

    EndUser findEndUserByUsername(String username);
}
