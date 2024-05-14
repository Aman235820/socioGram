package com.aman.socialMedia.Security;

import com.aman.socialMedia.Entities.User;
import com.aman.socialMedia.Exceptions.ResourceNotFoundException;
import com.aman.socialMedia.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //load user from Database
        User user = this.userRepo.findByEmail(username).orElseThrow(()-> new RuntimeException("User not Found !!"));

        return user;
    }
}
