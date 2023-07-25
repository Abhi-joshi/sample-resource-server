package com.abhishek.sampleresourceserver.repository;

import com.abhishek.sampleresourceserver.modal.EndUser;
import com.abhishek.sampleresourceserver.modal.EndUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EndUserRoleRepository extends JpaRepository<EndUserRole, Long> {
    List<EndUserRole> findEndUserRoleByEndUser(EndUser endUser);
}
