package com.aman.socialMedia.Exceptions;

public class InvalidAccessException extends RuntimeException{
       int pId , uId;
        public InvalidAccessException(Integer postId , Integer userId){

               super(String.format("The comment on the post: %s you are trying to access is in-accessible to the User: %s !!" , postId , userId));
               this.pId = postId;
               this.uId = userId;
        }

}
