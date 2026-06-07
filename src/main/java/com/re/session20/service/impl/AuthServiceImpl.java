package com.re.session20.service.impl;

import com.re.session20.model.dto.request.AuthRequest;
import com.re.session20.model.dto.response.TokenResponse;
import com.re.session20.model.entity.AppUser;
import com.re.session20.model.entity.UserToken;
import com.re.session20.repository.AppUserRepository;
import com.re.session20.repository.UserTokenRepository;
import com.re.session20.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserRepository userRepository;
    private final UserTokenRepository tokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TokenResponse login(AuthRequest request) {
        AppUser user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Tên đăng nhập hoặc mật khẩu không đúng"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Tên đăng nhập hoặc mật khẩu không đúng");
        }

        String accessToken = jwtProvider.generateToken(user.getUsername());
        String refreshToken = jwtProvider.generateRefreshToken(user.getUsername());

        UserToken userToken = UserToken.builder()
                .refreshToken(refreshToken)
                .isRevoked(false)
                .isExpired(false)
                .user(user)
                .build();
        tokenRepository.save(userToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public TokenResponse refresh(String refreshToken) {
        UserToken tokenEntity = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token không tồn tại"));

        if (tokenEntity.isRevoked() || tokenEntity.isExpired()) {
            throw new RuntimeException("Refresh token đã bị hủy bỏ hoặc hết hạn");
        }

        String username = jwtProvider.extractUsername(refreshToken);
        String newAccessToken = jwtProvider.generateToken(username);

        return TokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void logout(Long userId) {
        List<UserToken> activeTokens = tokenRepository.findAllByUserIdAndIsRevokedFalse(userId);

        activeTokens.stream().forEach(token -> token.setRevoked(true));
        tokenRepository.saveAll(activeTokens);
    }
}