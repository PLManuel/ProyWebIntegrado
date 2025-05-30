package com.OrderNet.ProyWebIntegrado.persistence.model.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ADMINISTRATOR(Set.of(
            Permissions.CREATE_USER,
            Permissions.EDIT_USER,
            Permissions.DELETE_USER,
            Permissions.READ_USER,
            Permissions.CREATE_PRODUCT,
            Permissions.EDIT_PRODUCT,
            Permissions.DELETE_PRODUCT,
            Permissions.VIEW_PRODUCT,
            Permissions.VIEW_ORDER,
            Permissions.EDIT_ORDER,
            Permissions.EDIT_WAITER)),

    WAITER(Set.of(
            Permissions.CREATE_ORDER,
            Permissions.VIEW_ORDER,
            Permissions.EDIT_ORDER));

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
