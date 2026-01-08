package com.vueboard.domains.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.vueboard.domains.auth.entity.User;


@Mapper
public interface JoinMapper {

    int checkEmailDuplicate(String email);
    int addUser(User user);

}