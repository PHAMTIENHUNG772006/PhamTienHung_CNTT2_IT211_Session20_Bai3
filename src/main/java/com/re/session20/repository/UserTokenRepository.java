package com.re.session20.repository;


import com.re.session20.model.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    Optional<UserToken> findByRefreshToken(String token);
    List<UserToken> findAllByUserIdAndIsRevokedFalse(Long userId);
}
