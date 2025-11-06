package com.springapps.familymanager.service;

import com.springapps.familymanager.entity.User;
import com.springapps.familymanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public User getByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public String addUser(User request) {
        User users = userRepo.findByEmail(request.getEmail());
        if(users==null) {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRole(request.getRole());
            userRepo.save(user);
            return "user registered";
        }
        return "user already exist";
    }
}
