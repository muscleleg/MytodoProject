package com.kimjaejun.mytodo.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity@Getter
public class TimePlan {
    @Id
    @GeneratedValue
    @Column(name = "timeplan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private LocalDate registerDate;

    @OneToMany(mappedBy = "timePlan")
    private List<TimePlanItem> items = new ArrayList<>();

    protected TimePlan() {
    }

    public TimePlan(Member member, String title, LocalDate registerDate) {
        this.member = member;
        this.title = title;
        this.registerDate = registerDate;
    }

    public static TimePlan createTimePlan(Member member, String title, LocalDate registerDate) {
        TimePlan timePlan = new TimePlan(member, title, registerDate);
        return timePlan;
    }
}
