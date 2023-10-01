package com.heretic.dmoney.services.impl;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CheckGenerationPasswordImpl {

    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public boolean checkPassword(String inputPassword, String password) {
        return encoder.matches(inputPassword, password);
    }

    public String getHashPassword(String password) {
        return encoder.encode(password);
    }
}