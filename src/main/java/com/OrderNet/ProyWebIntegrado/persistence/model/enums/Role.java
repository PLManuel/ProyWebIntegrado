package com.OrderNet.ProyWebIntegrado.persistence.model.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMINISTRATOR(Set.of(
            Permissions.CREATE_USER,
            Permissions.READ_USER,
            Permissions.UPDATE_USER,
            Permissions.DELETE_USER,

            Permissions.CREATE_RESTAURANT_TABLE,
            Permissions.READ_RESTAURANT_TABLE,
            Permissions.UPDATE_RESTAURANT_TABLE,
            Permissions.DELETE_RESTAURANT_TABLE,

            Permissions.CREATE_CATEGORY,
            Permissions.READ_CATEGORY,
            Permissions.UPDATE_CATEGORY,
            Permissions.DELETE_CATEGORY,

            Permissions.CREATE_PRODUCT,
            Permissions.READ_PRODUCT,
            Permissions.UPDATE_PRODUCT,
            Permissions.DELETE_PRODUCT,

            Permissions.CREATE_ORDER,
            Permissions.READ_ORDER,
            Permissions.UPDATE_ORDER,
            Permissions.DELETE_ORDER)),

    WAITER(Set.of(
            Permissions.CREATE_ORDER,
            Permissions.READ_ORDER,
            Permissions.UPDATE_ORDER,
            Permissions.DELETE_ORDER));

    private final Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        authorities.addAll(permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.name()))
                .collect(Collectors.toList()));
        return authorities;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
