package com.aman.socialMedia.Controllers;

import com.aman.socialMedia.EnumClass;
import com.aman.socialMedia.Models.PaginationResponseDTO;
import com.aman.socialMedia.Models.PostsDTO;
import com.aman.socialMedia.Models.ResponseDTO;
import com.aman.socialMedia.Services.PostsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/socialMedia/posts")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @Autowired
    private ObjectMapper mapper;

    @PostMapping(value = "/user/{userId}/createPost")
    public ResponseEntity<ResponseDTO> createPost(@RequestParam(value = "post") String post, @PathVariable Integer userId,
                                                  @RequestParam(value = "image" , required = false) MultipartFile imageFile) {

        PostsDTO convertedPost = null;
        try {
            //converting string into json object
            convertedPost = mapper.readValue(post , PostsDTO.class);

            PostsDTO postDto = this.postsService.createPost(convertedPost, userId , imageFile);
            return new ResponseEntity<>(new ResponseDTO(postDto, "Post added succesfully", false), HttpStatus.CREATED);

        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}/getPostsByUser")
    public ResponseEntity<ResponseDTO> getPostsByUser(@PathVariable(name = "userId") Integer uid,
                                                      @RequestParam(name = "pageNumber" ,defaultValue = EnumClass.PAGE_NUMBER, required = false) Integer pageNumber,
                                                      @RequestParam(name = "pageSize" , defaultValue = EnumClass.PAGE_SIZE , required = false) Integer pageSize) {

        try {
            PaginationResponseDTO postsByUser = this.postsService.getPostsByUser(uid , pageNumber , pageSize);
            return new ResponseEntity<>(new ResponseDTO(postsByUser, "Posts by user : " + uid + ", fetched successfully", false), HttpStatus.OK);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/removePost/{postId}")
    public ResponseEntity<ResponseDTO> removePost(@PathVariable Integer postId) {

        try {
            this.postsService.deletePost(postId);
            return new ResponseEntity<>(new ResponseDTO(null, "Post : " + postId + " ,Deleted successfully", false), HttpStatus.OK);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getPostbyId/{pId}")
    public ResponseEntity<ResponseDTO> getPostbyId(@PathVariable(name = "pId") Integer postId) {
        try {
            PostsDTO post = this.postsService.getPostbyId(postId);
            return new ResponseEntity<>(new ResponseDTO(post, "Post : " + postId + " ,fetched successfully", false), HttpStatus.OK);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<ResponseDTO> updatePost(@PathVariable Integer postId,
                                                  @RequestParam(value = "updatedPost") String post,
    @RequestParam(value = "image" , required = false) MultipartFile imageFile) {

        PostsDTO postObj = null;

        try {
            postObj = this.mapper.readValue(post , PostsDTO.class);
            PostsDTO updatedPost = this.postsService.updatePost(postObj, postId , imageFile);
            return new ResponseEntity<>(new ResponseDTO(updatedPost, "Post : " + postId + " ,updated successfully", false), HttpStatus.OK);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<ResponseDTO> getAllPosts(@RequestParam(name = "pageNumber" , defaultValue = EnumClass.PAGE_NUMBER, required = false) Integer pageNumber,
                                                   @RequestParam(name = "pageSize" , defaultValue = EnumClass.PAGE_SIZE , required = false) Integer pageSize,
    @RequestParam(name = "sortBy" , defaultValue = EnumClass.SORT_BY , required = false) String sortBy,
                                                   @RequestParam(name = "sortOrder" , defaultValue = EnumClass.SORT_ORDER , required = false) String sortOrder) {

        try {
            PaginationResponseDTO allPosts = this.postsService.getAllPosts(pageNumber , pageSize , sortBy , sortOrder);
            return new ResponseEntity<>(new ResponseDTO(allPosts, "Posts fetched successfully", false), HttpStatus.OK);
        } catch (Exception ce) {
            return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("searchPosts/{content}")
    public ResponseEntity<ResponseDTO> searchByContent(@PathVariable String content){
          try{
              return new ResponseEntity<>(new ResponseDTO(this.postsService.searchPosts(content) , "Search results fetched successfully" , false) , HttpStatus.ACCEPTED);
          }
          catch(Exception ce){
              return new ResponseEntity<>(new ResponseDTO(null , ce.getMessage() , true) , HttpStatus.BAD_REQUEST);
          }
    }






}
