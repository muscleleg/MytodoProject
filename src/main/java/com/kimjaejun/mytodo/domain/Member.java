package com.kimjaejun.mytodo.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member{
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    
    private String loginId;
    
    private String password;
    
    private String name;
    
    private String sex;
    
    private String email;
    private LocalDateTime registerDate;

    protected Member() {

    }
    public Member(String loginId, String password, String name, String sex, String email){
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.registerDate = LocalDateTime.now();
    }
    public static Member createMember(String loginId, String password, String name, String sex, String email) {
        Member member = new Member(loginId,password,name,sex,email);
        return member;
    }
}
