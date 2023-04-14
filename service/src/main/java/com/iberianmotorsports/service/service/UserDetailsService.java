package com.iberianmotorsports.service.service;

import com.iberianmotorsports.service.model.User;
import com.iberianmotorsports.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private UserRepository userRepository;


}
