package com.kimjaejun.mytodo.form.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Getter@Setter
public class SignupForm {
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]{6,12}", message = "영어만 6~12")
    private String loginId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String name;
    @NotEmpty
    private String sex;
    @NotEmpty
    private String email;
}
