package dev.shafin.jwtdemo.controller;

import dev.shafin.jwtdemo.service.TokenService;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.slf4j.LoggerFactory.*;

@RestController
public class AuthController {
    private static final Logger LOGGER = getLogger(AuthController.class);
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        LOGGER.debug("Generating token for {}", authentication.getName());
        String token = tokenService.generateToken(authentication);
        LOGGER.debug("Token generated for {} token: {}", authentication.getName(), token);
        return token;
    }
}
