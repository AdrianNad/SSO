package com.sec.client2.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ScopeController {

    @GetMapping("/read")
    @PreAuthorize("#oauth2.hasScope('read')")
    public String canIRead() {
        return "you can read";
    }

    @GetMapping("/write")
    @PreAuthorize("#oauth2.hasScope('write')")
    public String canIWrite() {
        return "you can write";
    }
}
