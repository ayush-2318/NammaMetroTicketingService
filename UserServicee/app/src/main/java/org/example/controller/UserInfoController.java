package org.example.controller;

import lombok.NonNull;
import org.example.model.UserInfoDto;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
public class UserInfoController {
    @Autowired
    private UserService userService;
    @PostMapping("/createupdate")
    public ResponseEntity<UserInfoDto> createUpdate(@RequestBody UserInfoDto userInfoDto){
        try {
            UserInfoDto user=userService.createOrUpdate(userInfoDto);
            return new  ResponseEntity<UserInfoDto>(user,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getuser")
    public ResponseEntity<UserInfoDto> getUser(@RequestHeader(value = "X-User-Id") @NonNull String userId){
        try {
            UserInfoDto user=userService.getUser(userId);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
