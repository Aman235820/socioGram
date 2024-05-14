package com.aman.socialMedia.Repositories;

import com.aman.socialMedia.Entities.Posts;
import com.aman.socialMedia.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepo extends JpaRepository<Posts , Integer>{

       Page<Posts> findByUser(User user, Pageable p);

       @Query("select p from Posts p where p.content like :key")
       List<Posts>searchByContent(@Param("key") String keyword);
}
