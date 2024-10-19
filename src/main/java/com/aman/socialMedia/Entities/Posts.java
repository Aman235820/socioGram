package com.aman.socialMedia.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "posts")
public class Posts {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Integer postId;

      @Column(name = "post_content" , length = 10000 , nullable = false)
      private String content;

      @Column(name = "image_url")
      private String image;

      private Date postDate;

      @ManyToOne                                     //here we are creating a join btw User table and post table , userId will be our foreign key
      @JoinColumn(name= "userId")
      private User user;

      @OneToMany(mappedBy = "post" , cascade = CascadeType.ALL)
      private List<Comments> comments = new ArrayList<>();


}
