package com.kimjaejun.mytodo.controller.member;

import com.kimjaejun.mytodo.domain.Member;
import com.kimjaejun.mytodo.form.member.SignupForm;
import com.kimjaejun.mytodo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private final MemberService memberService;
    @GetMapping("/signup")
    public String addMember(Model model) {
        model.addAttribute("signupForm",new SignupForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String addMember(@Valid @ModelAttribute("signupForm") SignupForm form, BindingResult bindingResult, Errors error) {
        if (bindingResult.hasErrors()) {
            bindingResult.reject("signupFail", "데이터 기입이 잘못되었습니다.");
            return "signup";
        }
        Member member = Member.createMember(form.getLoginId(), form.getPassword(), form.getName(), form.getSex(), form.getEmail());
        try {
            memberService.join(member);
        } catch (Exception e) {
            bindingResult.reject("signupFail", "아이디가 중복되었습니다.");
            return "signup";
        }
        return "redirect:/";
    }
}
