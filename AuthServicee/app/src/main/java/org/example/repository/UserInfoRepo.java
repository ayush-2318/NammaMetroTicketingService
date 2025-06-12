package org.example.repository;

import org.example.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.User;

public interface UserInfoRepo extends CrudRepository<UserInfo,Long> {
    public UserInfo findByUserName(String userName);
}
