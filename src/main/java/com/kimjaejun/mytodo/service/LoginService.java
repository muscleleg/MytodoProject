package com.kimjaejun.mytodo.service;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {
        return memberRepository.findByLogin(loginId, password);
    }
}
