package com.vueboard.domains.auth.service;

import org.springframework.stereotype.Service;

import com.vueboard.domains.auth.entity.User;

@Service
public interface JoinService {

    String checkEmailDuplicate(String email);

    void addUser(User user);

}