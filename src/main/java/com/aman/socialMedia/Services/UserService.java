package com.aman.socialMedia.Services;

import com.aman.socialMedia.Models.UserDTOs;
import com.aman.socialMedia.Security.JwtAuthRequest;

import  java.util.List;

public interface UserService {

          UserDTOs createUser(UserDTOs user);
          UserDTOs updateUser(UserDTOs user , Integer userId);

          UserDTOs getUserById(Integer userId);

          List<UserDTOs> getAllUsers();

          void deleteUser(Integer userId);

          Integer getUserId(String username);

          boolean resetPassword(JwtAuthRequest request);
}
