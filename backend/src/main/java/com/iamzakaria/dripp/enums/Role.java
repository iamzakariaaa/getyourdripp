package com.iamzakaria.dripp.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_CUSTOMER, ROLE_ADMIN;
    public String getAuthority() {
        return name();
    }
}
