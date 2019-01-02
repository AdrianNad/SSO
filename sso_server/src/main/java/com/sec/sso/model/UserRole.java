package com.sec.sso.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class UserRole implements GrantedAuthority {

    private String role;

    @Override
    public String getAuthority() {
        return role.toUpperCase() + "_ROLE";
    }
}
