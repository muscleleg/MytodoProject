package com.kimjaejun.mytodo.login;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.repository.MemberRepository;
import com.kimjaejun.mytodo.service.LoginService;
import com.kimjaejun.mytodo.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class loginTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    LoginService loginService;
    @Test
    @Transactional
    public void 회원가입() {
        Member member = Member.createMember("aammoof", "1234", "김재준", "1234", "aamoof@naver.com");
        Member member1 = Member.createMember("aammoof", "1234", "김재준", "1234", "aamoof@naver.com");

        memberService.join(member);
        Member findMember = memberService.findOneById(member.getId());
        Assertions.assertThat(findMember.getId()).isEqualTo(member1.getId());

    }
}
