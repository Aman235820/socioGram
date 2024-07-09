package com.aman.socialMedia.Services.impl;

import com.aman.socialMedia.Entities.Comments;
import com.aman.socialMedia.Entities.Posts;
import com.aman.socialMedia.Entities.User;
import com.aman.socialMedia.Exceptions.InvalidAccessException;
import com.aman.socialMedia.Exceptions.ResourceNotFoundException;
import com.aman.socialMedia.Models.CommentsDTO;
import com.aman.socialMedia.Models.PostsDTO;
import com.aman.socialMedia.Repositories.CommentsRepo;
import com.aman.socialMedia.Repositories.PostsRepo;
import com.aman.socialMedia.Repositories.UserRepo;
import com.aman.socialMedia.Services.CommentsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsRepo commentRepo;

    @Autowired
    private PostsRepo postRepo;

    @Autowired
    private UserRepo  userRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentsDTO writeComment(CommentsDTO comment, Integer userId, Integer postId) {

        Posts p = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post" , "Id" , postId));
        User r = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" , "Id" , userId));

        Comments comments = this.mapper.map(comment , Comments.class);

        comments.setPost(p);
        comments.setUser(r);

        Comments savedComment = this.commentRepo.save(comments);

        return this.mapper.map(savedComment , CommentsDTO.class);

    }

    @Override
    public void deleteComment(Integer commentId, Integer postId, Integer userId) {

        Comments c = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment" , "Id" , commentId));

        User u = c.getUser();
        Integer uId = u.getId();
        Posts p = c.getPost();
        Integer pId = p.getPostId();

        Integer creatorId =  p.getUser().getId();

        if(( Objects.equals(creatorId, userId) ||  Objects.equals(uId, userId)) && Objects.equals(pId, postId)){
             this.commentRepo.delete(c);
        }else{
            throw new InvalidAccessException(postId , userId);
        }
    }
}
