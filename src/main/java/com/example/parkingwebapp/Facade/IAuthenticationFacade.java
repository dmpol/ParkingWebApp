package com.example.parkingwebapp.Facade;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
