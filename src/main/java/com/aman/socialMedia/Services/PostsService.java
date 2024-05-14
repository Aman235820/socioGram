package com.aman.socialMedia.Services;


import com.aman.socialMedia.Models.PaginationResponseDTO;
import com.aman.socialMedia.Models.PostsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostsService {

    PostsDTO createPost(PostsDTO post , Integer userId , MultipartFile imageFile) throws IOException;

    PostsDTO updatePost(PostsDTO post, Integer postId , MultipartFile imageFile) throws IOException;

    void deletePost(Integer postId);

    PostsDTO getPostbyId(Integer postId);

    PaginationResponseDTO getPostsByUser(Integer userId, Integer pageNumber , Integer pageSize);


    List<PostsDTO> searchPosts(String keyword);

    PaginationResponseDTO getAllPosts(Integer pageNumber, Integer pageSize , String sortBy , String sortOrder);
}
