package com.aman.socialMedia.Services.impl;

import com.aman.socialMedia.Entities.Posts;
import com.aman.socialMedia.Entities.User;
import com.aman.socialMedia.Exceptions.ResourceNotFoundException;
import com.aman.socialMedia.Models.PaginationResponseDTO;
import com.aman.socialMedia.Models.PostsDTO;
import com.aman.socialMedia.Repositories.PostsRepo;
import com.aman.socialMedia.Repositories.UserRepo;
import com.aman.socialMedia.Services.PostsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostsServiceImpl implements PostsService {

    @Autowired
    private PostsRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Value("${project.image}")
    private String path;

    @Override
    public PostsDTO createPost(PostsDTO post, Integer userId , MultipartFile imageFile) throws IOException {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Posts posts = this.modelMapper.map(post, Posts.class);
        if (post.getImage() == null && imageFile == null) {
            posts.setImage("default.png");
        }
        else if(imageFile != null){

                 //fileName
                 String name = imageFile.getOriginalFilename();

                 //unique fileName
                 String randomID = UUID.randomUUID().toString();
                 String fileName = randomID.concat(name.substring(name.lastIndexOf('.')));

                 //fullPath
                 String filePath = path + File.separator + fileName;

                 File f = new File(path);
                 if(!f.exists()){
                     f.mkdir();
                 }

                 //copy file into the above folder
                 Files.copy(imageFile.getInputStream() , Paths.get(filePath));

                 posts.setImage(fileName);
        }
        posts.setUser(user);
        posts.setPostDate(new Date());
        Posts createdPost = this.postRepo.save(posts);

        return modelMapper.map(createdPost, PostsDTO.class);
    }

    @Override
    public PostsDTO updatePost(PostsDTO post, Integer postId , MultipartFile imageFile) throws IOException {

        Posts foundPost = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));

        foundPost.setContent(post.getContent());
        if (post.getImage() != null) {
            foundPost.setImage(post.getImage());
        }
        if (imageFile != null) {

             String name = imageFile.getOriginalFilename();


             String randomId = UUID.randomUUID().toString();
             String fileName = randomId.concat(name.substring(name.lastIndexOf('.')));

             String pathName = path + File.separator + fileName;

             File f = new File(path);
             if(!f.exists()){
                  f.mkdir();
             }

             Files.copy(imageFile.getInputStream() , Paths.get(pathName));
             foundPost.setImage(fileName);
        }

        Posts updatedPost = this.postRepo.save(foundPost);

        return this.modelMapper.map(updatedPost, PostsDTO.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Posts post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        this.postRepo.delete(post);

    }

    @Override
    //@Cacheable(key = "'allPosts'" , value = "RedisData")
    public PaginationResponseDTO getAllPosts(Integer pageNumber, Integer pageSize , String sortBy , String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable p = PageRequest.of(pageNumber, pageSize , sort);

        Page<Posts> pagePosts = this.postRepo.findAll(p);

        List<Posts> allPosts = pagePosts.getContent();

        List<PostsDTO> postDto = allPosts.stream().map(item -> this.modelMapper.map(item, PostsDTO.class)).collect(Collectors.toList());

        PaginationResponseDTO pagination = new PaginationResponseDTO(postDto, pagePosts.getNumber(), pagePosts.getSize(),
                pagePosts.getTotalElements(), pagePosts.getTotalPages(), pagePosts.isLast());

        return pagination;

    }

    @Override
    public PostsDTO getPostbyId(Integer postId) {

        Posts post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        return this.modelMapper.map(post, PostsDTO.class);
    }

    @Override
    public PaginationResponseDTO getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        Pageable p = PageRequest.of(pageNumber, pageSize);

        Page<Posts> postPage = this.postRepo.findByUser(user, p);

        List<PostsDTO> postDto = postPage.stream().map(item -> this.modelMapper.map(item, PostsDTO.class)).collect(Collectors.toList());

        PaginationResponseDTO pagination = new PaginationResponseDTO(postDto , postPage.getNumber() , postPage.getSize()
        , postPage.getTotalElements() , postPage.getTotalPages() , postPage.isLast());

        return pagination;

    }

    @Override
    public List<PostsDTO> searchPosts(String keyword) {
             List<Posts> searchedPost = this.postRepo.searchByContent("%" + keyword +"%");
             return searchedPost.stream().map(item-> this.modelMapper.map(item , PostsDTO.class)).collect(Collectors.toList());
    }
}
