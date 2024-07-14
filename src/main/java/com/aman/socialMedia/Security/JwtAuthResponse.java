package com.aman.socialMedia.Security;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
public class JwtAuthResponse {
     private String token;
     private String userName;
     private Integer id;
     private String password;
     private Integer age;
     private  String name;
     private Collection<? extends GrantedAuthority> role;

}
