package com.studentaccount.repositories;

import com.studentaccount.domain.entities.Like;
import com.studentaccount.domain.entities.Post;
import com.studentaccount.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    Like findByUserAndPost(User user, Post post);

    List<Like> findAllByPost(Post post);
}
