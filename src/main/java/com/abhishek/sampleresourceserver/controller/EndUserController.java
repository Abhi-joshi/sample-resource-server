package com.abhishek.sampleresourceserver.controller;

import com.abhishek.sampleresourceserver.client.UserInfo;
import com.abhishek.sampleresourceserver.service.EndUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enduser")
public class EndUserController {
    private final EndUserService endUserService;
    public EndUserController(EndUserService endUserService) {
        this.endUserService = endUserService;
    }

    @PostMapping("/register")
    public UserInfo register(@RequestBody UserInfo userInfo){
        return this.endUserService.register(userInfo);
    }
}
