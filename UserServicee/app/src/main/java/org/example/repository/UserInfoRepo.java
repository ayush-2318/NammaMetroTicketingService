package org.example.repository;

import org.apache.catalina.User;
import org.example.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserInfoRepo extends CrudRepository<UserInfo,String> {
    Optional<UserInfo> findByUserId(String userId);
}
