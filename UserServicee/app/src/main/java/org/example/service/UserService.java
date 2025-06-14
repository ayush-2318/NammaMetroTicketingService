package org.example.service;

import org.example.entities.UserInfo;
import org.example.model.UserInfoDto;
import org.example.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class UserService {
    @Autowired
    private final UserInfoRepo userInfoRepo;

    public UserService(UserInfoRepo userInfoRepo) {
        this.userInfoRepo = userInfoRepo;
    }

    public UserInfoDto createOrUpdate(UserInfoDto userInfoDto){
        Function<UserInfo,UserInfo> updatingUser=user->{
            return userInfoRepo.save(user);
        };
        Supplier<UserInfo> createUser=()->{
            return userInfoRepo.save(userInfoDto.transformToUserInfo());
        };

        UserInfo userInfo=userInfoRepo.findByUserId(userInfoDto.getUserId())
                .map(updatingUser)
                .orElseGet(createUser);

        return UserInfoDto.builder()
                .userId(userInfoDto.getUserId())
                .userName(userInfoDto.getUserName())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .email(userInfo.getEmail())
                .phoneNumber(userInfoDto.getPhoneNumber())
                .profilePicture(userInfoDto.getProfilePicture()).build();
    }

    public UserInfoDto getUser(String userId) throws Exception{
        Optional<UserInfo> userInfoOptional=userInfoRepo.findByUserId(userId);
        if(userInfoOptional.isEmpty()){
            throw new Exception("User Not Found");
        }
        UserInfo userInfo=userInfoOptional.get();
        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .userName(userInfo.getUserName())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .email(userInfo.getEmail())
                .phoneNumber(userInfo.getPhoneNumber())
                .profilePicture(userInfo.getProfilePicture()).build();
    }
}
