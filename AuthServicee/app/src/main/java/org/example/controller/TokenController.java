package org.example.controller;

import org.example.entities.RefreshToken;
import org.example.request.AuthRequest;
import org.example.request.RefreshTokenRequest;
import org.example.response.JWTResponseDto;
import org.example.service.JWTService;
import org.example.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/auth/v1")
public class TokenController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity AuthenticateAndGetToeken(@RequestBody AuthRequest authRequest){
        Authentication authentication=authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken=refreshTokenService.createRefreshToken(authRequest.getUserName());
            return  new ResponseEntity<>(JWTResponseDto.builder()
                    .accessToken(jwtService.generateToken(authRequest.getUserName()))
                    .token(refreshToken.getToken())
                    .build(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Exception in User service", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/refreshToken")
    public JWTResponseDto refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return refreshTokenService.findByRefreshToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String acessToken=jwtService.generateToken(userInfo.getUserName());
                    return JWTResponseDto.builder()
                            .accessToken(acessToken)
                            .token(refreshTokenRequest.getToken()).build();
                }).orElseThrow(()->new RuntimeException("Refresh Token Not in Db"));
    }
}
