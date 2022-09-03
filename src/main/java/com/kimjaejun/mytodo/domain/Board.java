package com.kimjaejun.mytodo.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Board {
    @Id@GeneratedValue
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String content;
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
