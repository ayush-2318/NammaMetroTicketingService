package org.example.service;

import org.example.entities.UserInfo;
import org.example.eventProducer.UserInfoProducer;
import org.example.model.UserInfoDto;
import org.example.model.UserInfoProducerDto;
import org.example.repository.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final   UserInfoRepo userInfoRepo;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;

    public UserDetailsServiceImpl(UserInfoRepo userInfoRepo, PasswordEncoder passwordEncoder, UserInfoProducer userInfoProducer) {
        this.userInfoRepo = userInfoRepo;
        this.passwordEncoder = passwordEncoder;
        this.userInfoProducer = userInfoProducer;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo=userInfoRepo.findByUserName(username);
        if(userInfo==null){
            throw  new UsernameNotFoundException("Could not find the user with given userName");
        }
        return new CustomUserDetails(userInfo);
    }

    public  String getUserByUserName(String userName){
        return Optional.of(userInfoRepo.findByUserName(userName)).map(UserInfo::getUserId).orElse(null);
    }

    public UserInfo checkIfUserAlreadyExists(String userName){
        return userInfoRepo.findByUserName(userName);
    }

    public String signUpUser(UserInfoDto userInfoDto){
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDto.getUserName()))){
            return null;
        }
        String id= UUID.randomUUID().toString();
        userInfoRepo.save(new UserInfo(id,userInfoDto.getUserName(),userInfoDto.getPassword(),new HashSet<>()));
        userInfoProducer.sendEventTokafka(new UserInfoProducerDto(userInfoDto.getFirstName(),userInfoDto.getLastName(),userInfoDto.getEmail(),userInfoDto.getPhoneNummer(),id,userInfoDto.getUserName()));
        return  id;
    }
}
