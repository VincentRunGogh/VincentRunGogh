package com.vincentrungogh.domain.board.entity;

import com.vincentrungogh.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users_like")
public class UsersLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    Board board;

    @Builder
    private UsersLike(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public static UsersLike createUsersLike(User user, Board board) {
        return UsersLike.builder()
                .user(user)
                .board(board)
                .build();
    }
}
