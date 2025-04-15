package com.sky.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sky.anno.ValidIdCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String name;

    private String password;

    @NotEmpty
    @Pattern(regexp = "^1[3-9]\\d{9}$"
            ,message = "手机号必须是11位有效数字")
    private String phone;
    @Pattern(regexp = "^([10])$")
    private String sex;
    @NotNull
    @ValidIdCard(message = "请输入有效的身份证号码")
    private String idNumber;

    private Integer status;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;

}
