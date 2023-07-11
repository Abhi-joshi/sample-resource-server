package com.abhishek.sampleresourceserver.service.impl;

import com.abhishek.sampleresourceserver.client.UserInfo;
import com.abhishek.sampleresourceserver.modal.EndUser;
import com.abhishek.sampleresourceserver.modal.EndUserRole;
import com.abhishek.sampleresourceserver.repository.EndUserRepository;
import com.abhishek.sampleresourceserver.service.EndUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class EndUserServiceImpl implements EndUserService {

    private final EndUserRepository endUserRepository;
    private final PasswordEncoder passwordEncoder;

    public EndUserServiceImpl(EndUserRepository endUserRepository, PasswordEncoder passwordEncoder) {
        this.endUserRepository = endUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserInfo register(UserInfo userInfo) {
        EndUser endUser = new EndUser();
        endUser.setUsername(userInfo.getUsername());
        endUser.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        endUser.setEmail(userInfo.getEmail());
        endUser.setEnabled(userInfo.getEnabled());
        Set<EndUserRole> endUserRoles = new HashSet<>();
        for(String endUserRole : userInfo.getRoles()){
            EndUserRole userRole = new EndUserRole();
            userRole.setRole(endUserRole);
            userRole.setUsername(userInfo.getUsername());
            userRole.setEndUser(endUser);
            endUserRoles.add(userRole);
        }
        endUser.setRoles(endUserRoles);
        this.endUserRepository.save(endUser);
        return new UserInfo(userInfo.getUsername());
    }
}
