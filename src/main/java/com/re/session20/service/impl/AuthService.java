package com.re.session20.service.impl;


import com.re.session20.model.dto.request.AuthRequest;
import com.re.session20.model.dto.response.TokenResponse;

public interface AuthService {
    TokenResponse login(AuthRequest request);
    TokenResponse refresh(String refreshToken);
    void logout(Long userId);
}