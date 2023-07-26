package com.abhishek.sampleresourceserver.controller;

import com.abhishek.sampleresourceserver.client.UserInfo;
import com.abhishek.sampleresourceserver.service.EndUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enduser")
public class EndUserController {
    private final EndUserService endUserService;
    public EndUserController(EndUserService endUserService) {
        this.endUserService = endUserService;
    }

    @GetMapping("/details")
    public UserInfo details(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal){
        return this.endUserService.details(principal);
    }

    @PostMapping("/register")
    public UserInfo register(@RequestBody UserInfo userInfo){
        return this.endUserService.register(userInfo);
    }
}
