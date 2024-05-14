package com.aman.socialMedia.Controllers;

import com.aman.socialMedia.Models.CommentsDTO;
import com.aman.socialMedia.Models.ResponseDTO;
import com.aman.socialMedia.Services.CommentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socialMedia/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentService;

    @PostMapping(value = "/writeComment/userId/{uId}/postId/{pId}")
    public ResponseEntity<ResponseDTO> writeComment(@Valid @RequestBody CommentsDTO comment,
                                                    @PathVariable(name = "uId") Integer userId,
                                                    @PathVariable(name = "pId") Integer postId) {


        try {
            CommentsDTO postedComment = this.commentService.writeComment(comment, userId, postId);
            return new ResponseEntity<>(new ResponseDTO(postedComment, "Comment posted successfully", false), HttpStatus.CREATED);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "deleteComment/comment/{commentId}")
    public ResponseEntity<ResponseDTO> deleteComment(@RequestParam(name = "postId") Integer postId ,
                                                     @RequestParam(name = "userId") Integer userId,
                                                     @PathVariable(name = "commentId") Integer cId){

            try{
                  this.commentService.deleteComment(cId , postId , userId );
                  return new ResponseEntity<>(new ResponseDTO(null , "Comment deleted successfully !!" , false) , HttpStatus.OK);
            }
            catch(Exception ce){
                return new ResponseEntity<>(new ResponseDTO(null , ce.getMessage() , true) , HttpStatus.BAD_REQUEST);
            }
    }
}
