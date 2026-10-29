package com.example.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.bookstore.model.AppUser;
import com.example.bookstore.model.AppUserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class DatabaseUserDetailsServiceTest {
    @Mock
    private AppUserRepository appUserRepository;

    @Test
    void loadsDatabaseUserAndMapsRoleToAuthority() {
        when(appUserRepository.findByUsername("reader"))
                .thenReturn(Optional.of(new AppUser("reader", "$2a$hash", "reader@example.com", "USER")));

        UserDetails user = new DatabaseUserDetailsService(appUserRepository).loadUserByUsername("reader");

        assertEquals("reader", user.getUsername());
        assertEquals("$2a$hash", user.getPassword());
        assertEquals("ROLE_USER", user.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void throwsWhenUsernameDoesNotExist() {
        when(appUserRepository.findByUsername("missing")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> new DatabaseUserDetailsService(appUserRepository).loadUserByUsername("missing"));
    }
}
