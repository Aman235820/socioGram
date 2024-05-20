package com.aman.socialMedia.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTOs {

       private Integer id;

       @NotEmpty
       @Size( min = 3 , message = "Username must be more than 4 characters")
       @Pattern(regexp = "^[a-zA-Z\\\\s]+$" , message = "Username can have characters only!")
       private String name;

       @Email(message = "Please enter valid email")
       private String email;

       @NotEmpty   //this will check both null and blank values
       @Size( min = 2 , max = 10 , message = "Password must be between 2 to 10 characters in length")
       private String password;

       private int age;

}
