package com.abhishek.sampleresourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @GetMapping("/messages")
    public String[] message() {
        return new String[] {"Message 1", "Message 2", "Message 3"};
    }
}
