package com.kimjaejun.mytodo.form.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


@Getter@Setter
public class SignupForm {
    @NotEmpty
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
