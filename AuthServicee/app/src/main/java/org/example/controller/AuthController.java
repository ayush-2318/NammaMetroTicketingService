package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.entities.RefreshToken;
import org.example.model.UserInfoDto;
import org.example.response.JWTResponseDto;
import org.example.service.JWTService;
import org.example.service.RefreshTokenService;
import org.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("auth/v1")
public class AuthController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity Signup(@RequestBody UserInfoDto userInfoDto) {
        try {
            //System.out.println("Received userName: " + userInfoDto.getFirstName());
            String userId = userDetailsServiceImpl.signUpUser(userInfoDto);
            if (Objects.isNull(userId)) {
                return new ResponseEntity<>("User already exist", HttpStatus.BAD_REQUEST);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDto.getUserName());
            System.out.println(refreshToken);
            String jwtToken = jwtService.generateToken(userInfoDto.getUserName());
            System.out.println(jwtToken);
            return new ResponseEntity<>(JWTResponseDto.builder().accessToken(jwtToken)
                    .token(refreshToken.getToken()).userId(userId).build(), HttpStatus.OK);

        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String stackTrace = sw.toString();

            return new ResponseEntity<>(stackTrace, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null&&authentication.isAuthenticated()){
            String userId=userDetailsServiceImpl.getUserByUserName(authentication.getName());
            if(Objects.nonNull(userId)){
                return ResponseEntity.ok(userId);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorised");
    }
}
