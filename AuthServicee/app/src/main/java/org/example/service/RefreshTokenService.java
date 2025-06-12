package org.example.service;

import org.example.entities.RefreshToken;
import org.example.entities.UserInfo;
import org.example.repository.RefreshTokenRepo;
import org.example.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    public RefreshToken createRefreshToken(String userName){
        UserInfo userInfoExtracted=userInfoRepo.findByUserName(userName);
        RefreshToken refreshToken=RefreshToken.builder()
                .userInfo(userInfoExtracted)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
       return refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken){
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepo.delete(refreshToken);
            throw new RuntimeException("Refresh Token is Expired");
        }
        return refreshToken;
    }

    public Optional<RefreshToken> findByRefreshToken(String refreshToken){
        return refreshTokenRepo.findByToken(refreshToken);
    }

}
