package com.sec.sso.controller;

import com.sec.sso.model.User;
import com.sec.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;

@RestController()
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public Principal user(Principal principal) {
        System.out.println(principal);
        return principal;
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            String mainPage = getMainPage(request.getHeader("referer"));
            response.sendRedirect(mainPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getMainPage(String referer) throws MalformedURLException {
        URL url = new URL(referer);
        String protocol = url.getProtocol();
        String authority = url.getAuthority();
        return String.format("%s://%s", protocol, authority);
    }

    @PostMapping()
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}