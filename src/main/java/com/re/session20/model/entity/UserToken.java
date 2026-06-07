package com.re.session20.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cột này dùng để đối soát chuỗi Token được gửi lên ở Filter xem đã bị Revoke chưa
    @Column(name = "refresh_token", length = 512, nullable = false)
    private String refreshToken;

    @Column(name = "is_revoked")
    private boolean isRevoked;

    @Column(name = "is_expired")
    private boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;
}