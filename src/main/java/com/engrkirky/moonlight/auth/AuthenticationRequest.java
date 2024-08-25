package com.engrkirky.moonlight.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
