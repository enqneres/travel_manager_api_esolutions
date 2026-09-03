package com.esolutions.travel.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
class TestUserInitializer implements CommandLineRunner {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    TestUserInitializer(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (!users.existsByEmail("test@example.com")) {
            users.save(new AppUser("test@example.com", encoder.encode("test-password")));
        }
    }
}
