package com.vincentrungogh.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vincentrungogh.domain.route.entity.Route;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @Column(name = "like_count", nullable = false)
    private int likeCount;

    @Column(name = "created", columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime created;

    @Builder
    private Board(LocalDateTime created, String comment, boolean isDelete, int likeCount) {
        this.comment = comment;
        this.isDelete = isDelete;
        this.likeCount = likeCount;
        this.created = created;
    }

    public static Board createBoard(String comment) {
        return Board.builder()
                .comment(comment)
                .isDelete(false)
                .likeCount(0)
                .created(LocalDateTime.now())
                .build();
    }
}
