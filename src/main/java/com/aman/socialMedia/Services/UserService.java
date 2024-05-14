package com.aman.socialMedia.Services;

import com.aman.socialMedia.Models.UserDTOs;

import  java.util.List;

public interface UserService {

          UserDTOs createUser(UserDTOs user);
          UserDTOs updateUser(UserDTOs user , Integer userId);

          UserDTOs getUserById(Integer userId);

          List<UserDTOs> getAllUsers();

          void deleteUser(Integer userId);


}
