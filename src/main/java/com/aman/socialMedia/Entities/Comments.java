package com.aman.socialMedia.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comments {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Integer commentId;

       private String content;

       @ManyToOne
       @JoinColumn(name = "postId")
       private Posts post;

       @ManyToOne
       @JoinColumn(name = "userId")
       private User user;

}
