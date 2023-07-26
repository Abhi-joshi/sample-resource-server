package com.abhishek.sampleresourceserver.service;

import com.abhishek.sampleresourceserver.client.UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

public interface EndUserService {
    UserInfo register(UserInfo userInfo);

    UserInfo details(OAuth2AuthenticatedPrincipal principal);
}
