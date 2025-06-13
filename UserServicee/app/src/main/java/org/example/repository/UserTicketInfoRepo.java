package org.example.repository;

import org.example.entities.UserTicketInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserTicketInfoRepo extends CrudRepository<UserTicketInfo,String> {
    List<UserTicketInfo> findByUserId(String userId);
}
