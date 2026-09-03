package com.esolutions.travel.auth;

public record AuthResponse(String token, UserResponse user) {
    public record UserResponse(Long id, String email) {
        static UserResponse from(AppUser user) {
            return new UserResponse(user.getId(), user.getEmail());
        }
    }

    static AuthResponse of(String token, AppUser user) {
        return new AuthResponse(token, UserResponse.from(user));
    }
}
