package com.vincentrungogh.domain.board.repository;

import com.vincentrungogh.domain.board.entity.UserLike;
import com.vincentrungogh.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLikeRepository extends JpaRepository<UserLike, Integer> {
    
    List<UserLike> findAllByUser(User user);

}
