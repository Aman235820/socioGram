package com.aman.socialMedia.Services.impl;

import com.aman.socialMedia.Entities.User;
import com.aman.socialMedia.Exceptions.ResourceNotFoundException;
import com.aman.socialMedia.Models.UserDTOs;
import com.aman.socialMedia.Repositories.UserRepo;
import com.aman.socialMedia.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTOs createUser(UserDTOs userDto) {
        User user = this.DTOtoUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.UsertoDTO(savedUser);
    }

    @Override
    public UserDTOs updateUser(UserDTOs userDto, Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAge(userDto.getAge());

        User updatedUser = this.userRepo.save(user);

        return this.UsertoDTO(updatedUser);
    }

    @Override
    public UserDTOs getUserById(Integer userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.UsertoDTO(user);
    }

    @Override
    public List<UserDTOs> getAllUsers() {

        List<User> userList = new ArrayList<>();
        List<UserDTOs> userDtoList = new ArrayList<>();
        userList = this.userRepo.findAll();

//             for(User  u : userList){
//                  userDtoList.add(this.UsertoDTO(u));
//             }

        userDtoList = userList.stream().map(item -> this.UsertoDTO(item)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer userId) {
           User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "Id" , userId));
           this.userRepo.delete(user);
    }


    private User DTOtoUser(UserDTOs userDto) {

        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAge(userDto.getAge());

        return user;
    }


    //mapping the User object into UserDTO object using modalMapper (this is a substitute method of the above mentioned way , more reliable and less lines of code)
    private UserDTOs UsertoDTO(User user) {
        UserDTOs userDto = new UserDTOs();
        userDto =  this.modelMapper.map(user , UserDTOs.class);

//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAge(user.getAge());

        return userDto;

    }

    public Integer getUserId(String username){
          User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User" , "UserName : "+username , 0));
          return user.getId();
    }


}
