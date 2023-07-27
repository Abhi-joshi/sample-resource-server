package com.abhishek.sampleresourceserver.service.impl;

import com.abhishek.sampleresourceserver.client.UserInfo;
import com.abhishek.sampleresourceserver.modal.EndUser;
import com.abhishek.sampleresourceserver.modal.EndUserRole;
import com.abhishek.sampleresourceserver.repository.EndUserRepository;
import com.abhishek.sampleresourceserver.repository.EndUserRoleRepository;
import com.abhishek.sampleresourceserver.service.EndUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EndUserServiceImpl implements EndUserService {

    private final EndUserRepository endUserRepository;
    private final EndUserRoleRepository endUserRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public EndUserServiceImpl(EndUserRepository endUserRepository, EndUserRoleRepository endUserRoleRepository, PasswordEncoder passwordEncoder) {
        this.endUserRepository = endUserRepository;
        this.endUserRoleRepository = endUserRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Mono<UserInfo> details(OAuth2AuthenticatedPrincipal principal) {
        return this.endUserRepository.findEndUserByUsername(principal.getName())
                .flatMap(endUser -> {
                    Flux<EndUserRole> endUserRoles = this.endUserRoleRepository.findEndUserRoleByEndUserId(endUser.getEndUserId());
                    return endUserRoles.collectList()
                            .map(roles -> {
                                UserInfo userInfo = new UserInfo();
                                userInfo.setUsername(endUser.getUsername());
                                userInfo.setEmail(endUser.getEmail());
                                userInfo.setEnabled(endUser.getEnabled());
                                Set<String> roleSet = roles.stream()
                                        .map(EndUserRole::getRole)
                                        .collect(Collectors.toSet());
                                userInfo.setRoles(roleSet);
                                return userInfo;
                            });
                });
    }

    @Override
    public Mono<UserInfo> register(UserInfo userInfo) {
        EndUser endUser = new EndUser();
        endUser.setUsername(userInfo.getUsername());
        endUser.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        endUser.setEmail(userInfo.getEmail());
        endUser.setEnabled(false);

        Mono<EndUser> savedEndUser = this.endUserRepository.save(endUser);

        Flux<EndUserRole> savedEndUserRoles = Flux.fromIterable(userInfo.getRoles())
                .flatMap(role -> {
                    EndUserRole userRole = new EndUserRole();
                    userRole.setRole(role);
                    userRole.setUsername(userInfo.getUsername());

                    return savedEndUser.map(savedUser -> {
                        userRole.setEndUserId(savedUser.getEndUserId());
                        return userRole;
                    });
                })
                .collectList()
                .flatMapMany(this.endUserRoleRepository::saveAll);

        return savedEndUserRoles.then(Mono.just(new UserInfo(userInfo.getUsername())));
    }
}
