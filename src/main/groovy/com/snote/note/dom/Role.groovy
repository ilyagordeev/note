package com.snote.note.dom

import org.springframework.security.core.GrantedAuthority

enum Role implements GrantedAuthority {
    USER

    @Override
    String getAuthority() {
        return name()
    }
}