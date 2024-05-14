package com.aman.socialMedia.Models;

import com.aman.socialMedia.Entities.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostsDTO {

       private Integer postId;

       @NotEmpty
       private String content;

       private String image;

       private Date postDate;

       private UserDTOs user;

       private List<CommentsDTO> comments;


}
