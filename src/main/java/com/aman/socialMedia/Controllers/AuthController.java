package com.aman.socialMedia.Controllers;

import com.aman.socialMedia.Models.ResponseDTO;
import com.aman.socialMedia.Models.UserDTOs;
import com.aman.socialMedia.Security.JwtAuthRequest;
import com.aman.socialMedia.Security.JwtAuthResponse;
import com.aman.socialMedia.Security.JwtHelper;
import com.aman.socialMedia.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("socialMedia/auth")
public class AuthController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody JwtAuthRequest request) {

        try {
            this.authenticate(request.getUsername(), request.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtHelper.generateToken(userDetails);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(token);
            response.setUserName(request.getUsername());
            response.setId(this.userService.getUserId(request.getUsername()));
            response.setRole(userDetails.getAuthorities());

            return new ResponseEntity<>(new ResponseDTO(response, "Token successfully generated !!", false), HttpStatus.CREATED);

        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> createUser(@Valid @RequestBody UserDTOs request) {
        try {
            UserDTOs createdUser = this.userService.createUser(request);
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = this.jwtHelper.generateToken(userDetails);

            JwtAuthResponse res = new JwtAuthResponse();
            res.setId(this.userService.getUserId(userDetails.getUsername()));
            res.setUserName(request.getEmail());
            res.setToken(token);
            res.setRole(userDetails.getAuthorities());

            return new ResponseEntity<>(new ResponseDTO(res, "Success", false), HttpStatus.CREATED);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    private void authenticate(String username, String password) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }


}
