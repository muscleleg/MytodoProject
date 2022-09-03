package com.kimjaejun.mytodo.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TimePlan {
    @Id
    @GeneratedValue
    @Column(name = "timeplan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "timePlan")
    private List<TimePlanItem> items = new ArrayList<>();
}
