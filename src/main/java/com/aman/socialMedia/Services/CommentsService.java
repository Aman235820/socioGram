package com.aman.socialMedia.Services;

import com.aman.socialMedia.Models.CommentsDTO;

public interface CommentsService {

       CommentsDTO writeComment(CommentsDTO comment , Integer userId , Integer postId);

       void deleteComment(Integer commentId , Integer postId , Integer userId);

}
