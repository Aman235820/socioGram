package com.aman.socialMedia.Security;


import lombok.Data;

@Data
public class JwtAuthResponse {
     private String token;
     private String userName;
     private Integer id;
}
