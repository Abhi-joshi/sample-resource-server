package com.abhishek.sampleresourceserver.service;

import com.abhishek.sampleresourceserver.client.UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import reactor.core.publisher.Mono;

public interface EndUserService {
    Mono<UserInfo> details(OAuth2AuthenticatedPrincipal principal);
    Mono<UserInfo> register(UserInfo userInfo);
}
