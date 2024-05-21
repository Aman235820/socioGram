package com.aman.socialMedia.Controllers;

import com.aman.socialMedia.Models.ResponseDTO;
import com.aman.socialMedia.Models.UserDTOs;
import com.aman.socialMedia.Security.JwtHelper;
import com.aman.socialMedia.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("socialMedia/user")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("updateUser/{userId}")
    public ResponseEntity<ResponseDTO> updateUser(@Valid @RequestBody UserDTOs user, @PathVariable("userId") Integer uid) {
        ResponseDTO response;
        try {
            UserDTOs updatedUser = this.userService.updateUser(user, uid);
            response = new ResponseDTO(updatedUser, "Successfully Updated", false);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //For Admin only
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Integer userId) {

        try {
            this.userService.deleteUser(userId);
            return new ResponseEntity<>(new ResponseDTO(null, "Successfully Deleted", false), HttpStatus.OK);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAllUsers")
    public ResponseEntity<ResponseDTO> getAllUsers() {

        try {
            List<UserDTOs> userList = new ArrayList<>();
            userList = this.userService.getAllUsers();
            return new ResponseEntity<>(new ResponseDTO(userList, "Successfully Fetched", false), HttpStatus.OK);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable("id") Integer userId){

        try{
             UserDTOs user = this.userService.getUserById(userId);
             return new ResponseEntity<>(new ResponseDTO(user , "Succesfully fetched !" , false) , HttpStatus.OK);
        }
        catch(Exception ce){
            return new ResponseEntity<>(new ResponseDTO(null , ce.getMessage() , true) , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
