package com.aman.socialMedia.Models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentsDTO {

       private Integer commentId;

       @NotEmpty
       private String content;

}
