package com.vincentrungogh.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_delete")
    private boolean isDelete;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "created")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @Builder
    private Board(LocalDateTime created, String comment, boolean isDelete, int likeCount) {
        this.comment = comment;
        this.isDelete = isDelete;
        this.likeCount = likeCount;
        this.created = created;
    }

    public static Board createBoard(String comment, boolean isDelete, int likeCount) {
        return Board.builder()
                .comment(comment)
                .isDelete(isDelete)
                .likeCount(likeCount)
                .created(LocalDateTime.now())
                .build();
    }
}
