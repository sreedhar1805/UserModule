package com.org.userdetails.model;

import java.util.Set;

public enum Erole {
    ROLE_ADMIN(Set.of(Permission.READ, Permission.WRITE, Permission.ADMINISTER)),
    ROLE_USER(Set.of(Permission.READ));

    private final Set<Permission> permissions;

    Erole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
