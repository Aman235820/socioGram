package com.aman.socialMedia.Repositories;

import com.aman.socialMedia.Entities.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepo extends JpaRepository<Comments , Integer> {
}
